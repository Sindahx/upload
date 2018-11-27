package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.Server;
import org.apache.commons.io.FilenameUtils;
import org.jboss.netty.channel.ChannelHandler;

import java.io.File;


/**
 *
 * User: chenruilin
 * Date: 13-10-11
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class PortraitHandler extends AbstractFileHandler {

    final static String PORTRAIT = Server.CONFIG.getProperty("portrait");
    private final static String  PORTRAIT_ID = "^" + PORTRAIT + "/(.+)?\\.[a-zA-Z0-9]{3,4}$";

    @Override
    public boolean check(String uri) {
        if (uri.startsWith(PORTRAIT)) {
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
        String strFileName = getFileName(uri,PORTRAIT_ID);
        StringBuilder sbSB = new StringBuilder(128);
        return sbSB.append(ROOT).append(PORTRAIT).append(File.separator)
                .append(strFileName).append(FilenameUtils.EXTENSION_SEPARATOR)
                .append(FilenameUtils.getExtension(uri)).toString();
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
