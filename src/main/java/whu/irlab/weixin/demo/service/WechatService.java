package whu.irlab.weixin.demo.service;

import javax.servlet.http.HttpServletRequest;

public interface WechatService {

    //处理消息
    String processRequest(HttpServletRequest httpServletRequest);
}
