package kr.co.kimyo.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** @name : 한글명
 * <pre></pre>
 * package : kr.co.kimyo.common.util
 * @class  : 
 * @author : 김윤오(P0004568)
 * @date   : 2023. 4. 28.
 *
 * @History
 * <pre>
 * No   Date         time          Author           Desc
 * ---------------------------------------------------------------
 *  1   2023. 4. 28. 오후 2:11:52  김윤오(P0004568) 최초작성
 * </pre>
 */
public class BeanUtil {
	private Class<?> klass;
	private Object self;
	
	public BeanUtil(Object self) {
		this.self  = self;
		this.klass = self.getClass();
	}
	
	public Field getField(String fieldName) {
		try {
			return klass.getField(fieldName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean hasField(String fieldName) {
		return getField(fieldName) != null;
	}
	
	public Field [] getFields() {
		return klass.getFields();
	}
	
	public Field [] getDeclaredFields() {
		try {
			return klass.getDeclaredFields();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Field getDeclaredField(String fieldName) {
		try {
			return klass.getDeclaredField(fieldName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean hasDeclaredField(String fieldName) {
		return getDeclaredField(fieldName) != null;
	}
	
	public boolean isNull(String fieldName) {
		return isNull(getField(fieldName));
	}
	
	public boolean isNull(Field f) {
        Class<?> t = f.getType();
        Object v;
        
        try {
            v = f.get(this.self);
        } catch (IllegalArgumentException e) {
            v = null;
        } catch (IllegalAccessException e) {
            v = null;
        }
        
        return (!t.isPrimitive()) && (v == null);
    }
	
	public String getTypeName(String fieldName) {
		return getTypeName(getField(fieldName));
	}
	
	public String getTypeName(Field f) {
		return f.getType().getSimpleName();
	}
	
	public boolean isString(Field f) {
		return "String".equals(getTypeName(f));
    }
	
	public Method getMethod(String methodName, Class<?> parameterTypes) {
		try {
			return klass.getMethod(methodName, parameterTypes);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Method [] getMethods() {
		try {
			return klass.getMethods();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Method [] getDeclaredMethods() {
		try {
			return klass.getDeclaredMethods();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Method getter(Field f) {
		return getMethod(StringUtil.getter(f.getName()), null);
	}
	
	public Method setter(Field f) {
		return getMethod(StringUtil.setter(f.getName()), f.getClass());
	}
	
	public Method getter(String fieldName) {
		return getMethod(StringUtil.getter(fieldName), null);
	}
	
	public Method setter(String fieldName) {
		return getMethod(StringUtil.setter(fieldName), getField(fieldName).getClass());
	}
	
	public String getValue(String fieldName) {
		return getValue(getField(fieldName), true);
	}
	
	public String getValue(String fieldName, boolean outputMode) {
		return getValue(getField(fieldName), outputMode);
	}
	
	public String getValue(Field f) {
		return getValue(f, true);
	}
	
	public String getValue(Field f, boolean outputMode) {
		String value = "";
		
		try {
			Method m = getter(f);
			
			if (m != null) value = String.valueOf(m.invoke(self, new Object[0])); 
		} catch (Exception e) {
			
		}
		
		return outputMode ? ( isString(f) ? "'" + value + "'" : isNull(f) ? "''" : value ) : value;
	}
	
	public void setValue(String fieldName, Object value) {
		setValue(getField(fieldName), value);
	}
	
	public void setValue(Field f, Object value) {
		try {
			Method m = setter(f);
			
			if (m != null) m.invoke(self, value);
		} catch (Exception e) {}
	}
	
    public String getString(String sep) {
        StringBuffer buff = new StringBuffer();

        Field [] fields = getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            buff.append(getValue(field));
            if (i + 1 == fields.length) {
                buff.append(sep);
            }
        }
        
        return buff.toString();
    }
    
    public Map<String, Object> toMap() {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	Field [] fields = getDeclaredFields();
    	for (Field f : fields) {
    		map.put(f.getName(), getValue(f));
    	}
    	
    	return map;
    }
    
    private final static String PREFIX_FORMAT = "%s.%s"; 
    
    @SuppressWarnings("static-access")
	public Properties toProperty(String prefix) {
    	Properties prop = new Properties();
    	boolean existPrefix = StringUtil.isEmpty(prefix);
    	
    	Field [] fields = getDeclaredFields();
    	for (Field f : fields) {
    		String fieldName = existPrefix ? PREFIX_FORMAT.format(prefix, f.getName()) : f.getName();
    		prop.put(fieldName, getValue(f));
    	}
    	
    	return prop;
    }
    
    public Object apply(Object source) {
    	if (!this.getClass().equals(source.getClass())) return null;
    	
    	BeanUtil sourceBean = new BeanUtil(source); 
    	
    	
    	Field [] fields = sourceBean.getDeclaredFields();
    	for (Field f : fields) {
    		setValue(f, sourceBean.getValue(f));
    	}
    	
    	return self;
    }
    
    public Object fromProperty(Properties prop) {
    	return fromProperty(prop, "");
    }
    
    public Object fromProperty(Properties prop, String prefix) {
    	boolean existPrefix = StringUtil.isEmpty(prefix);
    	
    	for (Object key : prop.keySet()) {
    		String fieldName = String.valueOf(key); 
    		fieldName = existPrefix ? fieldName.replaceAll(prefix + "\\.", "") : fieldName;
    		
    		setValue(fieldName, prop.get(key));
    	}
    	
    	return self;
    }
    
    public Object fromMap(Map<String, Object> map) {
    	for (String key : map.keySet()) {
    		setValue(key, map.get(key));
    	}
    	
    	return self;
    }
}
