package com.zxyl.utils;



/**
 * Created by GOODBOY on 2016/12/9.
 */


public class PathologyUtil {


    public static String chechDb_response = "<MCCI_IN999999UV99>\n" +
            "\t<!-- UUID,本条消息的交互实例唯一码，非常重要-->\n" +
            "\t<id root=\"2.999.1.96\" extension=\"uuid_value\"/>\n" +
            "    <!--本条消息的发送时间，精确到毫秒-->\n" +
            "\t<creationTime value=\"creationTime_value\"/>\n" +
            "\t<interactionId root=\"2.16.840.1.113883.1.6\" extension=\"MCCI_IN999999UV99\"/>\n" +
            "\t<receiver typeCode=\"RCV\">\n" +
            "\t\t<telecom value=\"\"/>\n" +
            "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
            "\t\t\t<id root=\"2.999.1.97\" extension=\"report_extension\"/>\n" +
            "\t\t\t<telecom value=\"设备编号\"/>\n" +
            "\t\t\t<softwareName code=\"report_code\" displayName=\"report_displayName\" codeSystem=\"2.999.2.3.2.84\" codeSystemName=\"医院信息平台系统域代码表\"/>\n" +
            "\t\t</device>\n" +
            "\t</receiver>\n" +
            "\t<sender typeCode=\"SND\">\n" +
            "\t\t<telecom value=\"\"/>\n" +
            "\t\t<device classCode=\"DEV\" determinerCode=\"INSTANCE\">\n" +
            "\t\t\t<id root=\"2.999.1.98\" extension=\"TIS\"/>\n" +
            "\t\t\t<telecom value=\"设备编号\"/>\n" +
            "\t\t\t<softwareName code=\"TIS\" displayName=\"远程医疗\" codeSystem=\"2.999.2.3.2.84\" codeSystemName=\"医院信息平台系统域代码表\"/>\n" +
            "\t\t</device>\n" +
            "\t</sender>\n" +
            "\t<!--成功链接到数据库返回:AA；失败：AE-->\n" +
            "\t<acknowledgement typeCode=\"typeCode_value\">\n" +
            "\t\t<targetMessage>\n" +
            "\t\t\t<!--请求的消息ID，非常非常重要-->\n" +
            "\t\t\t<id root=\"2.999.1.96\" extension=\"report_id_value\"/>\n" +
            "\t\t</targetMessage>\n" +
            "\t\t<acknowledgementDetail>\n" +
            "\t\t\t<!--链接到数据库结果描述，异常时将异常内容反馈-->\n" +
            "\t\t\t<text>text_value</text>\n" +
            "\t\t\t<resultMessage>\n" +
            "\t\t\t</resultMessage>\n" +
            "\t\t</acknowledgementDetail>\n" +
            "\t</acknowledgement>\n" +
            "</MCCI_IN999999UV99>\n";

}
