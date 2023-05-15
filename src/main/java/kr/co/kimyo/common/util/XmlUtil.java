package kr.triniti.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlUtil {
    public static Document getXmlDocument(String fileName) throws IOException, JDOMException {
        return getXmlDocument(fileName, false);
    }

    public static Document getXmlDocument(String fileName, boolean enabledValidation) throws IOException, JDOMException {
        return getXmlDocument(new File(fileName), enabledValidation);
    }

    public static Document getXmlDocument(File f) throws JDOMException, IOException {
        return getXmlDocument(f, false);
    }

    public static Document getXmlDocument(File f, boolean enabledValidation) throws JDOMException, IOException {
        FileInputStream fis = new FileInputStream(f);

        return getXmlDocument(fis, enabledValidation);
    }

    @SuppressWarnings("deprecation")
	public static Document getXmlDocument(InputStream is, boolean enabledValidation) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();

        builder.setValidation(enabledValidation);

        Document doc = builder.build(is);

        return doc;
    }

    @SuppressWarnings("deprecation")
	public static Document getXmlDocumentFromText(String xmlText, boolean enabledValidation) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();

        builder.setValidation(enabledValidation);

        StringReader reader = new StringReader(xmlText);

        Document doc = builder.build(reader);

        return doc;
    }

    public static Element getRootElement(String fileName) throws JDOMException, IOException {
        return getRootElement(fileName, false);
    }

    public static Element getRootElement(String fileName, boolean enabledValidation) throws JDOMException, IOException {
        return getRootElement(new File(fileName), enabledValidation);
    }

    public static Element getRootElement(File f) throws JDOMException, IOException {
        return getRootElement(f, false);
    }

    public static Element getRootElement(File f, boolean enabledValidation) throws JDOMException, IOException {
        FileInputStream fis = new FileInputStream(f);

        return getRootElement(fis, enabledValidation);
    }

    public static Element getRootElement(InputStream is) throws JDOMException, IOException {
        return getRootElement(is, false);
    }

    public static Element getRootElement(InputStream is, boolean enabledValidation) throws JDOMException, IOException {
        return getXmlDocument(is, enabledValidation).getRootElement();
    }

    public static Element getRootElementFromText(String xmlText, boolean enabledValidation) throws JDOMException, IOException {
        return getXmlDocumentFromText(xmlText, enabledValidation).getRootElement();
    }

    public static int getIntValue(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return 0;
    }

    public static long getLongValue(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
        }
        return 0L;
    }

    public static float getFloatValue(String str) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return 0.0F;
    }

    public static double getDoubleValue(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return 0.0D;
    }

    public static boolean getBooleanValue(String str) {
        if (str == null) {
            return false;
        }
        if (str == "") {
            return false;
        }
        if ("false".equals(str.toLowerCase())) {
            return false;
        }
        if ("n".equals(str.toLowerCase())) {
            return false;
        }
        if ("no".equals(str.toLowerCase())) {
            return false;
        }
        if ("off".equals(str.toLowerCase())) {
            return false;
        }
        if ("true".equals(str.toLowerCase())) {
            return true;
        }
        if ("y".equals(str.toLowerCase())) {
            return true;
        }
        if ("yes".equals(str.toLowerCase())) {
            return true;
        }
        if ("on".equals(str.toLowerCase())) {
            return true;
        }
        return false;
    }

    public static int getAttrIntValue(Element el, String attrName) {
        if (el != null) {
            return getAttrIntValue(el.getAttribute(attrName));
        }
        return 0;
    }

    public static long getAttrLongValue(Element el, String attrName) {
        if (el != null) {
            return getAttrLongValue(el.getAttribute(attrName));
        }
        return 0L;
    }

    public static float getAttrFloatValue(Element el, String attrName) {
        if (el != null) {
            return getAttrFloatValue(el.getAttribute(attrName));
        }
        return 0.0F;
    }

    public static double getAttrDoubleValue(Element el, String attrName) {
        if (el != null) {
            return getAttrDoubleValue(el.getAttribute(attrName));
        }
        return 0.0D;
    }

    public static boolean getAttrBooleanValue(Element el, String attrName) {
        return getAttrBooleanValue(el.getAttribute(attrName));
    }

    public static int getAttrIntValue(Attribute attr) {
        if (attr != null) {
            return getIntValue(attr.getValue());
        }
        return 0;
    }

    public static long getAttrLongValue(Attribute attr) {
        if (attr != null) {
            return getLongValue(attr.getValue());
        }
        return 0L;
    }

    public static float getAttrFloatValue(Attribute attr) {
        if (attr != null) {
            return getFloatValue(attr.getValue());
        }
        return 0.0F;
    }

    public static double getAttrDoubleValue(Attribute attr) {
        if (attr != null) {
            return getDoubleValue(attr.getValue());
        }
        return 0.0D;
    }

    public static boolean getAttrBooleanValue(Attribute attr) {
        return getBooleanValue(attr.getValue());
    }

    public static int getElementIntValue(Element el) {
        if (el != null) {
            return getIntValue(el.getValue());
        }
        return 0;
    }

    public static long getElementLongValue(Element el) {
        if (el != null) {
            return getLongValue(el.getValue());
        }
        return 0L;
    }

    public static float getElementFloatValue(Element el) {
        if (el != null) {
            return getFloatValue(el.getValue());
        }
        return 0.0F;
    }

    public static double getElementDoubleValue(Element el) {
        if (el != null) {
            return getDoubleValue(el.getValue());
        }
        return 0.0D;
    }

    public static boolean getElementBooleanValue(Element el) {
        return getBooleanValue(el.getValue());
    }

    public static Format XmlFormat(String mode, String encoding) {
        Format fmt = null;
        if ("pretty".equals(mode)) {
            fmt = Format.getPrettyFormat();
        }
        if ("compact".equals(mode)) {
            fmt = Format.getCompactFormat();
        }
        if ("raw".equals(mode)) {
            fmt = Format.getRawFormat();
        }
        fmt.setEncoding(encoding);

        return fmt;
    }

    public static String doc2str(Document doc, String encoding) {
        XMLOutputter xo = new XMLOutputter(XmlFormat("raw", encoding));

        return xo.outputString(doc);
    }

    public static String doc2str(Document doc) {
        XMLOutputter xo = new XMLOutputter(XmlFormat("raw", "utf-8"));

        return xo.outputString(doc);
    }

    public static String element2str(Element el, String encoding) {
        XMLOutputter xo = new XMLOutputter(XmlFormat("raw", encoding));
        return xo.outputString(el);
    }

    public static String element2str(Element el) {
        XMLOutputter xo = new XMLOutputter(XmlFormat("raw", "utf-8"));
        return xo.outputString(el);
    }

    private static Object str2obj(Class<?> cls, String value) {
        Object obj = null;

        String type = cls.getSimpleName();
        if ("String".equals(type)) {
            return value;
        }
        try {
            if ("Integer".equals(type)) {
                return Integer.valueOf(getIntValue(value));
            }
            if ("Long".equals(type)) {
                return Long.valueOf(getLongValue(value));
            }
            if ("Float".equals(type)) {
                return Float.valueOf(getFloatValue(value));
            }
            if ("Double".equals(type)) {
                return Double.valueOf(getDoubleValue(value));
            }
            if ("Boolean".equals(type)) {
                return Boolean.valueOf(getBooleanValue(value));
            }
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    public static Element objToXml(Object obj) {
        Element elObj = new Element(obj.getClass().getSimpleName());
        for (Field f : obj.getClass().getDeclaredFields()) {
            Element elField = new Element(f.getName());

            elField.setText(f.toString());

            elObj.addContent(elField);
        }
        return elObj;
    }

    @SuppressWarnings({ "rawtypes" })
	public static Object xmlToObj(Element elObj, Class c) {
        Object obj = null;
        try {
            obj = c.newInstance();

            Iterator<Element> it = elObj.getChildren().iterator();
            while (it.hasNext()) {
                Element elMember = (Element) it.next();

                Method m = obj.getClass().getDeclaredMethod(StringUtil.setter(elMember.getName()), new Class[0]);
                if (m != null) {
                    Class cls = m.getParameterTypes()[0];

                    Object value = str2obj(cls, elMember.getText());
                    if (value != null) {
                        m.invoke(cls.cast(str2obj(cls, elMember.getText())), new Object[0]);
                    }
                }
            }
        } catch (Exception localException) {
        }
        return obj;
    }

    public static String objToXmlText(Object obj) {
        return element2str(objToXml(obj));
    }

    @SuppressWarnings("rawtypes")
	public static Object XmlTextToObj(String xmlText, Class c) throws JDOMException, IOException {
        return xmlToObj(getRootElementFromText(xmlText, false), c);
    }
}
