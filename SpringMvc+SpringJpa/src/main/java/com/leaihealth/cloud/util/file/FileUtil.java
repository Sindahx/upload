package com.leaihealth.cloud.util.file;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhuxiange
 * *文件操作工具类)
 * 2012-10-26 下午02:49:07
 */
public class FileUtil {

    public static final String FILE_GUID = "fileGuid";

    public static final String FILE_PATH = "filePath";

    public static final String FTP_FILE_NAME = "ftpFileName";

    public static final String FILE_EXT_NAME = "extName";

    private static Map<String, String> sourceProperties;

    private static void init() {
        sourceProperties = new HashMap<String, String>();
    }

    public static Map<String, String> getSourceProperties() {
        if (sourceProperties == null) {
            init();
        }
        return sourceProperties;
    }


    /**
     * @return Map<String       ,       String> 返回类型
     * @throws
     */
    public Map readProperties(String fileName) {
        InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);
        Properties p = new Properties();
        try {
            p.load(inputStream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Map deptMap = new HashMap();
        Set s = p.keySet();
        for (Object key : s) {
            try {
                deptMap.put(key, new String(p.getProperty(String.valueOf(key)).getBytes("ISO-8859-1"), "GBK"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return deptMap;
    }

    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public static String readTxts(String filePathAndName, String encoding) {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data);
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = new String(str.toString().getBytes(), "gbk");
            fs.close();
            br.close();
            isr.close();
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

    /**
     * 进行文件排序时间
     *
     * @author 朱湘鄂
     */
    private static class CompratorByLastModified implements Comparator<File> {
        public int compare(File f1, File f2) {
            long diff = f2.lastModified() - f1.lastModified();
            if (diff > 0)
                return 1;
            else if (diff == 0)
                return 0;
            else
                return -1;
        }

        public boolean equals(Object obj) {
            return true;
        }
    }

    /**
     * 读取目录文件
     *
     * @param dirname 目录名称
     * @return list集合
     */
    @SuppressWarnings("unchecked")
    public static List getFiles(String dirname) throws Exception {
        List file_names = null;
        File dir = new File(dirname);
        if (dir.exists()) {
            file_names = new ArrayList();
            File[] files = dir.listFiles();
            // 排序
            Arrays.sort(files, new CompratorByLastModified());
            for (int i = 0; i < files.length; i++) {
                // 获取当文件最后修改时间
                @SuppressWarnings("unused")
                String creatime = FileUtil.format("yyyy-MM-dd hh:mm:ss", new Date(files[i].lastModified()));
                file_names.add(files[i].getName());
            }
        } else {
            System.out.println("该目录没有任何文件信息！");
        }
        return file_names;
    }

    /**
     * 格式化时间
     *
     * @param format 格式化显示样式
     * @param date   时间
     * @return
     */
    private static String format(String format, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    // 测试
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        /*FileUtil file = new FileUtil();
        String[] str = new String[]{"mpiUser", "mpipwd", "mpiurl"};
        Map<String, String> map = file.readProperties("source.properties", str);
        System.out.println(map.get("mpiUser"));
        System.out.println(map.get("mpipwd"));
        System.out.println(map.get("mpiurl"));*/
        FileUtil file = new FileUtil();
        System.out.println(FileUtil.readTxts("E:\\files\\sampledata\\44010400102\\20140926\\4f1864cace8d4c1d988e1a4013839e4c\\4f1864cace8d4c1d988e1a4013839e4c.json", "gbk"));
    }

    /**
     * 上传文件到ftp，返回map可通过常量来获取值
     *
     * @param response response对象
     * @param upFile   待上传文件对象
     * @param fileName 待上传文件的名称
     * @param filePath 上传的路径，无需加上ftp地址
     * @return map
     * key : FILE_GUID 文件在ftp的唯一标识
     * key : FILE_PATH 文件ftp路径
     * key : FTP_FILE_NAME 文件在ftp的文件名,包含文件类型后缀
     * key : FILE_EXT_NAME 文件类型
     */
    public Map<String, String> uploadFile(HttpServletResponse response, File upFile, String fileName, String filePath) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String extName = "";// 扩展名
            String newFileName = "";// 新文件名
            FileInputStream is = new FileInputStream(upFile);
            String guid = UUID.randomUUID().toString().replaceAll("-", "");// 当前时间
            String ftpUrl = FileUtil.getSourceProperties().get("remoteUrl");
            String savePath = ftpUrl + filePath;
            response.setCharacterEncoding("utf-8");
            File saveFilePath = new File(savePath);
            if (!saveFilePath.exists()) {
                saveFilePath.mkdirs();
            }
            // 获取扩展名
            if (fileName.lastIndexOf(".") >= 0) {
                extName = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            newFileName = guid + "." + extName;
            FileOutputStream os = new FileOutputStream(new File(savePath + newFileName));
            byte[] data = new byte[1024];
            int length = 0;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.close();
            is.close();
            map.put(FILE_GUID, guid);
            map.put(FILE_PATH, filePath + newFileName);
            map.put(FILE_EXT_NAME, extName);
            map.put(FTP_FILE_NAME, newFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 判断文件是否存在
    public static void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // 判断文件夹是否存在
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }

}
