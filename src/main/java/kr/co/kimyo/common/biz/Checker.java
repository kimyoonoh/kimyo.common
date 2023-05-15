package kr.co.kimyo.common.biz;

public class Checker {
	public static boolean isNull(Object value) {
		return value == null;
	}
	
	public static boolean isEmpty(Object str) {
		if (isNull(str)) return true;
		
		return str.isEmpty();
	}
	
	public static boolean eq(Object value, Object compValue) {
		if (isNull(value) && isNull(compValue)) return true;
		
		if (isNull(value)) return false;
		
		if (isNull(compValue)) return true;
		
		return compValue.equals(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> boolean isInclude(T value, T ... compValues) {
		if (isNull(value) && isNull(compValues)) return true;
		
		if (isEmpty(value)) return false;
		
		if (isNull(compValues)) return false;
		
		for (Object cv : compValues) {
			if (eq(cv, value)) return true;
		}
		
		return false;
	}
	
	public static boolean isExclude(String value, String ... compValues) {
		return !isInclude(value, compValues);
	}
	
	public static String header(String str, int len) {
		if (isEmpty(str)) return "";
		
		if (str.length() <= len) return str;
		
		return str.substring(0, len);
	}
	
	public static String header(String str) {
		return header(str, 1);
	}
		
	public static String footer(String str, int len) {
		if (isEmpty(str)) return "";
		
		if (str.length() <= len) return str;
		
		return str.substring(str.length() - len, str.length());
	}
	
	public static String choice(String key, String ... cvs) {
		int len = cvs.length;
		
		for (int i = 0; i < len; i += 2) {
			if (i + 1 == len) return cvs[i];
			
			if (isInclude(key, cvs[i].split(","))) return cvs[i + 1];
		}
		
		return "";
	}
}
