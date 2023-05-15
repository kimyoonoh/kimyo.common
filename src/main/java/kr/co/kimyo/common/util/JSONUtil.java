package kr.triniti.common.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtil {
    static final ObjectMapper mapper = new ObjectMapper();

    public static String objToStr(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonEOFException localJsonGenerationException) {
        } catch (JsonParseException localJsonMappingException) {
        } catch (IOException localIOException) {
        }
        return "{}";
    }
    
    @SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String str) {
    	return (Map<String, Object>) strToObj(str, Map.class);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object strToObj(String str, Class c) {
        Object obj = null;
        try {
            obj = mapper.readValue(str.getBytes(), c);
        } catch (Exception localException) {
        	localException.printStackTrace();
        }
        
        return obj;
    }
}
