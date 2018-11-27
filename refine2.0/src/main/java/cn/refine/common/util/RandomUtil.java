package cn.refine.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/2/1/001.
 */
public class RandomUtil {

    private static List<String> list;
    private static Properties props = new Properties();
    /**
     * 4位数 000  -  999
     * @return
     */

    public static String getExpoLocation() {

        int selFile = 000;

        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.size() <= 0) {

            for (int i = selFile; i <= 999; i++) {
                list.add(i + "");
            }
        }

        String number = list.get(0);
        list.remove(0);
        setProperties("expoLcationNum",number);
        return number;
    }


    private static void setProperties(String key,String value){
        props.setProperty(key,value);
    }

    public static void main(String[] args) {
        System.out.println("1111111111111111");
    }
}
