package cn.refine.controller;

import cn.refine.common.util.ExcelUtil;
import cn.refine.common.util.ResponseCommObject;
import cn.refine.util.date.DateUtils;
import cn.refine.util.file.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ivan on 2018/2/25.
 */
@Controller
@RequestMapping("/util")
public class UtilController {

    @Value("${test.savePath}")
    private String savePath;
    @Value("${test.pathPrefix}")
    private String pathPrefix;

    @RequestMapping("/uploadFile")
    @ResponseBody
    public Map<String, Object> uploadFile(@RequestParam(value="file") MultipartFile file, HttpServletResponse response,HttpServletRequest request, HttpSession session) {
    try {
        String msg = "";
        String extName = "";// 扩展名
        String newFileName;// 新文件名FileUtil.getSourceProperties().get("remoteUrl");
        if(file == null){
            msg = "上传失败";
            return	ResponseCommObject.fail(msg);
        }
        String name = file.getOriginalFilename();
        long size = file.getSize();
        if(name == null || ExcelUtil.EMPTY.equals(name) && size==0){
            msg = "上传失败";
            return	ResponseCommObject.fail(msg);
        }
//        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
//                + file.getOriginalFilename();
        String guid = UUID.randomUUID().toString().replaceAll("-", "");// 当前时间
        String nowDate = DateUtils.getCurrentDate(DateUtils.getYYYYMMDDDate());
        String savePath1 = savePath + "/portrait/businessLicense/" + nowDate + "/";
        response.setCharacterEncoding("utf-8");
        File saveFilePath = new File(savePath1);
        if (!saveFilePath.exists()) {
            saveFilePath.mkdirs();
        }
        // 获取扩展名
        String upFileFileName = file.getOriginalFilename();
        if (upFileFileName.lastIndexOf(".") >= 0) {
            extName = upFileFileName.substring(upFileFileName.lastIndexOf(".") + 1);
        }
        newFileName = guid + "." + extName;
        SaveFileFromInputStream(file.getInputStream(),savePath1,newFileName);
        Map<String,Object> map = new HashMap<>();
        map.put("msg","");
        map.put("guid",guid);
        map.put("filepath","/portrait/businessLicense/" + nowDate + "/" + newFileName);
        map.put("pathPrefix",pathPrefix);
        return map;
    }catch (Exception e){
        e.printStackTrace();
    }
    return null; // 这里不需要页面转向，所以返回空就可以了
}
    /**保存文件
     * @param stream
     * @param path
     * @param filename
     * @throws IOException
     */
    public void SaveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
        FileOutputStream fs = new FileOutputStream(path + "/" + filename);
        BufferedOutputStream bf = new BufferedOutputStream(fs);
        byte[] buffer = new byte[1024 * 1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread = stream.read(buffer)) != -1) {
            bytesum += byteread;
            bf.write(buffer, 0, byteread);
            bf.flush();
        }
        fs.close();
        stream.close();
    }

}
