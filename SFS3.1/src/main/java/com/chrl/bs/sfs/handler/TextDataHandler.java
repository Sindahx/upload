package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.Server;
import com.chrl.bs.sfs.util.StringTools;
import org.jboss.netty.channel.ChannelHandler;


/**
 *
 * User: chenruilin
 * Date: 2015-05-28
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class TextDataHandler extends AbstractDataHandler {

    final static String ANY_DATA = Server.CONFIG.getProperty("any.data");

    @Override
    public boolean check(String uri) {
        if (uri.startsWith(ANY_DATA)) {
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
        String filePath = uri.replace(ANY_DATA,"");
        StringBuilder sbSB = new StringBuilder(128);
        if ("true".equals(Server.CONFIG.getProperty("is.share.dir"))){
            String path = Server.CONFIG.getProperty("share.dir.path") ;
            while (path.endsWith("/")){
                path = path.substring(0,path.length()-1);
            }
            return sbSB.append(path).append(filePath).toString();
        }else {
            String strPath = StringTools.sanitize(sbSB.append(ROOT).append(filePath).toString()) ;
            return strPath;
        }

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
