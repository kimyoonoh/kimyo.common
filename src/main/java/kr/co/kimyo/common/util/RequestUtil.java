package kr.triniti.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class RequestUtil {
    private static final int PAGE_SIZE = 20;
    public static final Locale DEFAULT_LOCALE = new Locale("ko", "KR");
    static Logger log = LoggerFactory.getLogger(RequestUtil.class);

	public static Map<String, Object> bindParameterToMap(HttpServletRequest request) {
        return bindParameterToMap(request.getParameterMap());
    }

    public static Map<String, Object> bindParameterToMap(Map<String, String[]> requestParameters) {
        Map<String, Object> bindParameterMap = new HashMap<String, Object>();
        String[] pageValue;
        if ((requestParameters.containsKey("page")) && (requestParameters.containsKey("rows"))) {
            String[] rowsValue = (String[]) requestParameters.get("rows");
            pageValue = (String[]) requestParameters.get("page");

            int rows = Integer.valueOf(rowsValue[0]).intValue();
            int page = Integer.valueOf(pageValue[0]).intValue();

            bindParameterMap.put("rowIndex", Integer.valueOf((page - 1) * rows));
        }
        if ((requestParameters != null) && (!requestParameters.isEmpty())) {
            for (String parameter : requestParameters.keySet()) {
                String[] values = (String[]) requestParameters.get(parameter);
                if (values.length > 1) {
                    bindParameterMap.put(parameter, values);
                } else {
                    bindParameterMap.put(parameter, values[0]);
                }
            }
        }
        return bindParameterMap;
    }

    public static Map<String, Map<String, Object>> bindParameterToMap(String name, Map<String, String[]> requestParameters) {
        Map<String, Map<String, Object>> bindParameterMap = new HashMap<String, Map<String, Object>>();

        bindParameterMap.put(name, bindParameterToMap(requestParameters));

        return bindParameterMap;
    }

    public static void setResult(HttpServletResponse response, String json) throws Exception {
        response.setHeader("Content-Type", "text/html; charset=utf-8");
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public static int transBigDecimalToInt(BigDecimal value) {
        return value == null ? 0 : value.intValue();
    }

    public static Object getFirstItemField(List<Map<String, Object>> list, String fieldName) {
        return list.isEmpty() ? BigDecimal.ZERO : ((Map<String, Object>) list.get(0)).get(fieldName);
    }

    public static Map<String, Object> makeGridDataSet(Map<String, Object> inputParam, List<Map<String, Object>> rows) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (rows.size() > 0) {
            int pageSize = inputParam.get("rows") != null ? Integer.parseInt((String) inputParam.get("rows")) : PAGE_SIZE;
            int currentPage = Integer.parseInt((String) inputParam.get("page")) - 1;

            long totalCount = ((Long) getFirstItemField(rows, "total_count")).longValue();

            result.put("totalPage", Long.valueOf(totalCount / pageSize));
            result.put("totalRecords", Long.valueOf(totalCount));
            result.put("currPage", Integer.valueOf(currentPage));
        } else {
            result.put("totalPage", "0");
            result.put("totalRecords", "0");
            result.put("currPage", "1");
        }
        result.put("rows", rows);

        return result;
    }

    public static String makeValidationResultJSONText(BindingResult result) {
        String json = "errors : {";
        for (FieldError error : result.getFieldErrors()) {
            String message = error.getDefaultMessage();
            String field = error.getField();
            String code = error.getCode();
            Object value = error.getRejectedValue();

            String errorText = "[{";
            errorText = errorText + "message : '" + message + "',";
            errorText = errorText + "field   : '" + field + "',";
            errorText = errorText + "code    : '" + code + "',";
            errorText = errorText + "value   : '" + value + "',";
            errorText = errorText + "}],";

            json = json + errorText;
        }
        return json.substring(0, json.length() - 1) + "}";
    }

    public static String getResult(boolean result) {
        StringBuffer buff = new StringBuffer("result : ");

        buff.append("{").append("success : ").append(result).append("}");

        return buff.toString();
    }
}
