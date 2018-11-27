/**
 *
 *----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━by:chrl
 */


package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.Server;
import com.chrl.bs.sfs.util.FileList;
import com.chrl.bs.sfs.util.GetFileSize;
import com.chrl.bs.sfs.util.StringTools;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedFile;
import org.jboss.netty.util.CharsetUtil;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 抽象类  读取文件或者获取文件夹下面所有的文件列表。
 * User: chenruilin
 * Date: 13-10-10
 * Time:
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFtpDirHandler implements FileHandlerInterface,ChannelUpstreamHandler {

    final static String  ROOT = Server.CONFIG.getProperty("ftp.dir");
    final static String  FTP_FILE = Server.CONFIG.getProperty("ftp.dir.file.path");
    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    private static final int HTTP_CACHE_SECONDS = 31526000;

    private static final String FILE_NOT_FOUND = "FILE NOT FOUND";
    private static final String FILE_NOT_PERMISSION = "FILE NOT PERMISSION";

    private  boolean  RANDOM = true;
    /** 下载文件的名字  */
    private String downLoadFileName = null;
    private static Logger log = Logger.getLogger(AbstractFtpDirHandler.class);
    /**
     * 读取文件的处理流程
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)  {
        try {
            if (e instanceof MessageEvent) {
                MessageEvent event = (MessageEvent)e;
                HttpRequest request = (HttpRequest)event.getMessage();

                String uri = request.getUri();
                uri= StringTools.replaceAll2Web(uri);
                log.debug("uri = " + uri);
                if(check(uri)){
                    String strPath = path(uri);
                    strPath = StringTools.sanitize(strPath);
                    File file = read(strPath);
                    if (file!=null && file.isDirectory()) {

                        List<String> fileList =  FileList.getList(strPath);
                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < fileList.size(); i++) {
                            stringBuffer.append(StringTools.replaceAll2Web(fileList.get(i)).replace(ROOT, FTP_FILE)).append("**");
                        }
                        long size = GetFileSize.getFileSize(file);
                        if (size == 0){
                            sendMessage(ctx, OK,"");
                        }else {
                            stringBuffer.append(size);
                            sendMessage(ctx, OK, stringBuffer.toString());
                        }
                    }else {
                        if(file==null){
                            if (defaultPath(uri)!=null){
                                file = read(defaultPath(uri));
                                if (file==null){
                                    sendError(ctx, NOT_FOUND, FILE_NOT_FOUND);
                                    return;
                                }
                            }else {
                                sendError(ctx, NOT_FOUND, FILE_NOT_FOUND);
                                return;
                            }
                        }
                        if (cacheable() && RANDOM ){
                            ModifySince(request,file,ctx);
                        }
                        writeFuture(file,ctx,event);
                    }
                } else {
                    ctx.sendUpstream(e);
                }
            }else {
                ctx.sendUpstream(e);
            }
        }catch (Exception el){
            log.error(el.getMessage());
        }

    }

    /**
     * 错误信息
     * @param ctx
     * @param status
     * @param message
     */
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status, String message ){
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
        response.setHeader(CONTENT_TYPE, "text/HTML; charset=UTF-8");
        response.setContent(ChannelBuffers.copiedBuffer("", CharsetUtil.UTF_8));
        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
    }

    public void ModifySince(HttpRequest request,File file,ChannelHandlerContext ctx) throws ParseException {
        String ifModifiedSince = request.getHeader(IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length() != 0) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
            Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);
            long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
            long fileLastModifiedSeconds = file.lastModified() / 1000;
            if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                HttpResponse response = new DefaultHttpResponse(HTTP_1_1, NOT_MODIFIED);
                SimpleDateFormat setter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
                setter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
                Calendar time = new GregorianCalendar();
                response.setHeader(DATE, setter.format(time.getTime()));
                ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    /**
     * 读取文件
     * @param path
     * @return
     */
    public File read(String path){
        File file = new File(path);
        if (!file.exists()|| file.isHidden()) {
            return null;
        }
        return file;
    }

    /**
     * 发送信息
     * @param ctx
     * @param status
     * @param message
     */
    private static void sendMessage(ChannelHandlerContext ctx, HttpResponseStatus status, String message ){
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, status);
        response.setHeader(CONTENT_TYPE, "text/HTML; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        if(message == null || message.equalsIgnoreCase("null")){
            message = "";
        }
        response.setContent(ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8));
        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
    }





    public void writeFuture(File file,ChannelHandlerContext ctx, MessageEvent e) throws IOException {
        RandomAccessFile raf;

        try {
            raf = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException fnfe) {
            sendError(ctx, NOT_FOUND, FILE_NOT_FOUND);
            return;
        }

        long fileLength = raf.length();

        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

        response.setChunked(true);

        response.setHeader(TRANSFER_ENCODING, HttpHeaders.Values.CHUNKED);

        if(downLoadFileName!=null){
            response.setHeader("Content-Disposition", "attachment; filename="+downLoadFileName);
        }

        response.setHeader(CONTENT_LENGTH, fileLength);

        SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
        dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
        Calendar time = new GregorianCalendar();
        response.setHeader(DATE, dateFormatter.format(time.getTime()));
        time.add(Calendar.SECOND, HTTP_CACHE_SECONDS);
        response.setHeader(EXPIRES, dateFormatter.format(time.getTime()));
        response.setHeader(CACHE_CONTROL, "private, max-age=" + HTTP_CACHE_SECONDS);
        response.setHeader(LAST_MODIFIED, dateFormatter.format(new Date(file.lastModified())));
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials",true);
        Channel ch = e.getChannel();
        ch.write(response);
        ChannelFuture writeFuture;
        if (ch.getPipeline().get(SslHandler.class) != null) {
            writeFuture = ch.write(new ChunkedFile(raf, 0, fileLength, 8192));
        } else {
            final FileRegion region = new DefaultFileRegion(raf.getChannel(), 0, fileLength);
            writeFuture = ch.write(region);
            writeFuture.addListener(new ChannelFutureProgressListener() {
                public void operationComplete(ChannelFuture future) {
                    region.releaseExternalResources();
                }

                public void operationProgressed(ChannelFuture future, long amount, long current, long total) {
                }
            });
        }

        if (!isKeepAlive((HttpRequest) e.getMessage())) {
            writeFuture.addListener(ChannelFutureListener.CLOSE);
        }

    }
}
