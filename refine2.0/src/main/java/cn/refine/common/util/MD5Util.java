package cn.refine.common.util;

import java.security.MessageDigest;


/**
*   *字符加密)
* @author zhuxiange
*   2010-7-16 下午04:53:32
*  
*/
public class MD5Util {
	/**
	 * MD5:32位方法对指定字符串做MD5加密算法字符串转换
	 */
	public final static String getMD5ofStr(String param) {
		
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = param.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			String res = new String(str);
			return res;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}


	public static void main(String args[]) {
		System.out.println("MD5>:32位="+MD5Util.getMD5ofStr("111111"));
	}
}
