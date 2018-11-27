package com.zxyl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class JDBCUtlTool {

    public static  Map<String,Object> getConnection(String url,String name,String pwd) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("stat","1");
        String driver = "com.mysql.jdbc.Driver";   //获取mysql数据库的驱动类
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, name, pwd);//获取连接对象
            map.put("conn",conn);
            return map;
        } catch (Exception e) {
//            e.printStackTrace();
            map.put("message",e.toString());
            map.put("stat","0");
            return map;
        }
    }

    public static void closeAll(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String,Object> checkDb(String url,String name,String pwd){
//        boolean isTrue = false;
        Map<String,Object> map = JDBCUtlTool.getConnection(url,name,pwd);

        String stat = (String) map.get("stat");

        if(stat.equals("1")){
            Connection conn = (Connection)map.get("conn");
            closeAll(conn);
            map.remove("conn");
        }

//        Connection cc = JDBCUtlTool.getConnection(url,name,pwd);
//        if(!Validator.isBlank(cc)){
//            isTrue =  true;
//        }
//        closeAll(cc);
        return map;
    }
}