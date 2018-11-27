package com.chrl.bs.sfs.handler;

import com.chrl.bs.sfs.util.Debug;
import com.chrl.bs.sfs.util.FileList;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandler;


/**
 * 重新加载病历数据到内存
 * User: chenruilin
 * Date: 2015-05-28
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class ReloadDataHandler extends AbstractDataHandler {
    private static Logger log = Logger.getLogger(ReloadDataHandler.class);
    private static long times = 0;

    @Override
    public boolean check(String uri) {
        if (uri.toLowerCase().startsWith("/reload")) {
            try {
                long tempTimes = System.currentTimeMillis();
                if(times == 0 || tempTimes-times > 20000) {
                    FileList.initData();
                    times = tempTimes;
                }else{
                    Debug.out("加载过于频繁,每次加载的间隔需要20秒");
                    log.debug("加载过于频繁,每次加载的间隔需要20秒");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        return null;
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
