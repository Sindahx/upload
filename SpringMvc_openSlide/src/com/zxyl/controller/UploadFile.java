package com.zxyl.controller;

import com.google.gson.Gson;
import com.zxyl.redis.service.BaseService;
import com.zxyl.utils.CMDutils;
import com.zxyl.utils.PublicData;
import com.zxyl.utils.SpringPropertyUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/20.
 */
@Controller
public class UploadFile {

    @Resource
    private BaseService baseService;

    @RequestMapping("/upload")
    public String uploadPage(Model model,String uuid,String userName) {

        String maxChunkSize = (String) SpringPropertyUtil.getContextProperty("maxChunkSize");
        model.addAttribute("maxChunkSize",maxChunkSize);
        model.addAttribute("uuid",uuid);
        model.addAttribute("userName",userName);
        return "upload";
    }

    @RequestMapping("/uploadFile")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void upload(HttpServletRequest request, HttpServletResponse response,String userName) {

        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获取传入文件
            multipartRequest.setCharacterEncoding("utf-8");
            MultipartFile file = multipartRequest.getFile("myFile");

            String savePath = (String) SpringPropertyUtil.getContextProperty("savePath");
            this.SaveAs(savePath + userName+file.getOriginalFilename(), file, request, response);
            // 设置返回值
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", file.getOriginalFilename());
            response.setStatus(200);
            Gson gson = new Gson();
            PrintWriter printWriter = response.getWriter();
            printWriter.write(gson.toJson(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/removeFile")
    @ResponseBody
    public String removeFile(String fileName, String postfix,String uuid) throws Exception {

        String targetPath = (String) SpringPropertyUtil.getContextProperty("targetPath");
        String savePath = (String) SpringPropertyUtil.getContextProperty("savePath");
        String tomcatPath = (String) SpringPropertyUtil.getContextProperty("tomcatPath");
//        baseService.put("ln_image_file",uuid,"flag_true");
        String newFileName = uuid + "." + postfix;
        File file = new File(savePath + fileName);
        File targetFile = new File(targetPath + newFileName);
        PublicData.map.put(uuid,"flag_true");
        this.copyFile(file, targetFile);
        file.delete();
        Map map = new HashMap();
        map.put("name", uuid);

        CMDutils.executeLinuxCmd(targetFile.getPath(),tomcatPath,uuid);
        baseService.put("imageTransform",uuid,"isOk");
        Gson gson = new Gson();
        return gson.toJson(map);
    }
    @RequestMapping("/serchFile")
    @ResponseBody
    public String serchFile(String uuid,HttpServletRequest req){


        String callbackFunName =req.getParameter("jsonpCallback");//得到js函数名称
//        String isExis =  (String)baseService.get("ln_image_file",uuid);
        String isExis =  PublicData.map.get(uuid);
        //查询文件是否上传成功
        PublicData.map.remove(uuid);
        Gson gson = new Gson();
        Map map = new HashMap();
        map.put("isExist",isExis);

        String str =  callbackFunName+"("+gson.toJson(map)+")";

        return str;
    }

    /**
     * 复制文件
     *
     * @param fromFile
     * @param toFile   <br/>
     *                 2016年12月19日  下午3:31:50
     * @throws IOException
     */
    public void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = ins.read(b)) != -1) {
            out.write(b, 0, n);
        }

        ins.close();
        out.close();
    }

    private void SaveAs(String saveFilePath, MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {

        long lStartPos = 0;
        long startPosition = 0;
        long endPosition = 0;
        int fileLength = 100000;
        OutputStream fs = null;

        String contentRange = request.getHeader("Content-Range");
        System.out.println(contentRange);

        if (contentRange == null) {
            FileUtils.writeByteArrayToFile(new File(saveFilePath), file.getBytes());

        } else {
            // bytes 10000-19999/1157632     将获取到的数据进行处理截取出开始跟结束位置
            if (contentRange != null && contentRange.length() > 0) {
                contentRange = contentRange.replace("bytes", "").trim();
                contentRange = contentRange.substring(0, contentRange.indexOf("/"));
                String[] ranges = contentRange.split("-");
                startPosition = Long.parseLong(ranges[0]);
                endPosition = Long.parseLong(ranges[1]);
            }

            //判断所上传文件是否已经存在，若存在则返回存在文件的大小
            if (new File(saveFilePath).exists()) {
                fs = new FileOutputStream(saveFilePath, true);
                FileInputStream fi = new FileInputStream(saveFilePath);
                lStartPos = fi.available();
                fi.close();
            } else {
                fs = new FileOutputStream(saveFilePath);
                lStartPos = 0;
            }
            System.out.println(lStartPos);
            //判断所上传文件片段是否存在，若存在则直接返回
            if (lStartPos > endPosition) {
                String maxChunkSize = (String) SpringPropertyUtil.getContextProperty("maxChunkSize");
                lStartPos--;
                lStartPos = lStartPos-Long.parseLong(maxChunkSize);
                Long endPostion = lStartPos+ Long.parseLong(maxChunkSize);
                response.setHeader("Range","bytes "+lStartPos+"-"+endPostion);
                fs.close();
                return;
            } else if (lStartPos <= startPosition) {

                byte[] nbytes = new byte[fileLength];
                InputStream inputStream = file.getInputStream();
                int nReadSize = inputStream.read(nbytes);
                while (nReadSize!=-1) {
                    fs.write(nbytes, 0, nReadSize);
                    nReadSize = inputStream.read(nbytes);
                }
            }
        }
        if (fs != null) {
            fs.flush();
            fs.close();
            fs = null;
        }
    }
}
