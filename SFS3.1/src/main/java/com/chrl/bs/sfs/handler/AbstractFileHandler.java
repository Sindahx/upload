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

import com.chrl.bs.sfs.RedisClient;
import com.chrl.bs.sfs.Server;
import com.chrl.bs.sfs.util.StringTools;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedFile;
import org.jboss.netty.util.CharsetUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 抽象类  读取文件 下载。
 * User: chenruilin
 * Date: 13-10-10
 * Time:
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFileHandler implements FileHandlerInterface,ChannelUpstreamHandler {

    private static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    private static final int HTTP_CACHE_SECONDS = 31526000;

    private static final String FILE_NOT_FOUND = "FILE NOT FOUND";
    private static final String FILE_NOT_PERMISSION = "FILE NOT PERMISSION";

    private  boolean  RANDOM = true;
    final static String  ROOT = Server.CONFIG.getProperty("root")+File.separatorChar;
    final static String  FAVICON = "/favicon.ico";

    private static Logger log = Logger.getLogger(AbstractFileHandler.class);

    /** 下载文件的名字  */
    private String downLoadFileName = null;

    /**
     * 读取文件的处理流程
     * @param ctx
     * @param e
     * @throws Exception
     */
    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof MessageEvent) {
            MessageEvent event = (MessageEvent)e;
            HttpRequest request = (HttpRequest)event.getMessage();

            String uri = request.getUri();
            uri= StringTools.replaceAll2Web(uri);
            log.info("uri = "+ uri);
            if(uri.contains("?")){
                uri = uri.substring(0,uri.indexOf("?"));
                RANDOM = false;
            }

            if (uri.equals(FAVICON)) {
                sendError(ctx, FORBIDDEN, FILE_NOT_FOUND);
                return;
            }

            if(check(uri)){

                String strPath = path(uri);
                if(strPath==null){
                    sendError(ctx, BAD_REQUEST, FILE_NOT_FOUND);
                    return;
                }
                strPath = StringTools.sanitize(strPath);

                log.info(strPath);
                File file = read(strPath);
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

                if(security()){
                    QueryStringDecoder objDecoder = new QueryStringDecoder(uri);
                    String token = objDecoder.getParameters().get("token").get(0);
                    String id = objDecoder.getParameters().get("id").get(0);
                    if (!isTokenValid(token, id)) {
                        sendError(ctx, UNAUTHORIZED, FILE_NOT_PERMISSION);
                        return;
                    }
                }

                downLoadFileName = filename(uri);


                if (cacheable() && RANDOM ){
                    ModifySince(request,file,ctx);
                }
                writeFuture(file,ctx,event);
            } else {
                ctx.sendUpstream(e);
            }
        }else {
            ctx.sendUpstream(e);
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
        String strStyle = "text-align:center;word-break:break-all;color:#999999;font-weight:bold;font-family:inherit;font-family: 'Microsoft YaHei';font-size: 17.5px;";
        if (FILE_NOT_FOUND.equals(message)){
            response.setContent(ChannelBuffers.copiedBuffer("<!DOCTYPE html><br/><br/><br/><h4 style=\""+strStyle+"\">找不到文件。</h4>", CharsetUtil.UTF_8));
        }else if(FILE_NOT_PERMISSION.equals(message)){
            response.setContent(ChannelBuffers.copiedBuffer("<!DOCTYPE html><br/><br/><br/><h4 style=\""+strStyle+"\">没有权限。</h4>", CharsetUtil.UTF_8));
        }else{
            response.setContent(ChannelBuffers.copiedBuffer("<!DOCTYPE html><br/><br/><br/><h4 style=\""+strStyle+"\">ERROR</h4>", CharsetUtil.UTF_8));
        }

        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
    }


    public boolean isTokenValid(String token, String value) {
        RedisClient objRedisClient = new RedisClient();
        Jedis objJedis = objRedisClient.masterPool.getResource();
        Pipeline objPipeline = objJedis.pipelined();
        Response<String> objValue = objPipeline.get(token);
        objPipeline.del(token);
        objPipeline.expire(token, 300);
        objPipeline.sync();
        objRedisClient.masterPool.returnResource(objJedis);
        String strValue = objValue.get();
        return strValue != null && strValue.equals(value);
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
        if (!file.exists() || !file.isFile() || file.isHidden()) {
            return null;
        }
        return file;
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

    /**
     * 获取文件名
     * @param uri
     * @param pattern
     * @return
     */
    public String getFileName(String uri,String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(uri);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }


    /**
     * 特殊字符转化
     * @param str
     * @return
     */
    public String specialCharDecoder(String str){
        return str .replace("+"," ")
                .replace("%28","(")
                .replace("%29",")")
                .replace("%23","#")
                .replace("%40","@")
                .replace("%7B","{")
                .replace("%7D","}")
                .replace("%5B","[")
                .replace("%5D","]")
                .replace("%7E","~")
                .replace("%21","!")
                .replace("%24","$")
                .replace("%5E","^")
                .replace("%3D","=")
                .replace("%2C",",")
                .replace("%27","'")
                .replace("%3B",";");
    }
}
