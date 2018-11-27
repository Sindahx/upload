package com.chrl.bs.sfs.util;

/**
 * Created by cat(chrl) on 2015/5/29.
 */

import com.chrl.bs.sfs.Server;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileList {
    private static long count = 0;
    private static Logger log = Logger.getLogger(FileList.class);
    /**
     * 获取文件夹下所有的文件,包括子文件夹下的 过滤掉Image 和 出院数据
     * @param dir_name  主文件夹
     * @return 所有文件列表
     * @throws Exception
     */
    public static List<String> getListJson(String dir_name) throws Exception {
        Vector<String> ver = new Vector<String>();
        List<String> filesList = new ArrayList<String>();
        ver.add(dir_name);
        while (ver.size() > 0) {
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            if(files != null ) {
                int len = files.length;
                for (int i = 0; i < len; i++) {
                    String tmp = files[i].getAbsolutePath();
                    if (files[i].isDirectory()) {    //如果是目录，则加入队列。以便进行后续处理
                        ver.add(tmp);
                    } else if (!tmp.contains("\\out\\") && !tmp.contains("\\Image\\")) {
                        filesList.add(tmp);
                    }
                }
            }else {
                log.debug("文件夹不存在 ！");
            }
        }
        log.debug("filesList size = " + filesList.size());
        return filesList;
    }



    /**
     * 获取文件夹下所有的文件,包括子文件夹下的 过滤掉Image 和 出院数据
     * @param dir_name  主文件夹
     * @return 所有文件列表
     * @throws Exception
     */
    public static List<String> getShareListJson(String dir_name) throws Exception {
        Vector<String> ver = new Vector<String>();
        List<String> filesList = new ArrayList<String>();
        ver.add(dir_name);
        while (ver.size() > 0) {
            SmbFile[] files = new SmbFile(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            if(files != null ) {
                int len = files.length;
                for (int i = 0; i < len; i++) {
                    String tmp = files[i].getCanonicalPath();
                    if (files[i].isDirectory()) {    //如果是目录，则加入队列。以便进行后续处理
                        ver.add(tmp);
                    } else if (!tmp.contains("\\out\\") && !tmp.contains("\\Image\\")) {
                        filesList.add(tmp);
                    }
                }
            }else {
                log.debug("文件夹不存在 ！");
            }
        }
        log.debug("filesList size = " + filesList.size());
        return filesList;
    }





    /**
     * 获取文件夹下所有的文件,包括子文件夹下的
     * @param dir_name  主文件夹
     * @return 所有文件列表
     * @throws Exception
     */
    public static List<String> getList(String dir_name) throws Exception {
        Vector<String> ver = new Vector<String>();
        List<String> filesList = new ArrayList<String>();
        ver.add(dir_name);
        while (ver.size() > 0) {
            File[] files = new File(ver.get(0).toString()).listFiles();    //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            if(files != null ) {
                int len = files.length;
                for (int i = 0; i < len; i++) {
                    String tmp = files[i].getAbsolutePath();
                    if (files[i].isDirectory()) {    //如果是目录，则加入队列。以便进行后续处理
                        ver.add(tmp);
                    } else {
                        filesList.add(tmp);
                    }
                }
            }else {
                log.debug("文件夹不存在 ！");
            }
        }
        log.debug("filesList size = " + filesList.size());
        return filesList;
    }

    /**
     * 读取txt文件的内容
     * @param filePath 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(String filePath){
        StringBuffer result = new StringBuffer();
        try{

            InputStream in = new FileInputStream(filePath);//读取文件上的数据。
            InputStreamReader isr = new InputStreamReader(in,Server.CONFIG.getProperty("encode"));//读取
            BufferedReader bufr = new BufferedReader(isr);//缓冲
            String line ;
            while((line = bufr.readLine())!=null){
                result.append(line);
            }
            isr.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public synchronized static void initData() throws Exception {
        if ("false".equalsIgnoreCase(Server.CONFIG.getProperty("is.load.json.files"))){
            return;
        }
        long t1 = System.currentTimeMillis();
        List<String> list ;
        if ("true".equals(Server.CONFIG.getProperty("is.share.dir"))){
            list = FileList.getShareListJson(Server.CONFIG.getProperty("share.dir.path"));
        }else {
            list = FileList.getListJson(Server.CONFIG.getProperty("json"));
        }
        Server.DATA_MAP.clear();
        log.debug("clear the map.");
        log.debug("loading......");
        Debug.out("clear the map..");
        Debug.out("loading......");

        String content;
        int i = 0;
        int size = list.size();
        String hideInfo = Server.CONFIG.getProperty("hide.info");
        for(String str : list){
            i++;
            log.info("[" + i + "/" + size + "]loading file: " + str);
            if ("true".equals(Server.CONFIG.getProperty("is.share.dir"))){
                content = ReadShareFile.readShareFile(str);
            }else {
                content = FileList.txt2String(str);
            }

            Server.DATA_MAP.put(str,content);
            Server.DATA_MAP.put(str+"-times",System.currentTimeMillis());
            if("true".equals(hideInfo)){
                Server.DATA_MAP.put(str+"-hide",false);
            }
        }

        long t2 = System.currentTimeMillis();
        Debug.out(" load finish used:" + (t2 - t1) + "MS.files=" + size);
        log.debug(" load finish used:" + (t2 - t1) + "MS.files=" + size);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Debug.out("第"+(++count)+"次加载.");
        log.debug("第" + (count) + "次加载.");
        Debug.out("The last loading time:"+ sdf.format(new Date()));
        log.debug("The last loading time:" + sdf.format(new Date()));



    }

    public static void main(String[] args) throws Exception {
        // 为了以可读可写的方式打开文件，这里使用RandomAccessFile来创建文件。
        FileChannel fc = new RandomAccessFile("e:\\2.txt", "r").getChannel();
        //注意，文件通道的可读可写要建立在文件流本身可读写的基础之上
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

        String a = out.toString();
        System.out.print(a);

        fc.close();
    }
}