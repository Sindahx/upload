package com.chrl.bs.sfs.util;


import com.chrl.bs.sfs.Server;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.log4j.Logger;


/**
 * Created by Administrator on 2016/1/26.
 */
public class ReadShareFile {
    private static Logger log = Logger.getLogger(ReadShareFile.class);
    public static void main(String[] args) {
        System.out.println(ReadShareFile.readShareFile(""));
    }

    public static String readShareFile(String path){
//        path = "smb://administrator:app_8888@192.168.1.254/e/JSON_FILES/\\455755741\\in\\Data\\4001.json";
        try {
            SmbFile smbFile = new SmbFile(path);
            int length = smbFile.getContentLength();// 得到文件的大小
            byte buffer[] = new byte[length];
            SmbFileInputStream in = new SmbFileInputStream(smbFile);
            // 建立smb文件输入流
            while ((in.read(buffer)) != -1) {
            }
            in.close();
            if(Server.CONFIG.getProperty("encode")!=null && !"".equals(Server.CONFIG.getProperty("encode"))) {
                return new String(buffer, Server.CONFIG.getProperty("encode"));
            }else{
                return new String(buffer,"UTF-8");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }
}