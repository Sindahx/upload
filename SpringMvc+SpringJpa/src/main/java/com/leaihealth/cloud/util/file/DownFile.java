package com.leaihealth.cloud.util.file;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * ClassName: com.leaihealth.cloud.util.file.DownFile
 * <p>
 * Auth: huangxing
 * <p>
 * Date: 2019/3/12
 * <p>
 * COPYRIGHT (C) 2016 RETair.com. All Rights Reserved.
 */
public class DownFile {

    public static void downFile(HttpServletResponse resp, String name, String path) {


        try {
            InputStream in = new FileInputStream(path);

            resp.setContentType("application/force-download");//应用程序强制下载
            resp.setHeader("Content-Disposition", "attachment;filename=" + name);
            resp.setContentLength(in.available());

            //第三步：老套路，开始copy
            OutputStream out = resp.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
