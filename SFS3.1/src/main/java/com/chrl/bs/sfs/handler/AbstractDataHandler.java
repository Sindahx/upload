package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.Server;
import com.chrl.bs.sfs.util.Debug;
import com.chrl.bs.sfs.util.HideInfoUtils;
import com.chrl.bs.sfs.util.StringTools;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;


import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.*;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.*;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 抽象类
 * 读取 静态 DATA_MAP 里面的JSON数据 并且 判断是否需要脱敏，需要则脱敏后在次存放到 DATA_MAP
 * User: chenruilin
 * Date: 13-10-10
 * Time:
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDataHandler implements FileHandlerInterface,ChannelUpstreamHandler {

    private static final String FILE_NOT_FOUND = "FILE NOT FOUND";

    final static String  ROOT = Server.CONFIG.getProperty("json");
    private static Logger log = Logger.getLogger(AbstractDataHandler.class);
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
            if(check(uri)){
                String strPath = path(uri);
                if(strPath==null){
                    sendError(ctx, BAD_REQUEST, FILE_NOT_FOUND);
                    return;
                }

                log.debug("filePath=" +strPath);
                String hideInfo = Server.CONFIG.getProperty("hide.info");
                if("true".equals(hideInfo)) {
                    if (Boolean.FALSE.equals(Server.DATA_MAP.get(strPath + "-hide"))) {
                        Debug.out("脱敏处理：" + strPath);
                        Server.DATA_MAP.put(strPath, HideInfoUtils.hide(String.valueOf(Server.DATA_MAP.get(strPath))));
                        Server.DATA_MAP.put(strPath + "-hide", true);
                    }
                }
                String file = String.valueOf(Server.DATA_MAP.get(strPath));
                sendMessage(ctx, OK, file);
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
        response.setContent(ChannelBuffers.copiedBuffer("", CharsetUtil.UTF_8));

        ctx.getChannel().write(response).addListener(ChannelFutureListener.CLOSE);
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

}
