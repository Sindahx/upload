package com.leaihealth.cloud.controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leaihealth.cloud.util.JsonResponse;
import com.leaihealth.cloud.util.file.DownFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.leaihealth.cloud.config.SystemConfig;
import com.leaihealth.cloud.util.date.DateUtils;
import com.leaihealth.cloud.vo.request.RequestFileVo;

/**
 * Created by Administrator on 2018/3/20.
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadFileController {
	


	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	@CrossOrigin(origins = "*", maxAge = 3600)
	public void upload(HttpServletRequest request, HttpServletResponse response, RequestFileVo vo,
			Integer isTransfer) {

		response.setContentType("json/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		try {
			vo.setGuid(UUID.randomUUID().toString());
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 获取传入文件
			multipartRequest.setCharacterEncoding("utf-8");
			MultipartFile file = multipartRequest.getFile("myFile");

			String filename = file.getOriginalFilename();
			String filePattern = "(.+)?\\.[a-zA-Z0-9]{2,5}$";
			Matcher matcher = Pattern.compile(filePattern).matcher(filename);
			if (matcher.find()) {
				vo.setExtName(FilenameUtils.getExtension(filename));
			} else {
				vo.setExtName("cf");// 自定义文件类型
			}
			vo.setNewFileName(vo.getGuid()+"."+vo.getExtName());// 新文件名称
			String nowDate = DateUtils.getCurrentDate(DateUtils.getYYYYMMDDDate());
			String savePath = SystemConfig.getString("baseDir");
			String newPath = nowDate+"/"+filename;
//			this.SaveAs(savePath+vo.getNewFileName(), file, request, response);
			this.SaveAs(savePath+newPath, file, request, response);
			vo.setSavePath(savePath);
			// 设置返回值


			Map<String, Object> resultMap =  new HashMap<>();
			resultMap.put("name", file.getOriginalFilename());
			resultMap.put("path",newPath);
			response.setStatus(200);
			Gson gson = new Gson();
			PrintWriter printWriter = response.getWriter();
			printWriter.write(gson.toJson(JsonResponse.ok().withData(resultMap)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SaveAs(String saveFilePath, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long lStartPos = 0;
		long startPosition = 0;
		long endPosition = 0;
		int fileLength = 100000;
		OutputStream fs = null;

		String contentRange = request.getHeader("Content-Range");

		if (contentRange == null) {
			FileUtils.writeByteArrayToFile(new File(saveFilePath), file.getBytes());

		} else {
			// bytes 10000-19999/1157632 将获取到的数据进行处理截取出开始跟结束位置
			if (contentRange != null && contentRange.length() > 0) {
				contentRange = contentRange.replace("bytes", "").trim();
				contentRange = contentRange.substring(0, contentRange.indexOf("/"));
				String[] ranges = contentRange.split("-");
				startPosition = Long.parseLong(ranges[0]);
				endPosition = Long.parseLong(ranges[1]);
			}

			// 判断所上传文件是否已经存在，若存在则返回存在文件的大小
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
			// 判断所上传文件片段是否存在，若存在则直接返回
			if (lStartPos > endPosition) {
//                String maxChunkSize = (String) SpringPropertyUtil.getContextProperty("maxChunkSize");
				String maxChunkSize = "10000000";
				lStartPos--;
				lStartPos = lStartPos - Long.parseLong(maxChunkSize);
				Long endPostion = lStartPos + Long.parseLong(maxChunkSize);
				response.setHeader("Range", "bytes " + lStartPos + "-" + endPostion);
				fs.close();
				return;
			} else if (lStartPos <= startPosition) {

				byte[] nbytes = new byte[fileLength];
				InputStream inputStream = file.getInputStream();
				int nReadSize = inputStream.read(nbytes);
				while (nReadSize != -1) {
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
	
	/**
	 * 文件下载
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/downUpload",method = RequestMethod.GET)
	@CrossOrigin(origins = "*", maxAge = 3600)
	public void DownUpload(HttpServletRequest req, HttpServletResponse resp) {

		String savePath = SystemConfig.getString("baseDir");
		String name = req.getParameter("name");//获取要下载的文件名

		DownFile.downFile(resp,name,savePath+name);

		  try {
			/*req.setCharacterEncoding("UTF-8");

			    //第一步：设置响应类型
			    resp.setContentType("application/force-download");//应用程序强制下载
			    //第二读取文件
//			    String savePath = SystemConfig.dataConfig.get("baseDir");
			  String savePath = SystemConfig.getString("baseDir");
//		    String path = getServletContext().getRealPath("/up/"+name);
//		    InputStream in = new FileInputStream(savePath+"/uploadImage/20180920/"+name);
			    InputStream in = new FileInputStream(savePath+name);
			    //设置响应头，对文件进行url编码
			    name = URLEncoder.encode(name, "UTF-8");
			    resp.setHeader("Content-Disposition", "attachment;filename="+name);
			    resp.setContentLength(in.available());

			    //第三步：老套路，开始copy
			    OutputStream out = resp.getOutputStream();
			    byte[] b = new byte[1024];
			    int len = 0;
			    while((len = in.read(b))!=-1){
			      out.write(b, 0, len);
			    }
			    out.flush();
			    out.close();
			    in.close();*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
