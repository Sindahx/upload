package com.zxyl.utils;

import java.io.*;

/**
 * Created by Administrator on 2018/4/2.
 */
public class CMDutils {

    public static String executeLinuxCmd(String pathname_,String targetPath,String name_) {

        String cmd = "vips dzsave "+pathname_+" "+targetPath+name_;

        System.out.println("got cmd job : " + cmd);
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmd);
            printMessage(process.getInputStream());
            printMessage(process.getErrorStream());

            int value = process.waitFor();
            System.out.println(value);
            return "";
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printMessage(final InputStream input) {
        new Thread(new Runnable() {
            public void run() {
                Reader reader = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
