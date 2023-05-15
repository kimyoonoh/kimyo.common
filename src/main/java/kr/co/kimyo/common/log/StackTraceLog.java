package kr.triniti.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import kr.triniti.common.util.JSONUtil;
import kr.triniti.common.util.StringUtil;

/**
 * <pre>
 * kr.triniti.common.exception.StackTraceLog
 * 
 * Exception의 StackTrace 내용을 모두 출력해주는 로그
 * 
 * </pre>
 * @Author  : Kim yunoh
 * @Date    : 2021. 3. 3.
 * @Version : 1.0.0
 */
public class StackTraceLog {
	static Logger log = LoggerFactory.getLogger(StackTraceLog.class);
	
	final static int LIMIT_COLUMN = 120;
	final static String LINE_SEP_CH = "=";
	
	final static String LINE_SEP = StringUtil.repeat(LINE_SEP_CH, LIMIT_COLUMN) + "\n";
	
	public static Logger getLogger() {
		if (log == null) log = LoggerFactory.getLogger(StackTraceLog.class);
		
		return log;
	}
	
	public static void setLogger(Logger logger) {
		log = logger;
	}
	
	public static String getStackCall(Exception e) {
		return getStackCall(e, "", null);
	}
	
	public static String getStackCall(Exception e, String appendText) {
		return getStackCall(e, appendText, null);
	}
	
	public static String getStackCall(Exception e, Object param) {
		return getStackCall(e, "", param);
	}
	
	private final static String MESSAGE_HEADER = "Exception : [%s] / Message : [%s]\n";
	private final static String MESSAGE_TRACE  = "%s.%s(%d)\n";
	
	public static String getStackCall(Exception e, String appendText, Object param) {
		StringBuffer buff = new StringBuffer();
		
		buff.append(String.format(MESSAGE_HEADER, e.getClass().getSimpleName(), e.getMessage()));
		
		// Exception 호출 내용
		for (StackTraceElement el : e.getStackTrace()) {
			buff.append(String.format(MESSAGE_TRACE, el.getClassName(), el.getMethodName(), el.getLineNumber()));
		}

		// 사용자 추가 메세지
		if (StringUtil.isEmpty(appendText)) {
			buff.append(appendText);
		}
		
		// 사용자 입력 파라미터
		if (param != null) {
			buff.append(JSONUtil.objToStr(param));
		}
		
		return buff.toString();
	}
	
	public static boolean isCorrectLevel(Level level) {
//		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(StackTraceLog.class);
//		
//		ch.qos.logback.classic.Level internalLevel = ch.qos.logback.classic.Level.toLevel(Level.DEBUG.toInt()); 
//		
//		if (((ch.qos.logback.classic.Logger) log).getLevel() == null) logger.setLevel(internalLevel);
//		
//		if (!((ch.qos.logback.classic.Logger) log).getLevel().isGreaterOrEqual(internalLevel)) return true;
		
//		return false;
		return true;
	}
	
	public static void info(Exception e) {
		info(e, "");
	}
	
	public static void info(Exception e, String message) {
		//if (!isCorrectLevel(Level.INFO)) return;
			
		log.info(getStackCall(e, message));
	}
	
	public static void debug(Exception e) {
		debug(e, "");
	}
	
	public static void debug(Exception e, String message) {
		//if (!isCorrectLevel(Level.DEBUG)) return;
		
		log.debug(getStackCall(e, message));
	}
	
	public static void warn(Exception e) {
		warn(e, "");
	}
	
	public static void warn(Exception e, String message) {
		//if (!isCorrectLevel(Level.WARN)) return;
		
		log.warn(getStackCall(e, message));
	}
	
	public static void error(Exception e) {
		error(e, "");
	}
	
	public static void error(Exception e, String message) {
		//if (!isCorrectLevel(Level.ERROR)) return;
		
		log.error(getStackCall(e, message));
	}
}