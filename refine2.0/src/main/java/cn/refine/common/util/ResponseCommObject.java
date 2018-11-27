package cn.refine.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseCommObject {
	public final static String Rows= "Rows";
	public final static String Total= "Total";
	public final static String Code= "code";
	public final static String Msg= "msg";
	
	public final static int CODE_SUCCESS =1;
	public final static int CODE_FAIL =0;
	
	private Map<String,Object> map = new HashMap<String,Object>(2);
	
	public static Map<String, Object> createListToMap(List<?> list){
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put(Rows, list);
		map.put(Code, CODE_SUCCESS);
		return map;
	}

	public static Map<String, Object> createListToMap(List<?> list,int count){
		Map<String,Object> map = new HashMap<String,Object>(2);
		map.put(Rows, list);
		map.put(Total, count);
		map.put(Code, CODE_SUCCESS);
		return map;
	}
	
	public static Map<String, Object> success(){
		Map<String,Object> map = new HashMap<String,Object>(0);
		map.put(Code, CODE_SUCCESS);
		return map;
	}
	
	public static Map<String, Object> fail(String msg){
		Map<String,Object> map = new HashMap<String,Object>(0);
		map.put(Code, CODE_FAIL);
		map.put(Msg, msg);
		return map;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	public void putObjectMap(String key, Object value){
		this.map.put(key, value);
	}
	
	public void putRowsTotalToMap(List<?> list){
		this.map.put(Rows, list);
		this.map.put(Total, list.size());
	}
	
	public void putRowsToMap(Object value){
		this.map.put(Rows, value);
	}
	public void putTotalToMap(int total){
		this.map.put(Total, total);
	}
	
	public void setCode(int code){
		this.map.put(Code, code);
	}
}
