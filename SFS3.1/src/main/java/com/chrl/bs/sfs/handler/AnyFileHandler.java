package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.Server;
import org.jboss.netty.channel.ChannelHandler;


/**
 *
 * User: chenruilin
 * Date: 2015-05-28
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class AnyFileHandler extends AbstractFileHandler {

    final static String ANY_FILE = Server.CONFIG.getProperty("any.file");

    @Override
    public boolean check(String uri) {
        if (uri.startsWith(ANY_FILE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean security() {
        return false;
    }

    @Override
    public boolean cacheable() {
        return true;
    }

    @Override
    public String path(String uri) {
        String filePath = uri.replace(ANY_FILE,"");
        StringBuilder sbSB = new StringBuilder(128);
        return sbSB.append(ROOT).append(filePath).toString();
    }

    @Override
    public String defaultPath(String uri) {
        StringBuilder sbSB = new StringBuilder(128);
        return sbSB.append(ROOT).append("default.txt").toString();
    }

    @Override
    public String filename(String uri) {
        return null;
    }

}
