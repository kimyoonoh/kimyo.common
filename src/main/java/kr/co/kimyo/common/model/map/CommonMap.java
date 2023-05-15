package kr.triniti.common.map;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import kr.triniti.common.model.vo.AbstractVO;


/**
 * <pre>
 * kr.triniti.common.map.CommonMap
 * </pre>
 * @Author  : Kim yunoh
 * @Date    : 2021. 3. 3.
 * @Version : 1.0.0
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("serial")
public class CommonMap<K, V> extends HashMap<K, V> {
	public String getString(K key) {
		return (String) this.get(key);
	}

	public String getString(K key, String value) {
		return this.containsKey(key) ? getString(key) : value;
	}

	public boolean getBoolean(K key) {
		Object obj = get(key);
		
		if (obj instanceof Boolean) return ((Boolean) obj).booleanValue();
		
		return Boolean.parseBoolean(this.getString(key));
	}

	public boolean getBoolean(K key, boolean value) {
		return this.containsKey(key) ? getBoolean(key) : value;
	}

	@SuppressWarnings("deprecation")
	public Date getDate(K key) {
		Object obj = get(key);
		
		if (obj instanceof java.util.Date) return (Date) obj;
		
		return new Date(Date.parse(getString(key)));
	}

	public Date getDate(K key, Date value) {
		return this.containsKey(key) ? getDate(key) : value;
	}

	public char getChar(K key) {
		Object obj = get(key);
		
		if (obj instanceof Character) return ((Character) obj).charValue();
		
		return this.getString(key).toCharArray()[0];
	}

	public char getChar(K key, char value) {
		return this.containsKey(key) ? getChar(key) : value;
	}

	public byte getByte(K key) {
		Object obj = get(key);
		
		if (obj instanceof Byte) return ((Byte) obj).byteValue();
		
		return Byte.parseByte(getString(key));
	}

	public byte getByte(K key, byte value) {
		return this.containsKey(key) ? getByte(key) : value;
	}
	
	public short getShort(K key) {
		Object obj = get(key);
		
		if (obj instanceof Short) return ((Short) obj).shortValue();
		
		return Short.parseShort(getString(key));
	}

	public short getShort(K key, short value) {
		return this.containsKey(key) ? getShort(key) : value;
	}

	public int getInt(K key) {
		Object obj = get(key);
		
		if (obj instanceof Integer) return ((Integer) obj).intValue();
		
		return Integer.parseInt(getString(key));
	}

	public int getInt(K key, int value) {
		return this.containsKey(key) ? getInt(key) : value;
	}

	public long getLong(K key) {
		Object obj = get(key);
		
		if (obj instanceof Long) return ((Long) obj).longValue();
		
		return Long.parseLong(getString(key));
	}

	public long getLong(K key, long value) {
		return this.containsKey(key) ? getLong(key) : value;
	}

	public float getFloat(K key) {
		Object obj = get(key);
		
		if (obj instanceof Float) return ((Float) obj).floatValue();
		
		return Float.parseFloat(getString(key));
	}

	public float getFloat(K key, float value) {
		return this.containsKey(key) ? getFloat(key) : value;
	}

	public double getDouble(K key) {
		Object obj = get(key);
		
		if (obj instanceof Double) return ((Double) obj).doubleValue();
		
		return Double.parseDouble(getString(key));
	}

	public double getDouble(K key, double value) {
		return this.containsKey(key) ? getDouble(key) : value;
	}

	public AbstractVO getVO(K key) {
		return (AbstractVO) get(key);
	}

	public AbstractVO getVO(K key, AbstractVO value) {
		return this.containsKey(key) ? getVO(key) : value;
	}

	@SuppressWarnings("rawtypes")
	public Collection getCollection(K key) {
		return (Collection) get(key);
	}

	@SuppressWarnings("rawtypes")
	public Collection getCollection(K key, Collection value) {
		return this.containsKey(key) ? getCollection(key) : value;
	}
}
