package cn.refine.util.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件扫描类
 * @author CHRL
 *
 */
public class FileScan extends Thread {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {

            sleep(180000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 扫描指定路径下的文件目录和文件
     * 打印出最后更新时间.
     * 实际用时去掉打印
     * @param path
     */
    public void scanByPath(String path){
        System.setProperty("user.timezone", "Asia/Shanghai");
        File f = new File(path);
        if(f.isDirectory()){
            File [] files = f.listFiles();
            for (File file : files) {
                if(!file.isDirectory()){
                    System.out.println(file.getName()
                        +"   [ create-time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date(file.lastModified()))+"]");
                }else{
                	System.out.println("--"+file.getName()
                		+"   [ create-time:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date(file.lastModified()))+"]");
                }
            }
        }
    }

    public static void main(String [] arg){

        FileScan fs = new FileScan();
        fs.scanByPath("\\\\192.168.0.100\\d$");
    }

}
