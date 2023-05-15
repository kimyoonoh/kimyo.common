package kr.co.kimyo.common.model.vo;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;

import kr.co.kimyo.common.util.BeanUtil;
import kr.co.kimyo.common.util.JSONUtil;
import kr.co.kimyo.common.util.XmlUtil;

import org.jdom2.Element;
import org.jdom2.JDOMException;

@SuppressWarnings("serial")
public abstract class AbstractVO implements ValueObject {
	BeanUtil bean = new BeanUtil(this);
	
    public String toString() {
        return bean.getString(" ");
    }

    public String toCSV() {
        return bean.getString(",");
    }

    public String toXML() {
        return XmlUtil.objToXmlText(this);
    }

    public String toJSON() {
        return JSONUtil.objToStr(this);
    }
    
    public Map<String, Object> toMap() {
    	return bean.toMap();
    }
    
    public Properties toProperty() {
    	return bean.toProperty("");
    }
    
    public Properties toProperty(String prefiex) {
    	return bean.toProperty(prefiex);
    }
    
    public AbstractVO fromJSON(JsonNode json) {
    	return fromJSON(json.asText());
    }
    
    public AbstractVO fromJSON(String jsonText) {
    	return (AbstractVO) JSONUtil.strToObj(jsonText, this.getClass());
    }
    
    public AbstractVO fromXML(Element xml) {
    	return (AbstractVO) XmlUtil.xmlToObj(xml, this.getClass());
    }
    
    public AbstractVO fromXML(String xmlText) {
    	try {
			return (AbstractVO) XmlUtil.XmlTextToObj(xmlText, this.getClass());
		} catch (JDOMException e) {
		} catch (IOException e) {
		}
    	
    	return this;
    }
    
    public AbstractVO fromVO(AbstractVO vo) {
    	return (AbstractVO) bean.apply(vo);
    }
    
    public AbstractVO fromMap(Map<String, Object> map) {
    	return (AbstractVO) bean.fromMap(map);
    }
    
    public AbstractVO fromProperty(Properties prop) {
    	return (AbstractVO) bean.fromProperty(prop);
    }
    
    public AbstractVO fromProperty(Properties prop, String prefix) {
    	return (AbstractVO) bean.fromProperty(prop, prefix);
    }
}
