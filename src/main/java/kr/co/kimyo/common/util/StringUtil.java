package kr.triniti.common.util;

import java.io.ByteArrayOutputStream;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
//import java.nio.ByteBuffer;
//import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringEscapeUtils;

import kr.triniti.common.model.vo.AbstractVO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings({ "deprecation", "restriction" })
public class StringUtil {
    public static final String DEFAULT_CHAR_SET = "UTF-8";

    public static boolean isNotEmpty(String str) {
    	return !isEmpty(str);
    }
    
    public static boolean isEmpty(StringBuffer str) {
        if (str == null)   return true;
        if (str.length() == 0) return true;
        
        int length = str.length();
        for (int i = 0; i < length; i++) {
        	if (str.charAt(i) != ' ') return false;
        }
        
        return true;
    }
    
    public static boolean isEmpty(String str) {
        if (str == null)   return true;
        if (str.isEmpty()) return true;

        // 공백이 아닌 문자열이 있다면 빈 문자열이 아니다.
        // charArray 전환 동작도 많은 시간이 걸림. charAt을 쓰는게 제일 빠름
        int length = str.length();
        for (int i = 0; i < length; i++) {
        	if (str.charAt(i) != ' ') return false;
        }
        
        // trim 실행시 대량의 문자열일 경우 많은 시간 소요됨.. 
        //if (str.trim().isEmpty()) return true;
        
        return true;
    }
    
    public static String value(String str, String defaultValue) {
        return (String) value(Boolean.valueOf(isEmpty(str)), defaultValue);
    }

    public static Object value(Object obj, Object defaultObj) {
        return obj == null ? defaultObj : obj;
    }

    public static String repeat(String str, int times) {
        if (times <= 0) return "";
        
        return new String(new char[times]).replace("", str);
    }

    public static String lPad(String str, int length, String padStr) {
        int repeatTimes = length - str.length();
        
        return repeat(padStr, repeatTimes) + str;
    }

    public static String rPad(String str, int length, String padStr) {
        int repeatTimes = length - str.length();
        if (repeatTimes <= 0) {
            return str;
        }
        return str + repeat(padStr, repeatTimes);
    }

    public static String capitalize(String str) {
        char[] chars = str.toCharArray();

        chars[0] = Character.toUpperCase(chars[0]);

        return new String(chars);
    }

    public static String appendPrefix(String prefix, String str) {
        return prefix + capitalize(str);
    }

    public static String setter(String member) {
        return appendPrefix("set", member);
    }

    public static String getter(String member) {
        return appendPrefix("get", member);
    }

