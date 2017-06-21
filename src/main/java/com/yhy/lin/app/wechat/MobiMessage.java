package com.yhy.lin.app.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.yhy.lin.app.entity.RefundReqData;

/**
* Description : 
* @author Administrator
* @date 2017年6月21日 下午5:02:38
*/
public class MobiMessage {  
	  
    public static Map<String,String> xml2map(HttpServletRequest request) throws IOException, DocumentException {  
        Map<String,String> map = new HashMap<String, String>();  
        SAXReader reader = new SAXReader();  
        InputStream inputStream = request.getInputStream();  
        Document document = reader.read(inputStream);  
        Element root = document.getRootElement();  
        List<Element> list = root.elements();  
        for(Element e:list){  
            map.put(e.getName(), e.getText());  
        }  
        inputStream.close();  
        return map;  
    }  
  
  
    //订单转换成xml  
//    public static String JsApiReqData2xml(JsApiReqData jsApiReqData){  
//        /*XStream xStream = new XStream(); 
//        xStream.alias("xml",productInfo.getClass()); 
//        return xStream.toXML(productInfo);*/  
//        MobiMessage.xstream.alias("xml",jsApiReqData.getClass());  
//        return MobiMessage.xstream.toXML(jsApiReqData);  
//    }  
  
    public static String RefundReqData2xml(RefundReqData refundReqData){  
        /*XStream xStream = new XStream(); 
        xStream.alias("xml",productInfo.getClass()); 
        return xStream.toXML(productInfo);*/  
        MobiMessage.xstream.alias("xml",refundReqData.getClass());  
        return MobiMessage.xstream.toXML(refundReqData);  
    }  
  
    public static String class2xml(Object object){  
  
        return "";  
    }  
    public static Map<String, String> parseXml(String xml) throws Exception {  
        Map<String, String> map = new HashMap<String, String>();  
        Document document = DocumentHelper.parseText(xml);  
        Element root = document.getRootElement();  
        List<Element> elementList = root.elements();  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
        return map;  
    }  
  
    //扩展xstream，使其支持CDATA块  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;  
  
                //@SuppressWarnings("unchecked")  
                public void startNode(String name, Class clazz) {  
                    super.startNode(name, clazz);  
                }  
  
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });  
  
}  
