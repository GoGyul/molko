package com.sbs.untact.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		return format1.format(time);
	}
	
	public static Map<String, Object> mapOf(Object... args){
		
		if(args.length % 2 != 0 ) {
			throw new IllegalArgumentException("인자를 짝수개");
		}
		
		int size = args.length / 2;
		
		Map<String, Object> map = new LinkedHashMap<>();
		
		for(int i = 0; i < size; i++) {
			int keyIndex = i * 2;
			int valueIndex = keyIndex + 1;
			
			String key;
			Object value;
			
			try {
				key = (String)args[keyIndex];
			}catch(ClassCastException e) {
				throw new IllegalArgumentException("키는 String으로 넣으세요");
			}
			value = args[valueIndex];
			map.put(key, value);
		}
		return map;
	}
	
	// int 로 바꿔주는 함수
	public static int getAsInt(Object object, int defaultValue) {
		if(object instanceof BigInteger) {
			return ((BigInteger) object).intValue();
		}else if(object instanceof Double) {
			return (int) Math.floor((double)object);
		}else if(object instanceof Float) {
			return (int) Math.floor((float)object);
		}else if (object instanceof Long) {
			return (int)object;
		}else if (object instanceof Integer) {
			return (int) object;
		}else if (object instanceof String) {
			return Integer.parseInt((String)object);
		}
		return defaultValue;
	}
}
