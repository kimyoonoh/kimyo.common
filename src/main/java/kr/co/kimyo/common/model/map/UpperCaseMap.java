package kr.triniti.common.map;

import java.util.HashMap;

public class UpperCaseMap extends HashMap<String, Object> {
	private static final long serialVersionUID = -8662149735657307641L;

	public Object put(String paramName, Object paramValue) {
        return super.put(paramName.toUpperCase(), paramValue);
    }
}