package whu.irlab.weixin.demo.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WechatUtil {

    /**
     * 检验signature
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String token,String timestamp,String nonce){

        //按字典排序，顺序拼接
        String[] message = new String[]{token,timestamp,nonce};
        Arrays.sort(message);
        StringBuilder content = new StringBuilder();
        for(String msg:message){
            content.append(msg);
        }
        System.out.println("明文： " + content.toString());

        //SHA1加密
        String tmpContent = DigestUtils.sha1Hex(content.toString());
        System.out.println("生成报文：   " + tmpContent);

        //比较、返回
        return tmpContent != null ? tmpContent.equals(signature) : false;
    }

    /**
     * 解析服务器发送的xml格式的消息，返回hashmap
     * @param httpServletRequest
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> parseXml(HttpServletRequest httpServletRequest) throws Exception {
        //保存解析结果
        HashMap<String,String> requestMap = new HashMap<>();
        //dom4j解析
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = httpServletRequest.getInputStream();
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for(Element e:elementList){
            requestMap.put(e.getName(),e.getText());
            System.out.println(e.getName()+"    "+e.getText());
        }
        inputStream.close();
        inputStream = null;
        return requestMap;

    }


    /**
     * 把map转为微信要求的xml格式
     * @param requestMap
     * @return
     */
    public static String map2XML(HashMap<String,String> requestMap){
        Document document = DocumentHelper.createDocument();
        Element xml = document.addElement("xml");
        Element toUserName = xml.addElement("ToUserName");
        Element fromUserName = xml.addElement("FromUserName");
        Element createTime = xml.addElement("CreateTime");
        Element msgType = xml.addElement("MsgType");
        Element content = xml.addElement("Content");

        toUserName.addCDATA(requestMap.get("ToUserName"));
        fromUserName.addCDATA(requestMap.get("FromUserName"));
        createTime.addText(requestMap.get("CreateTime"));
        msgType.addCDATA(requestMap.get("MsgType"));
        content.addCDATA(requestMap.get("Content"));
        return document.getRootElement().asXML();
    }

    /**
     * 回复文本消息
     * @param requestMap
     * @param content
     * @return
     */
    public static String sendTextMsg(HashMap<String,String> requestMap,String content){
        HashMap<String,String> map=new HashMap<String, String>();
        map.put("ToUserName", requestMap.get("FromUserName"));
        map.put("FromUserName",  requestMap.get("ToUserName"));
        map.put("MsgType", "text");
        Long responseTime = new Date().getTime();
        map.put("CreateTime", responseTime.toString());
        map.put("Content", content);
        return  map2XML(map);
    }

}