	public static byte[] fromBase64(String content) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(content);
        } catch (IOException localIOException) {
        }
        return "".getBytes();
    }

    public static byte[] fromBase64(byte[] content) {
        return fromBase64(content.toString());
    }

    public static String toBase64(String content) {
        return toBase64(content.getBytes());
    }

	public static String toBase64(byte[] content) {
		BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(content);
    }

    public static String removeHTMLTag(String str) {
        return str.replaceAll("<[^>]*>", "").replaceAll("'", "‘");
    }

    public static String removeNewLine(String str) {
        return str.replaceAll("\n", "");
    }

    public static String transPureText(String str) {
        if (str == null) {
            return "";
        }
        return removeNewLine(removeHTMLTag(str.trim()));
    }

	public static String escapeHTML(String str) {
        return StringEscapeUtils.escapeHtml4(str);
    }

	public static String escapeJava(String str) {
        return StringEscapeUtils.escapeJava(str);
    }

    public static int position(String source, String part) {
        if (isEmpty(source)) {
            return -1;
        }
        if (isEmpty(part)) {
            return -1;
        }
        return source.toLowerCase().indexOf(part.toLowerCase());
    }

    public static boolean isContain(String source, String part) {
        return position(source, part) > -1;
    }

    public static String encodeURL(String url) {
        return encodeURL(url, "UTF-8");
    }

    public static String encodeURL(String str, String charSet) {
        try {
            return URLEncoder.encode(str, charSet);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static String decodeURL(String str) {
        return decodeURL(str, "UTF-8");
    }

    public static String decodeURL(String str, String charSet) {
        try {
            return URLDecoder.decode(str, charSet);
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

    public static String mapping(String str, Map<String, Object> paramMap) {
        Set<String> keys = paramMap.keySet();
        for (String key : keys) {
            str = str.replaceAll("\\{" + key + "\\}", String.valueOf(paramMap.get(key)));
        }
        return str;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String mapping(String str, AbstractVO vo) {
        Class t = vo.getClass();

        Field[] fields = t.getDeclaredFields();
        for (Field field : fields) {
            String methodName = appendPrefix("get", field.getName());
            try {
                Method method = t.getMethod(methodName, new Class[0]);
                if (method != null) {
                    str = str.replaceAll("\\{" + field.getName() + "\\}", String.valueOf(method.invoke(vo, new Object[0])));
                }
            } catch (Exception e) {
                str = str.replaceAll("\\{" + field.getName() + "\\}", "PARAM_ERROR");
            }
        }
        return str;
    }
    
    public final static Charset UTF8_CharSet  = Charset.forName("UTF-8");
    public final static Charset EUC_CharSet   = Charset.forName("EUC-KR");
    public final static Charset MS949_CharSet = Charset.forName("x-windows-949");
    
    public static String transCharset(String str, Charset source, Charset target) {
		ByteArrayOutputStream requestOutputStream = new ByteArrayOutputStream();
		
		try {
			requestOutputStream.write(str.getBytes(source.name()));
			return requestOutputStream.toString(source.name());
		} catch (Exception e) {
		}
		
		return "";
    }
    
    public static String encode(String str, String charset) {
   		return encode(str.getBytes(), charset);
    }
    
    public static String encode(byte [] chars, String charset) {
    	try {
    		return new String(chars, charset);
    	} catch (UnsupportedEncodingException e) {}
    	
    	return new String(chars);
    }
    
//    public static String transEncoding(String src, String srcEncode, String tarEncode) {
//    	try {
//			byte [] srcByteArray = src.getBytes(srcEncode);
//			
//			CharBuffer cb = CharBuffer.wrap(new String(srcByteArray, srcEncode).toCharArray());
//			
//			Charset tarCharset = Charset.forName(tarEncode);
//			ByteBuffer bf = tarCharset.encode(cb);
//			
//			String fileName = "./temp.txt";
//			FileUtil.save(fileName, bf.array());
//			
//			String result = FileUtil.read(new FileInputStream(fileName), tarEncode); 
//			
//			//FileUtil.delete(fileName);
//			
//			return result;
//    	} catch (Exception e) {}
//    	
//    	return src;
//    }
    
    //private final static String LINE_DIVIDE_LETTER = "(?:\\.\\s)|[\\?\\|\t!,;:]";
    //private final static String LINE_DIVIDE_LETTER = "(\\.\\s|\\?|\\!|,)";
    //private final static String LINE_DIVIDE_LETTER = "\\.\\s|?|!|,\\n";
    private final static String LINE_DIVIDE_LETTER = "(:|\\s|\\?|!|\\W[.,]\\W?)";
    //private final static String REMOVE_LETTER = "\"|\'|\\||\\(|\\)|\\[|\\]|\\{|\\}";
    //private final static String REMOVE_LETTER = "([^가-힣\\w\\s\\+\\?\\.\\!\\%\\-,])";
    private final static String REMOVE_LETTER = "([^가-힣?.!,\\w\\s])";
    public final static int MAX_CHAPTER_LENGTH = 20 * 1024;
    public final static int MAX_LINE_LENGTH = 100;
    
    public static String compact(String text) {
    	return text
    			.replaceAll(REMOVE_LETTER, " ")  // 한글 영문자, 개행 기호외에 전부 삭제
    			.replaceAll("\\b([가-힣a-zA-Z\\?\\.\\!])\\b", " ") // 1글자로 구성된 문자 삭제
    			.replaceAll("\\s[.,?!]\\s", " ") // . 문자 삭제
    			;
    }
    
    public static List<String> textLines(String text) {
    	List<String> textLines = new ArrayList<String>();
    	
    	//String divideText = compact(text).replaceAll(LINE_DIVIDE_LETTER, "$1\n");
    	String [] splitText = compact(text).replaceAll("\\s{2,}", " ").trim().split("\n");
    	for (String line : splitText) {
    		if (StringUtil.isEmpty(line)) continue;
    		
    		if (line.length() > 128) {
    			textLines.addAll(Arrays.asList(line.split(LINE_DIVIDE_LETTER)));
    		} else {
    			textLines.add(line);
    		}
    	}
    	
    	return textLines;
    }
    
    public static List<String> textBlocks(String text, String splitPattern) {
    	return Arrays.asList(text.split(splitPattern));
    }
    
    public static List<String> textBlocks(List<String> lines, int limit) {
    	List<String> blocks = new ArrayList<String>();
    	
    	StringBuffer buff = new StringBuffer();
    	int blockSize = 0; 
    	for (String line : lines) {
    		int lineSize = line.getBytes().length;
    		
    		if ((blockSize + lineSize) >= limit) {
    			blocks.add(buff.toString());
    			blockSize = lineSize;
    			buff.setLength(0);
    		} else {
    			blockSize += lineSize;
    		}
    		
    		buff.append(line).append("\n");
    	}
    	
    	blocks.add(buff.toString());
    	
    	return blocks;
    }
    
    private final static int [] CHAR_RANGE = {
    	 0x0000,   0x007f, 1, // 일반 아스키 코드
    	 0x0800,   0x07ff, 2,
    	 0x0800,   0xffff, 3,
    	0x10000, 0x10ffff, 4  
    };
    
    private static int utf8CharBytes(char c) {
    	int code = (int) c;
    	
    	for (int i = 0; i <= 6; i += 3) {
    		if ((CHAR_RANGE[i] <= code) && (code <= CHAR_RANGE[i + 1])) return CHAR_RANGE[i + 2]; 
    	}
    	
    	return -1;
    }
    
    public static List<String> textChunkList(String str, int size) {
    	List<String> chunks = new ArrayList<String>();
    	
    	StringBuffer buff = new StringBuffer();
    	
    	int textLength = str.length();
    	int chunkSize = 0;
    	for (int i = 0; i < textLength; i++) {
    		char ch = str.charAt(i);
    		
    		int bytes = utf8CharBytes(ch);
    		
    		if (bytes == -1) continue;
    		
    		chunkSize += bytes;
    		
    		if (chunkSize >= size) {
    			chunks.add(buff.toString());
    			chunkSize = bytes;
    			buff.setLength(0);    			
    		}
    		
   			buff.append(ch);
    	}
    	
    	chunks.add(buff.toString());
    	
    	return chunks;
    }
    
    public static List<String> divideLine(String line, int lengthLimit) {
    	List<String> chunks = new ArrayList<String>();
    	
    	// 지정된 길이 이내라면 바로 넘긴다.
    	if (line.length() < lengthLimit) {
    		chunks.add(line);
    	} else {
    		// 지정된 길이 이상이면 분리를 한다.
    		String [] lineParts = line.split(LINE_DIVIDE_LETTER);
    		
    		StringBuffer buff = new StringBuffer();
    		int partJoinLength = 0;
    		for (int i = 0; i < lineParts.length; i++) {
    			int partLength = lineParts[i].length();
    			
    			if ((partJoinLength + partLength) < lengthLimit) {
    				buff.append(lineParts[i]).append(" ");
    				partJoinLength += partLength;
    			} else {
    				chunks.add(buff.toString());
    				buff.setLength(0);
    				buff.append(lineParts[i]).append(" ");
    				partJoinLength = partLength;
    			}
    		}
    		
    		chunks.add(buff.toString());
    	}
    	
    	return chunks;
    }
    
    public static String [] extractLine(String pattern, String content) {
    	return extractLine(Pattern.compile(pattern), content);
    }
    
    public static String [] extractLine(Pattern pattern, String content) {
    	Matcher m = pattern.matcher(content);
    	
    	if (m.find()) {
    		int count = m.groupCount();
    		
    		// 정규식 패턴에 그룹 설정이 안되어 있으면 매칭된 텍스트 전체를 하나의 그룹으로 잡는다.
    		if (count == 0) {
    			String [] parts = new String[1];
    			parts[0] = m.group();
    			return parts;
    		}
    		
    		String [] parts = new String[count];
    		
    		for (int i = 0; i < count; i++) {
    			parts[i] = m.group(i + 1);
    		}
    		
    		return parts;
    	}
    	
    	return new String[0];
    }
    
    public static List<String []> extract(String pattern, String content) {
    	return extract(Pattern.compile(pattern), content);
    }
    
    public static List<String []> extract(Pattern pattern, String content) {
    	List<String []> list = new ArrayList<String []>();
    	
    	Matcher m = pattern.matcher(content);
    	
    	while (m.find()) {
    		int count = m.groupCount();
    		String [] parts = new String[count];
    		
    		for (int i = 0; i < count; i++) {
    			parts[i] = m.group(i + 1);
    		}
    		
    		list.add(parts);
    	}
    	
    	return list;
    }
    
    public static String concat(Collection<String> strs) {
    	return concat(strs, ",");
    }
    
    public static String concat(Collection<String> strs, String sep) {
    	StringBuffer buff = new StringBuffer();
    	
    	Object [] list = strs.toArray();
    	for (int i = 0; i < list.length; i++) { 
    		buff.append(list[i]);
    		
    		if (i < (list.length - 1)) buff.append(sep);
    	}
    	
    	return buff.toString();
    }
    
    public final static String NORMAL_DATETIMEMIL_FORMAT   = "%04d-%02d-%02d %02d:%02d:%02d.%03d";
    public final static String NORMAL_DATETIME_FORMAT      = "%04d-%02d-%02d %02d:%02d:%02d";
    public final static String COMPACT_DATETIMEMIL_FORMAT  = "%04d%02d%02d%02d%02d%02d%03d";
    public final static String COMPACT_DATETIME_FORMAT     = "%04d%02d%02d%02d%02d%02d";
    public final static String NORMAL_DATE_FORMAT      = "%04d-%02d-%02d";
    public final static String COMPACT_DATE_FORMAT     = "%04d%02d%02d";
    
    public static String getTimestampToDate(long timestamp, String format) {
    	Calendar c = Calendar.getInstance();
    	
    	c.setTimeInMillis(timestamp);

    	return String.format(format, 
	    	c.get(Calendar.YEAR),
	    	c.get(Calendar.MONTH) + 1,
	    	c.get(Calendar.DATE),
	    	c.get(Calendar.HOUR_OF_DAY),
	    	c.get(Calendar.MINUTE),
	    	c.get(Calendar.SECOND),
	    	c.get(Calendar.MILLISECOND)
    	);
    }
    
    public static String getTimestampToDate(long timestamp) {
    	return getTimestampToDate(timestamp, NORMAL_DATETIME_FORMAT);
    }
    
    public static String getTimestampToDate() {
    	return getTimestampToDate(System.currentTimeMillis(), COMPACT_DATETIME_FORMAT);
    }
    
    static class SubText {
    	int start;
    	int end;
    	String [] text;
    }
    
    private static String [] Tokens = {
    	"#token1", "#token2", "#token3", "#token4", "#token5", "#token6", "#token7", "#token8", "#token9"
    };
    
    private static Pattern patRevert = Pattern.compile("(?<=\\w+=')([0-9A-Z]+?)(?=')");
    
    public static String revertMarkingTextValue(String source) {
    	Matcher m = patRevert.matcher(source);
    	String target = new String(source);
    	
    	while (m.find()) {
    		String token = m.group();
    		String value = new String(hexStrToBytes(token));
    		
    		target = target.replaceAll(token, value);
    	}
    	
    	return target;
    }
    
    public static String removeUnusedMarkingText(String source) {
    	return source
				.replaceAll("^[^<]*", "")
				.replaceAll("(?<=>)[^<]+(?=<)", "<sep/>")
				.replaceAll("[^>]*$", "");
    }
    
    public static String markingTextValue(String source, Pattern pat, String replacePattern) {
    	StringBuffer       srcText  = new StringBuffer(source);
    	ArrayList<SubText> subTexts = new ArrayList<SubText>();
    	
    	Matcher m = pat.matcher(source);
    	
    	while (m.find()) {
    		if (StringUtil.isEmpty(m.group().trim())) continue;
    		
    		SubText sub = new SubText();
    		
    		sub.start = m.start();
    		sub.end = m.end();
    	
    		if (m.groupCount() > 0) {
				String [] list = new String[m.groupCount()];
				
				for (int i = 0; i < list.length; i++) {
					list[i] = m.group(i + 1);
				}
    	
				sub.text = list;
			} else {
				String [] list = new String[1];
				
				list[0] = m.group();
				
				sub.text = list;
			}
    		
			subTexts.add(sub);
    	}

		for (int i = subTexts.size() - 1; i >= 0; i--) {
			SubText sub = subTexts.get(i);
	
			String replaceText = replacePattern;
		
			for (int j = 0; j < sub.text.length; j++) {
				String value = StringUtil.isEmpty(sub.text[j]) ? "" : bytesToHexStr(sub.text[j].trim().getBytes());
			
				replaceText = replaceText.replaceAll(Tokens[j], value);
			}
		
			srcText.replace(sub.start, sub.end, replaceText);
		}
	
    	return srcText.toString();
    }
    
    /**
     * ascii 코드 '0' : 48, 'A' : 64, 'a' : 96 임
     * 
     * char로 들어온  문자로 16진 값을 구하는 테이블
     * 
     * a7 이면  ascii 코드 96, 55를 인덱스로 값을 검색하면 10, 7 이 나옴. 
     */
    public final static byte [] ASC_HEX_TABLES = {
     // 0   1   2   3   4   5   6   7   8   9
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0, //  0
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0, // 10
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0, // 20
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0, // 30
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  1, // 40
    	2,  3,  4,  5,  6,  7,  8,  9,  0,  0, // 50
    	0,  0,  0,  0,  0, 10, 11, 12, 13, 14, // 60
       15,  0,  0,  0,  0,  0,  0,  0,  0,  0, // 70
    	0,  0,  0,  0,  0,  0,  0,  0,  0,  0, // 80
    	0,  0,  0,  0,  0,  0,  0,  0, 11, 12, // 90
       13, 14, 15
    };  
    
    // 바이트 배열에서 바이트 값으로 인덱싱하면 16진 문자열로 반환
    public final static String [] BIN_TABLES = {
    //    0     1     2     3     4     5     6     7     8     9     A     B     C     D     E     F
    	"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", // 0
    	"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F", // 1
    	"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", // 2
    	"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", // 3
    	"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F", // 4
    	"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", // 5
    	"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", // 6
    	"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", // 7
    	"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F", // 8
    	"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F", // 9
    	"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF", // A
    	"B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", // B
    	"C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", // C
    	"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", // D
    	"E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF", // E
    	"F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF"  // F
    };
    
    // 16진 -> 2진 매핑 테이블
    public final static String [] HEX_BIN_TABLES = {
    	"0000", "0001", "0010", "0011",
    	"0100", "0101", "0110", "0111",
    	"1000", "1001", "1010", "1011",
    	"1100", "1101", "1110", "1111"
    };
    
    // 2진 -> 16진 매핑 테이블
    public final static String [] BIN_HEX_TABLES = {
    	"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
    };
    
    
    public static byte [] hexStrToBytes(String hexStr) {
    	char [] hexs = hexStr.toCharArray();
    	byte [] bytes = new byte[hexs.length / 2];
    	
    	for (int i = 0, j = 0; i < hexs.length; i += 2, j++) {
    		bytes[j] = (byte) ((ASC_HEX_TABLES[hexs[i]] << 4) + ASC_HEX_TABLES[hexs[i + 1]]);
    	}
    	
    	return bytes;
    }
    
    public static String bytesToHexStr(byte [] bins) {
    	StringBuffer buff = new StringBuffer();
    	
    	for (int i = 0; i < bins.length; i++) {
    		int hexIndex = bins[i] >= 0 ? bins[i] : bins[i] + 256;
    		
    		buff.append(BIN_TABLES[hexIndex]);
    	}
    	
    	return buff.toString();
    }
    
    public static String bytesToBinStr(byte [] bins) {
    	char [] hexStr =  bytesToHexStr(bins).toCharArray();
    	
    	StringBuffer buff = new StringBuffer();
    	for (int i = 0; i < hexStr.length; i++) {
    		char hexChar = hexStr[i];
    		
    		int hexValue = hexChar > '9' ? (hexChar - 'A') + 10 : hexChar - '0';  
    		
    		buff.append(HEX_BIN_TABLES[hexValue]);
    	}
    	
    	return buff.toString();
    }
    
    public static Integer[] kmpSearch(char[] ptrn, char[] txt) {
        if(ptrn.length >= txt.length) return new Integer[0];
        
        /*** [START] calculate pmt (partial match table) ***/
        int[] pmt = new int[ptrn.length + 1];
        pmt[0] = -1;
        for (int i = 1; i < ptrn.length + 1; i++) {  // i is MISMATCH position
            PMT_LOOP :
            for (int j = 0; j < i; j++) {            // j is length
                for (int k = 0; k < j; k++) {        // k for indexing ptrn
                    if (ptrn[k] != ptrn[i - j + k])  // if not with this j
                        continue PMT_LOOP;          // go to next j
                }
                
                pmt[i] = j;  // definitely MAX value because j is increasing
            }
        }
        /*** [ END ] calculate pmt (partial match table) ***/
        
        /*** [START] search pattern in text using pmt ***/
        List<Integer> res = new ArrayList<Integer>();
        
        int i = 0, j = 0;
        KMP_LOOP :
        while(i <= (txt.length - ptrn.length)) {  // i is pointer for txt
            while(j < ptrn.length) {              // j is pointer for ptrn
                if(ptrn[j] != txt[i + j]) {       // if MISMATCHED
                    i += (j - pmt[j]);            //    JUMP !!!!!!!!!
                    j = (j == 0) ? 0 : pmt[j];     //    SKIP !!!!!!!!!
                    continue KMP_LOOP;            //    continue with new i, j
                } else {                          // if MATCHED
                    j++;                          //    just j++
                }
            }
            
            res.add(i);                             // AT HERE, FULLY MATCHED
            i += (ptrn.length - pmt[ptrn.length]);  // to next i
            j = pmt[ptrn.length];                   // to next j
        }
        /*** [ END ] search pattern in text using pmt ***/
        
        return res.toArray(new Integer[0]);
    }
    
  
    // 문자열의 한글, 영문, 숫자, 특수문자로 나누어 정규식 패턴을 생성한다.
    public static Pattern getPattern(String source) {
    	return Pattern.compile(source
    		.replaceAll("([^가-힣a-zA-Z0-9\\s])", "\\\\$1")
    		.replaceAll("[0-9]+", "([0-9]+)")
    		.replaceAll("[가-힣]+", "([가-힣]+)")
    		.replaceAll("[a-zA-Z]+", "([a-zA-Z]+)")
    	);
    }
    
    private static int getCharType(char ch) {
    	if (ch >= 'a' && ch <= 'z')  return 1;
		if (ch >= 'A' && ch <= 'Z')  return 1;
		if (ch >= '0' && ch <= '9')  return 2;
		if (ch >= '가' && ch <= '힣') return 3;
		
		return 4;
    }
    
    public static ArrayList<String> splitText(String source) {
    	ArrayList<String> tokens = new ArrayList<String>();
    	
    	char [] letters = source.toCharArray();
    	StringBuffer buff = new StringBuffer();
    	
    	for (int i = 0; i < letters.length; i++) {
    		int charType = getCharType(letters[i]);
    		
    		int nextType = (i + 1) >= letters.length ? -1 : getCharType(letters[i + 1]);
    		
    		buff.append(letters[i]);
    		if (charType != nextType || charType == 4) {
    			tokens.add(buff.toString());
    			buff.setLength(0);
    		}
    	}
    	
    	return tokens;
    }
    
    public static Pattern getPattern(String source, String target) {
    	ArrayList<String> sourceTokens = splitText(source);
    	ArrayList<String> targetTokens = splitText(target);
    	
    	StringBuffer buff = new StringBuffer();
    	
    	for (int i = 0; i < sourceTokens.size(); i++) {
    		String sToken = sourceTokens.get(i);
    		String tToken = i < targetTokens.size() ? targetTokens.get(i) : ""; 
    		
    		int charType = getCharType(sToken.toCharArray()[0]);
    		
    		if (sToken.equals(tToken)) {
    			if (charType == 4) {
    				buff.append("\\").append(sToken);
    			} else {
    				buff.append(sToken);
    			}
    		} else {
    			switch (charType) {
    			case 1 : buff.append("([a-zA-Z]{1,").append(sToken.length()).append("})");
    				break;
    			case 2 : buff.append("(\\d{1,").append(sToken.length()).append("})");
					break;
    			case 3 : buff.append("([가-힣]{1,").append(sToken.length()).append("})");
					break;
				default : buff.append("(\\").append(sToken).append(")");
    			}
    		}
    	}
    	
    	return Pattern.compile(buff.toString());
    }
    
    private static ScriptEngine engine = (new ScriptEngineManager()).getEngineByName("JavaScript");
    public static Object eval(String expText) {
    	try {
			return engine.eval(expText);
		} catch (ScriptException e) {
			return expText;
		}
    }
    
    // 중복된 라인을 1개만 남기고 제거한다.
    public static String dedupLine(String context) {
    	String [] lines = context.split("\n");
    	HashMap <String, String> result = new HashMap<String, String>();
    	
    	for (String line : lines) {
    		result.put(line, line);
    	}
    	
    	return StringUtil.concat(result.values(), "\n");
    }
    
//    public static void test01() {
////textLines(FileUtil.readText("D:\\발표\\sample2.xlsx.txt"));
//    	
//    	/*
//    	String content = FileUtil.readText("D:\\발표\\test.xlsx.txt");
//    	
//    	List<String> pages = textBlocks(textLines(content.replaceAll("\\b(?:[A-Z0-9\\.\\-_]+)\\b", " ").replaceAll("~[*^]~", " ")), 19 * 1024); 
//    	for (int i = 0; i < pages.size(); i++) {
//    		System.out.println(pages.get(i));
//    	}
//    	*/
//    	
//    	/*
//    	String patPattern = "(?:([초중고특])(급))?\\s*?(기술[자사])";
//    	String repPattern = "<attr type='grade' value='#token1' unit='#token2' man='#token3'/>";
//    	
//		String target = markingTextValue(content, Pattern.compile(patPattern), repPattern);
//		String output = revertMarkingTextValue(target);
//		String compact = removeUnusedMarkingText(output); 
//		System.out.println(target + "\n===========================\n" + output + "\n==========================\n" + compact);
//		*/
//    	
//    	Pattern extPattern = Pattern.compile("[\\_\\-\\s\\.]([a-zA-Z0-9]{1,5})$");
//    	String aaa = "asdasdasd.1234";
//    	String bbb = StringUtil.extractLine(extPattern, aaa)[0];
//    	
//    	System.out.println(bbb);
//    	
//    	LevenshteinDistance lev = LevenshteinDistance.getDefaultInstance();
//    	CosineSimilarity co = new CosineSimilarity();
//    	
//    	Map<CharSequence, Integer> s1 = new HashMap<CharSequence, Integer>();   
//    	Map<CharSequence, Integer> s2 = new HashMap<CharSequence, Integer>();
//    	
//    	String source1 = "2018년 기획서-[김지현]_Ver0.9.ppt";
//    	String source2 = "2017년 설계서-[유승환]_Ver1.2.ppt.old.20180910";
//
//    	String [] text1 = source1.split("[^가-힣0-9a-zA-Z]");
//    	String [] text2 = source2.split("[^가-힣0-9a-zA-Z]");
//    	
//    	for (int i = 0; i < text1.length; i++) {
//    		s1.put(text1[i], i);
//    	}
//    	
//    	for (int i = 0; i < text2.length; i++) {
//    		s2.put(text2[i], i);
//    	}
//    	
//    	double score = co.cosineSimilarity(s1, s2);
//    	
//    	int simDist = lev.apply(source1, source2);
//    	
//    	Integer [] sIndex = kmpSearch(source1.toCharArray(), "기획서".toCharArray());
//    	
//    	System.out.println(simDist + ":" + score + ":" + Arrays.toString(sIndex));
//    	
//    	Pattern pattern = getPattern(source1);
//    	
//    	System.out.println(pattern.matcher(source1).matches() + ":" + pattern.matcher(source2).matches());
//    	
//    	List<String> strs = splitText(source1);
//    	System.out.println(strs);
//    	
//    	pattern = getPattern(source1, source2);
//    	
//    	System.out.println(pattern.matcher(source1).find() + ":" + pattern.matcher(source2).find());
//    	
//    	List<String []> list = StringUtil.extract(pattern, source2);
//    	
//    	for (String [] tokens : list) {
//    		for (String token : tokens) { 
//    			System.out.println(token);
//    		}
//    	}
//    	
//    	Pattern remarkPattern1 = Pattern.compile("(?<=/\\x2A)([^/]*)(?=\\x2A/)|(?<=//)(.+)$", Pattern.MULTILINE);
//    	//Pattern remarkPattern2 = Pattern.compile("", Pattern.MULTILINE);
//    	
//    	String ff = FileUtil.readText("D:\\test\\sample.java");
//    	
//    	List<String []> remarkTexts = StringUtil.extract(remarkPattern1, ff);
//    	//remarkTexts.addAll(StringUtil.extract(remarkPattern2, ff));
//    	
//    	for (String [] aaaaa : remarkTexts) {
//    		System.out.println(aaaaa[0] + ":" + aaaaa[1]);
//    	}
//    	
//    	String test = "Price Terms  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 >주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영>전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 >주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영>전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주  EKHQ_대영전자 주";
//    	
//    	List<String> lineParts = divideLine(test, 128);
//    	for (String linePart: lineParts) {
//    		System.out.println(String.format("%03d:%s", linePart.length(), linePart));
//    	}
//    	
//    	//String date  = StringUtil.getTimestampToDate(
//    	//		System.currentTimeMillis() - (86400 * 1000), // 현재 타임스탬프에 하루치 타임스탬프 값을 뺀다.. (전날짜) 
//    	//		StringUtil.COMPACT_DATE_FORMAT
//    	//	);
//    	//System.out.println(date);
//    }
    
    public static String bondingShortLine(String content, int shortCond) {
    	List<String> lineSet = new ArrayList<String>();
    	
    	String [] lines = content.split("\n");
    	
    	StringBuilder linePart = new StringBuilder();
    	for (int i = 0; i < lines.length; i++) {
    		String line = lines[i];

    		// 파일 분리자는 그대로 넣는다.
    		if (line.startsWith("..PAGE:") || line.startsWith("..SHEET:") || line.startsWith("..FILE:")) {
    			if (linePart.length() > 0) {
    				lineSet.add(linePart.toString());
    				linePart.setLength(0);
    			}
    			
    			lineSet.add(line);
    			continue;
    		}

    		// 길이가 지정한 길이보다 길다면 그대로 넣는다.
    		if (line.length() > shortCond) {
    			if (linePart.length() > 0) {
    				lineSet.add(linePart.toString());
    				linePart.setLength(0);
    			}
    			
    			lineSet.add(line);
    			continue;
    		} else {
    			// 마지막행이라면 그대로 넣는다. 
    			if ((i + 1) == lines.length) {
        			if (linePart.length() > 0) {
        				lineSet.add(linePart.toString());
        				linePart.setLength(0);
        			}
        			
        			lineSet.add(line);
        			continue;
    			}
    			
    			// 다음행의 길이가 지정한 길이보다 길다면 그대로 넣는다.
    			if (lines[i + 1].length() > shortCond) {
        			if (linePart.length() > 0) {
        				lineSet.add(linePart.toString());
        				linePart.setLength(0);
        			}
        			
        			lineSet.add(line);
        			continue;
    			}
    			
    			linePart.append(line).append(" ");
    			
    			if (linePart.length() > shortCond) {
    				lineSet.add(linePart.toString());
    				linePart.setLength(0);
    			}
    		}    		
    	}
    	
    	return StringUtil.concat(lineSet, "\n");
    }
    
    /*
    public static String bondingShortLine(String content, int shortCond) {
    	Pattern pP = Pattern.compile("(\\.\\.(?:PAGE|SHEET|FILE):\\d+)", Pattern.MULTILINE);
    	Pattern pL = Pattern.compile(String.format("^([^\n]{1,%d})$\n(?![^\n]{%d,})", shortCond, shortCond + 1), Pattern.MULTILINE);
    	
    	String safeMarker = StringUtil.repeat("&", shortCond);
    	String pageBreak = String.format("%s$1", safeMarker);
    	
    	String result = pP.matcher(content).replaceAll(pageBreak);
    	
    	String test = pL.matcher(result).replaceAll("$1 ");
    	// ^([^\n]{1,20})$\n(?![^\n]{21,})
    	
    	
    	String test2 = test.replaceAll(safeMarker, "");
    	
    	System.out.println(test2);
    	
    	//return pL.matcher(test).replaceAll("$1 ");
    	
    	return test2;
    }
    */
    
//    public static void test02() {
//    	String content = FileUtil.readText("D:\\test\\01\\cccc.txt");
//		
//		long s = System.currentTimeMillis();
//		
//		String result = bondingShortLine(content, 80);
//		
//		System.out.println(System.currentTimeMillis() - s);
//		
//		FileUtil.save("D:\\test\\test-01-cccc.txt", result);
//    }
    
    public static List<String> splitLine(String content) {
    	int len = content.length();
    	
    	List<String> lines = new ArrayList<String>();
    	
    	int oldIndex = 0;
    	while (oldIndex <= len) {
    		int newIndex = content.indexOf("\n", oldIndex);
    		lines.add(content.substring(oldIndex, newIndex));
    		
    		oldIndex = newIndex + 1;
    	}
    	
    	return lines;
    }
    
//    public static void test03() {
//    	long ss = System.currentTimeMillis();
//    	
//    	String content = FileUtil.readText("D:\\test\\01\\test-03.xlsx.txt");
//    	
//    	long ee = System.currentTimeMillis() - ss;
//    	
//    	long s1 = System.currentTimeMillis();
//    	for (int i = 0; i < 100; i++) {
//    		content.split("\n");
//    	}
//    	long e1 = System.currentTimeMillis() - s1;
//    	
//    	long s2 = System.currentTimeMillis();
//    	for (int i = 0; i < 100; i++) {
//    		splitLine(content);
//    	}
//    	long e2 = System.currentTimeMillis() - s2;
//    	
//    	System.out.println(String.format("%05d-%05d-%05d", ee, e1, e2));
//    }
//    
//    public static void test04() {
//    	String aa = "sangam-nas2-userdata2-307636-ted1219.kim-20181101~20181102.filelist";
//    	Pattern FILE_LIST_NAME = Pattern.compile("([^\\-]*?)-([^\\-]*?)-([^\\-]*?)-([^\\-]*?)-([^\\-]*?)-([^\\.]*?)\\.filelist");
//    	
//    	List<String []> aaa = extract(FILE_LIST_NAME, aa);
//    	
//    	for (String [] aaaa : aaa) {
//    		for (String i : aaaa) {
//    			System.out.println(i);
//    		}
//    	}
//    }
//    
//    public static void test05() {
//    	System.out.println(
//    		String.format("%s%s%s%s%s%s", "a", "b", "c", "d", "e", "f")
//    	);
//    }
    
    public static int charCount(String source, char ch) {
    	int count = 0;
    	int index = 0;
    	
    	while ((index = source.indexOf(ch, index)) > -1) {
    		index++;
    		count++;
    	}
    	
    	return count;
    }
    
    public static char firstChar(String str) {
    	return isEmpty(str) ? '\0' : str.charAt(0); 
    }
    
    public static char lastChar(String str) {
    	return isEmpty(str) ? '\0' : str.charAt(str.length() - 1); 
    }
    
    public static int lineCount(String source) {
    	return isEmpty(source) ? 0 : charCount(source, '\n') + 1;
    }
    
    public static boolean isIncludeStr(String value, String ... compValues) {
    	if (compValues == null) return false;
    	
    	if (value == null && compValues == null) return true;
    	
    	if (value == null) return false;
    	
    	for (String cv : compValues) {
    		if (cv.equals(value)) return true;
    	}
    	
    	return false;
    }
    
    public static boolean isExcludeStr(String value, String ... compValues) {
    	if (compValues == null) return true;
    	
    	if (value == null && compValues == null) return true;
    	
    	if (value == null) return false;
    	
    	for (String cv : compValues) {
    		if (cv.equals(value)) return false;
    	}
    	
    	return true;
    }
    
    public static void main(String [] args) {
    	
    	String [] aa = "a b c d e f g h i j k l m n o p q r s t u v w x y z 0 1 2 3 4 5 6 7 8 9 A B C D E F G H I J K L M  N O P Q R S  T U V W X Y Z ~ ! @ # $ % ^ & * ( ) _ + { } : ; < > ? / , . [ ] - = `".split(" ");
    	
    	System.out.println(isIncludeStr("-", aa));
    	
    	System.out.println(isExcludeStr("a", aa));
    	
    	java.util.Map<String, HashMap<String, String>> pack = new java.util.HashMap<String, HashMap<String, String>>();
    	
    	pack.put("itemMap", new java.util.HashMap<String, String>());
    	
    	java.util.HashMap<String, String> item1 = pack.get("itemMap");
    	java.util.HashMap<String, String> item2 = pack.get("itemMap");
    	
    	
    	
    }
}

