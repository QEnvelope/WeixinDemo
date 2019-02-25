package whu.irlab.weixin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whu.irlab.weixin.demo.service.WechatService;
import whu.irlab.weixin.demo.util.WechatUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatService wechatService;

    @Value("${wechat.token}")
    private String token;
    /**
     * 验证消息来自微信服务器，接入生效
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public String validate(@RequestParam(value = "signature") String signature,
                         @RequestParam(value = "timestamp") String timestamp,
                         @RequestParam(value = "nonce") String nonce,
                         @RequestParam(value = "echostr") String echostr){
        return WechatUtil.checkSignature(signature,token,timestamp,nonce) ? echostr : null;
    }


    /**
     * 处理微信服务器发来的消息返回
     * @param httpServletRequest
     */
    @RequestMapping(value = "/work", method = RequestMethod.POST)
    public String processMsg(HttpServletRequest httpServletRequest){
        return wechatService.processRequest(httpServletRequest);
    }

    @RequestMapping("/hello")
    public String index(){
        return "hello world!";
    }
}
