package com.zxyl.utils;

import org.dom4j.*;
import org.jaxen.SimpleNamespaceContext;

import java.util.*;

public class JiexiXml {

    /**
     * 便利所有节点下的所有 子节点和属性到map，相同节点的值会被覆盖
     *
     * @param ele
     * @param map
     * @return
     */
    public static Map parseAll(Element ele, Map<String, String> map) {

//		Map<String, Object> returnMap = new HashMap<String, Object>();

        List<Attribute> attrList = ele.attributes();
        for (Attribute attr : attrList) {
            //每循环一次，解析此节点的一个【属性=值】，没有则输出空  
            map.put(attr.getName(), attr.getValue());
        }

        if (ele.elements().size() == 0) {
            map.put(ele.getName(), ele.getText());
//			 System.err.println(ele.getName()+":"+ele.getText());
        } else {

            List<Element> elementList = ele.elements();

            Iterator<Element> it = elementList.iterator();
            while (it.hasNext()) {
                // returnMap.put(ele.getName(), getMap(it.next()));
                parseAll(it.next(), map);
            }

        }

        return map;
    }

    /**
     * 解析当前节点下的所有得内容
     *
     * @param ele
     * @param map
     * @return
     */
    public static Map parseToMapThis(Element ele, Map<String, String> map) {

//		Map<String, Object> returnMap = new HashMap<String, Object>();

        List<Attribute> attrList = ele.attributes();
        for (Attribute attr : attrList) {
            //每循环一次，解析此节点的一个【属性=值】，没有则输出空  
            map.put(attr.getName(), attr.getValue());
        }


        List<Element> elementList = ele.elements();
        for (Element element : elementList) {

            map.put(element.getName(), element.getText());
        }


        return map;
    }


    /**
     * 解析入院记录xml
     *
     * @param doc
     * @return
     */
    public static Map parseinHospital(Document doc) {

        Map map = new HashMap();

        List<Element> list = null;
        list = doc.selectNodes("//return");

        if (!list.isEmpty() && list.size() > 0) {
            map = JiexiXml.parseToMapThis(list.get(0), map);
        }


        return map;
    }

    /**
     * 解析入院记录xml
     *
     * @param doc
     * @return
     */
    public static List<Object> parseinList(Document doc) {

        List<Object> listData = new ArrayList<Object>();

        List<Element> list = null;
        list = doc.selectNodes("//return");

        for (Element element2 : list) {
            Map map = new HashMap();
            map = JiexiXml.parseToMapThis(element2, map);
            listData.add(map);
        }


        return listData;
    }


    public static List<Object> parseinList(String xml,String parame) {

        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return parseinList(doc,parame);
    }

    /**
     * 解析入院记录xml
     *
     * @param doc
     * @return
     */
    public static List<Object> parseinList(Document doc,String parame) {

        List<Object> listData = new ArrayList<Object>();

        List<Element> list = null;
        list = doc.selectNodes("//"+parame);

        for (Element element2 : list) {
            Map map = new HashMap();
            map = JiexiXml.parseToMapThis(element2, map);
            listData.add(map);
        }


        return listData;
    }


