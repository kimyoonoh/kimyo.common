package kr.triniti.common.map;

import java.util.HashMap;

/**
 * <pre>
 * kr.triniti.common.map.LowerCaseMap
 * 
 * 모든 입력 
 * </pre>
 * @Author  : Kim yunoh
 * @Date    : 2021. 3. 3.
 * @Version : 1.0.0
 */
public class LowerCaseMap extends HashMap<String, Object> {
    private static final long serialVersionUID = 8393955582336728644L;

    public Object put(String paramName, Object paramValue) {
        return super.put(paramName.toLowerCase(), paramValue);
    }
}