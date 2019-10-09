package whu.irlab.weixin.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface WechatService {

    //处理消息
    String processRequest(HttpServletRequest httpServletRequest);

    //发送文本消息
    boolean sendTextToUser(String userId,String text);

    //发送图片消息
    //需要先将消息上传为临时素材


}
