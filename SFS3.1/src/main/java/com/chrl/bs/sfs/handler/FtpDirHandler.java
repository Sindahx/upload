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
public class FtpDirHandler extends AbstractFtpDirHandler {

    final static String FILE_DIR = Server.CONFIG.getProperty("ftp.dir.path");
    @Override
    public boolean check(String uri) {
        if (uri.startsWith(FILE_DIR)) {
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
        String filePath = uri.replace(FILE_DIR,"");
        StringBuilder sbSB = new StringBuilder(128);
        return sbSB.append(ROOT).append(filePath).toString();
    }

    @Override
    public String defaultPath(String uri) {
        return null;
    }

    @Override
    public String filename(String uri) {
        return null;
    }

}
