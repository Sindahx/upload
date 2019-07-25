package com.leaihealth.cloud.util.tools;

public class StringSubUtil {
	
	public static String stringSub(String str){
		String subStr = "";
		for(int i=0; i<str.length();i++){
			subStr+=str.substring(i,str.length())+",";
		}
		subStr = subStr.substring(0, subStr.length()-1);
		return subStr;
	}
	
	public static String pinyinSub(String str){
		String subStr = "";
		String[] allpy = new String [str.length()];
		String[] py = new String[str.length()];
		for(int i=0; i<str.length();i++){
			allpy[i] = PinYin.getPinYin(String.valueOf(str.charAt(i)));
			py[i] = PinYin.getPinYinHeadChar(String.valueOf(str.charAt(i)));
		}
		String sub = "";
		for(int i=0; i<str.length();i++){
			for(int k=0;k<i+1;k++){
				sub += allpy[k];
			}
			for(int j=i+1;j<str.length();j++){
				sub += py[j];
			}
			subStr+=sub+",";
			sub = "";
		}
		for(int i=0; i<str.length();i++){
			for(int k=0;k<i+1;k++){
				sub += py[k];
			}
			for(int j=i+1;j<str.length();j++){
				sub += allpy[j];
			}
			subStr+=sub+",";
			sub = "";
		}
		subStr = subStr.substring(0, subStr.length()-1);
		return subStr;
	}
	
	public static void main(String[] args) {
		System.out.println(pinyinSub("邓紫莹"));
	}
}
