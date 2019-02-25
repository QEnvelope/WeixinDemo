package whu.irlab.weixin.demo.service.Impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import whu.irlab.weixin.demo.model.QaEngineRequest;
import whu.irlab.weixin.demo.service.QuestionAnswerService;
import whu.irlab.weixin.demo.service.WechatService;
import whu.irlab.weixin.demo.util.WechatUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Value("${robotai.url}")
    private String callCenterUrl;

    @Value("${qa.sender.original}")
    private String senderOriginal;

    @Value("${qa.recommend}")
    private boolean isRecommended;

    private AtomicLong syncId = new AtomicLong(0);

    @Override
    public String processRequest(HttpServletRequest httpServletRequest) {
        System.out.println(httpServletRequest.toString());
        HashMap<String,String> requestMap = null;
        String response = "";
        try {
            requestMap = WechatUtil.parseXml(httpServletRequest);
            String userId = DigestUtils.sha1Hex(requestMap.get("FromUserName"));
            String question = requestMap.get("Content");
            QaEngineRequest request = new QaEngineRequest(userId, question);
            request.setSyncId(String.valueOf(syncId.getAndIncrement()));
            request.setOriginal(senderOriginal);
            request.setRecommended(isRecommended);
            String answer = questionAnswerService.requestForRobotai(request);
            response = WechatUtil.sendTextMsg(requestMap, answer);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
