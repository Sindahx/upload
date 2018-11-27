package com.chrl.bs.sfs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脱敏处理。。
 * Created by chenrl on 2015/11/3.
 */
public class HideInfoUtils {

    public static String hide( String  str){

        str = hideByField(str, "NAME" , 1 );
        str = hideByField(str, "LINK_ADDRESS" , 6 );
        str = hideByField(str, "ID_NUMBER" , 6 );
        str = hideByField(str, "HOME_TEL" , 6 );
        str = hideByField(str, "SELF_TEL" , 6 );
        str = hideByField(str, "KEEPER_TEL" , 6 );
        str = hideByField(str, "MATE_TEL" , 6 );
        str = hideByField(str, "TEL" , 6 );

//        Debug.out(str);
        return str;
    }

    /**
     * 根据字段名隐藏数据
     *  String str = "[{\"MARITAL_STATUS\":\"已婚\",\"LINK_ADDRESS\":\"广东省佛山市\",\"EMAIL_ADDR\":\"\",\"DRIVING_LICENSES_NO\":\"\",\"EHR_DOCU_DATE\":
     *  \"2015-08-19 23:06:41\",\"AREA_TEL\":\"\",\"ALERGY\":\"\",\"NAME_WORKUNIT\":\"\",\"HOME_TEL\":\"\",\"ID_NUMBER\":\"\",\"NATION_NAME\":\"汉族\",
     *  \"SNO\":\"3368788504\",\"TEL\":\"\",\"SEX\":\"1\",\"FBLOOD\":\"*?\",\"CITIZENSHIP\":\"-?\",\"KEEPER_TEL\":\"\",\"PASSPORT_NO\":\"\",
     *  \"OFFICER_NO\":\"\",\"HK_MA_ID_NO\":\"\",\"BIRTHDAY\":\"1957-05-04\",\"REGISTERED_ADDRESS_SORT\":\"\",\"ZIP_CODE\":\"528000\",\"ED_BACKGROUND\":\"\",
     *  \"NAME\":\"3368788504\",\"WORK_TEL\":\"\",\"guid\":\"839ed1f4-d711-4644-9177-47b30b300714\",\"INSURANCE_NAME\":\"\",\"PERSON_ID\":\"33687885\",
     *  \"INSURANCE_TYPE\":\"自费\",\"DISABILITY_CERTIFICATE_NO\":\"\",\"ORG_CODE\":\"43521\",\"MATE_TEL\":\"\",\"HOUSEHOLD_REGISTER_NO\":\"\",\"INSURANCE_NO\":\"\"}]";
     * @param str  要隐藏的是数据
     * @param field 要隐藏的字段
     * @param keep 要保留几位
     * @return 替换过的字符串
     */
    public static String hideByField(String str,String field,int keep){
        Pattern pattern = Pattern.compile("\""+field+"\":\"?(.+?)\"");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            String value = matcher.group(1);
            if ("\",".equals(value)){
                continue;
            }
//            Debug.out(value);
            if (value.length()>keep) {
                String temp = "";
                int length = value.length()-keep;
                int i = 0 ;
                while (i<length){
                    temp+="*";
                    i++;
                }
                str = str.replace(value, value.substring(0, keep) + temp);
            }
        }
        return str;
    }

    public static void main(String[] args) {
        String str = "[{\"MARITAL_STATUS\":\"已婚\",\"LINK_ADDRESS\":\"广东省佛山市\",\"EMAIL_ADDR\":\"\",\"DRIVING_LICENSES_NO\":\"\",\"EHR_DOCU_DATE\":\"2015-08-19 23:06:41\",\"AREA_TEL\":\"\",\"ALERGY\":\"\",\"NAME_WORKUNIT\":\"\",\"HOME_TEL\":\"\",\"ID_NUMBER\":\"\",\"NATION_NAME\":\"汉族\",\"SNO\":\"3368788504\",\"TEL\":\"\",\"SEX\":\"1\",\"FBLOOD\":\"*?\",\"CITIZENSHIP\":\"-?\",\"KEEPER_TEL\":\"\",\"PASSPORT_NO\":\"\",\"OFFICER_NO\":\"\",\"HK_MA_ID_NO\":\"\",\"BIRTHDAY\":\"1957-05-04\",\"REGISTERED_ADDRESS_SORT\":\"\",\"ZIP_CODE\":\"528000\",\"ED_BACKGROUND\":\"\",\"NAME\":\"3368788504\",\"WORK_TEL\":\"\",\"guid\":\"839ed1f4-d711-4644-9177-47b30b300714\",\"INSURANCE_NAME\":\"\",\"PERSON_ID\":\"33687885\",\"INSURANCE_TYPE\":\"自费\",\"DISABILITY_CERTIFICATE_NO\":\"\",\"ORG_CODE\":\"43521\",\"MATE_TEL\":\"\",\"HOUSEHOLD_REGISTER_NO\":\"\",\"INSURANCE_NO\":\"\"}]";
        Debug.out(HideInfoUtils.hide(str));

    }
}