    /**
     * 解析入院记录xml
     *
     * @param xml
     * @return
     */
    public static Map parseinHospital(String xml) {

        Map map = new HashMap();

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
            list = doc.selectNodes("//return");

            if (!list.isEmpty() && list.size() > 0) {
                map = JiexiXml.parseToMapThis(list.get(0), map);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return map;
    }


    /**
     * 解析入院记录xml
     *
     * @param xml
     * @return
     */
    public static Map parseinHospital(String xml,String result) {

        Map map = new HashMap();

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
            list = doc.selectNodes("//"+result);

            if (!list.isEmpty() && list.size() > 0) {
                map = JiexiXml.parseToMapThis(list.get(0), map);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return map;
    }


    public static Map<String,Object> parseAll(String xml,String result,Map map){
        if(Validator.isBlank(map)){
            map = new HashMap();
        }

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
            list = doc.selectNodes("//"+result);

            if (!list.isEmpty() && list.size() > 0) {
                map = JiexiXml.parseAll(list.get(0), map);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return map;
    }


    /**
     * 解析入院记录xml
     *
     * @param xml
     * @return
     */
    public static List<Object> parseinHospitalDet(String xml) {

//		Map map = new HashMap();
        List<Object> listData = new ArrayList<Object>();

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);

            HashMap ss = new HashMap();
            ss.put("c", "urn:hl7-org:v3");

            XPath xpath = doc.createXPath("//c:observation");
            xpath.setNamespaceContext(new SimpleNamespaceContext(ss));

            list = xpath.selectNodes(doc);
            if (!list.isEmpty() && list.size() > 0) {
                for (Element element2 : list) {
                    Map<String, String> map1 = new HashMap<String, String>();
                    // map =getMap(element2,map);
                    map1 = JiexiXml.parseAll(element2, map1);
                    listData.add(map1);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return listData;
    }

    /**
     * 解析入院记录xml
     *
     * @param doc
     * @return
     */
    public static List<Object> parseinHospitalDet(Document doc) {

//		Map map = new HashMap();
        List<Object> listData = new ArrayList<Object>();

        List<Element> list = null;

        HashMap ss = new HashMap();
        ss.put("c", "urn:hl7-org:v3");

        XPath xpath = doc.createXPath("//c:observation");
        xpath.setNamespaceContext(new SimpleNamespaceContext(ss));

        list = xpath.selectNodes(doc);
        if (!list.isEmpty() && list.size() > 0) {
            for (Element element2 : list) {
                Map<String, String> map1 = new HashMap<String, String>();
                // map =getMap(element2,map);
                map1 = JiexiXml.parseAll(element2, map1);
                listData.add(map1);
            }
        }


        return listData;
    }

    /**
     * 解析入院记录xml
     *
     * @param xml
     * @return
     */
    public static List<Object> parseDayDisease(String xml) {

        List<Object> listData = new ArrayList<Object>();

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
            list = doc.selectNodes("//return");

            if (!list.isEmpty() && list.size() > 0) {
                for (Element element2 : list) {
                    Map<String, String> map = new HashMap<String, String>();
                    map = JiexiXml.parseToMapThis(element2, map);
                    listData.add(map);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return listData;
    }




    public static Map<String, String> pantient(String xml) {

//

        Map map = new HashMap();

        List<Element> list = null;
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);

            HashMap ss = new HashMap();
            ss.put("c", "urn:hl7-org:v3");

            XPath xpath = doc.createXPath("//c:administrativeGenderCode");
            xpath.setNamespaceContext(new SimpleNamespaceContext(ss));

            list = xpath.selectNodes(doc);

            if (!list.isEmpty() && list.size() > 0) {
                map = JiexiXml.parseAll(list.get(0), map);
            }

            xpath = doc.createXPath("//c:birthTime");
            xpath.setNamespaceContext(new SimpleNamespaceContext(ss));

            list = xpath.selectNodes(doc);
            if (!list.isEmpty() && list.size() > 0) {
                map = JiexiXml.parseAll(list.get(0), map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return map;
    }

    public static String textxml = "<PRPA_IN999999UV99>\n" +
            "\t<!-- UUID,交互实例唯一码-->\n" +
            "\t<id root=\"2.999.1.96\" extension=\"e92acb6f-87cf-4084-8b14-3f04d2bef3d2\"/>\n" +
            "\t<!-- 消息发送时间yyyyMMddhhmmSSsss 精确到毫秒 -->\n" +
            "\t<creationTime value=\"20130501130624003\"/>\n" +
            "\t<interactionId root=\"2.16.840.1.113883.1.6\" extension=\"PRPA_IN999999UV99\"/>\n" +
            "\t<receiver typeCode=\"RCV\">\n" +
            "\t\t<telecom value=\"\"/>\n" +
            "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
            "\t\t\t<id root=\"2.999.1.97\" extension=\"XXX\"/>\n" +
            "\t\t\t<telecom value=\"设备编号\"/>\n" +
            "\t\t\t<softwareName code=\"XXX\" displayName=\"XXX系统\" codeSystem=\"2.999.2.3.2.84\" codeSystemName=\"医院信息平台系统域代码表\"/>\n" +
            "\t\t</device>\n" +
            "\t</receiver>\n" +
            "\t<sender typeCode=\"SND\">\n" +
            "\t\t<telecom value=\"\"/>\n" +
            "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
            "\t\t\t<id root=\"2.999.1.98\" extension=\"HIP\"/>\n" +
            "\t\t\t<telecom value=\"设备编号\"/>\n" +
            "\t\t\t<softwareName code=\"HIP\" displayName=\"集成平台\" codeSystem=\"2.999.2.3.2.84\" codeSystemName=\"医院信息平台系统域代码表\"/>\n" +
            "\t\t</device>\n" +
            "\t</sender>\n" +
            "\t<controlActProcess classCode=\"CACT\" moodCode=\"APT\">\n" +
            "\t\t<authorOrPerformer typeCode=\"AUT\">\n" +
            "\t\t\t<signatureText>签名字符串</signatureText>\n" +
            "\t\t\t<assignedDevice classCode=\"ASSIGNED\"/>\n" +
            "\t\t</authorOrPerformer>\n" +
            "\t\t<subject typeCode=\"SUBJ\">\n" +
            "\t\t\t<request>\n" +
            "\t\t\t</request>\n" +
            "\t\t</subject>\n" +
            "\t</controlActProcess>\n" +
            "</PRPA_IN999999UV99>\n";

    public static void main(String[] args) {
        Map map = parseinHospital(textxml,"id");
        System.out.println(map.toString());
        Map map2 = parseAll(textxml,"sender",null);
        System.out.println(map2.toString());
    }

}
