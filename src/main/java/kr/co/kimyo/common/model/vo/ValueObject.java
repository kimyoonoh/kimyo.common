package kr.co.kimyo.common.model.vo;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import org.jdom2.Element;

public interface ValueObject extends Serializable {
	public String toString();

    public String toCSV();

    public String toXML();

    public String toJSON();
    
    public Map<String, Object> toMap();
    
    public Properties toProperty();
    
    public Properties toProperty(String prefiex);
    
    public AbstractVO fromJSON(String jsonText);
    
    public AbstractVO fromJSON(JsonNode json);
    
    public AbstractVO fromXML(String xmlText);
    
    public AbstractVO fromXML(Element xml);
    
    public AbstractVO fromVO(AbstractVO vo);
    
    public AbstractVO fromMap(Map<String, Object> map);
    
    public AbstractVO fromProperty(Properties prop);
    
    public AbstractVO fromProperty(Properties prop, String prefix);
}
