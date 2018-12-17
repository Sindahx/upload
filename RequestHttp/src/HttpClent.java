import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class HttpClent {


    public static void main(String[] args) throws Exception {

        ResourceBundle resource = ResourceBundle.getBundle("config");//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可
        int total = 9000;

        String url = resource.getString("url");

//        total = Integer.valueOf(args[0]);


        int i  = 0;

        while(i<total){
            start(url);

            Thread.sleep(2000);
            i++;

            System.out.println("请求第"+i+"次");
        }


    }

    public static void start(String url) {

//        String url = "http://space.xlejy.com/index.php?r=space/person/blog/view&sid=402ae5398fb24db696ead5fcccf364fc&id=1614784681";

        String result = doGet(url);

        Document doc = Jsoup.parse(result);
        Elements rows = doc.select("span[class=ajax-widget]");
        Elements rows2 = rows.select("span[data-classname=widgets.mm_center_person.blog.blogview.BlogViewWidget]");
        Element ele = rows2.get(0);
        String str = ele.attr("data-kid");
        System.out.println(str);

        Long time = new Date().getTime();

        String url2 = "http://space.xlejy.com/index.php?r=widget&classname=widgets.mm_center_person.blog.blogview.BlogViewWidget&kid="+str+"&_="+time;
        System.out.println(url2);
        doGet(url2);
    }

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        String result= "";
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(httpurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();
        }

        return result;
    }

    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            os = connection.getOutputStream();
            os.write(param.getBytes());
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result;
    }
}