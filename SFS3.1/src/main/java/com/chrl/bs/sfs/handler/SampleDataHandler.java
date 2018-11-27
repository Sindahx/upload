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
public class SampleDataHandler extends AbstractFileHandler {

    final static String SAMPLEDATA = Server.CONFIG.getProperty("sampledata");
    private final static String  SAMPLEDATA_ID = "^" + SAMPLEDATA + "/(.+)?\\.[a-zA-Z0-9]*$";

    @Override
    public boolean check(String uri) {
        if (uri.startsWith(SAMPLEDATA)) {
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
        String strFileName = getFileName(uri,SAMPLEDATA_ID);
        StringBuilder sbSB = new StringBuilder(128);
        return sbSB.append(ROOT).append(SAMPLEDATA).append(File.separator)
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
