package cn.qq.utils;

import java.io.*;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class UcstarStringUtils {
	/**
     * add by rwl
     * 处理String、long、float、double等数据
     */
    public static String nullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

    
    /**
     * 解析xml
     * 提取richtext中的内容
     * @param body
     * @return
     */
    public static String formatRichTextToContent(String body) {
    	if(body == null) {
    		return "";
    	}
    	if(body.indexOf("richtext") < 0) {
    		return body;
    	}
        String content = body;

        InputStream contentbyte = null;

        try {
            contentbyte = new ByteArrayInputStream(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SAXReader reader = new SAXReader();
        Document document = null;
        StringBuffer title = new StringBuffer();

        try {
            document = reader.read(contentbyte);
            
            if (document.getRootElement().element("paragraphlayout") == null) {
            	document = null;
            	title.delete(0, title.length());
                return body;
            }
            List<Element> paragraphEles = document.getRootElement().element("paragraphlayout").elements("paragraph");
            int i = 0;
            for (Element paragraphEle : paragraphEles) {
                if (paragraphEle.element("text") != null) {
                	
                	List<Element> texts = paragraphEle.elements("text");
                	if (texts.size()>1) {
                		for (Element text : texts) {
                    		title.append(text.getText());
    					}
                	}else {
                		title.append(paragraphEle.element("text").getText());
                	}
                	
                    if (++i < paragraphEles.size()) {
                        title.append("\n");
                    }
                } else if (paragraphEle.element("animation") != null) {
                	Element animationfaceEle = paragraphEle.element("animation").element("animationface");
                	if(animationfaceEle != null) {
                		title.append("[" + animationfaceEle.elementTextTrim("facekey") + "]");
                	} else {
                		title.append("表情");
                	}
                    
                    if (++i < paragraphEles.size()) {
                        title.append("\n");
                    }
                } else {
                    title.append("\n");
                }
            }

        } catch (DocumentException ex) {
        	document = null;
        	title.delete(0, title.length());
            return body;
        }
        return decodeHtml(title.toString());
    }
    
    
    
    /**
     * 转换unicode编码
     * @param str
     * @return
     */
    private static String decodeHtml(String str) {
        if (str.equals("")) {
            return "";
        }
        String ret = "";

        String[] toParse = str.split("&#");
        for (int i = 0; i < toParse.length; i++) {
            String s = toParse[i];
            if (i > 0) {
                if (s == null || s.length() <= 0) {
                    continue;
                }
                try {
                    if(s.length() > 5) {
                        ret += Character.toChars(Integer.parseInt(s.substring(0, 5)))[0];
                        if (s.length() > 6) {
                            ret += s.substring(6, s.length());
                        }
                    } else if(s.length() > 4) {
                        ret += Character.toChars(Integer.parseInt(s.substring(0, 4)))[0];
                    } else {
                        ret += s;
                    }
                } catch(Exception e) {
                }
            } else {
                ret += s;
            }
        }
        ret = ret.replaceAll("&amp;", "&");

        return ret;
    }
   
}
