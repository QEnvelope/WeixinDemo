package whu.irlab.weixin.demo.service.Impl;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import whu.irlab.weixin.demo.model.QaEngineRequest;
import whu.irlab.weixin.demo.model.WechatResponse;
import whu.irlab.weixin.demo.service.QuestionAnswerService;
import whu.irlab.weixin.demo.service.WechatService;
import whu.irlab.weixin.demo.util.WechatUtil;
import whu.irlab.weixin.demo.util.redis.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class WechatServiceImpl implements WechatService {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${robotai.url}")
    private String callCenterUrl;

    @Value("${qa.sender.original}")
    private String senderOriginal;

    @Value("${qa.recommend}")
    private boolean isRecommended;

    @Value("${wechat.sendUrl}")
    private String sendUrl;

    private Gson gson = new Gson();

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

    @Override
    public boolean sendTextToUser(String userId,String text) {
        String urlVariables = initTextMap(userId,text);
        try{
            String accessToken = (String)redisUtils.get("access_token");
            HttpEntity<String> request = new HttpEntity<>(urlVariables);
            WechatResponse wechatResponse = restTemplate.postForObject(sendUrl+accessToken,request,WechatResponse.class);
            if(wechatResponse.getErrorCode()==0){
                logger.info("发送文本消息到用户成功: "+text);
                return true;
            }
            else {
                logger.error("发送文本消息到用户失败！");
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    //初始化文本消息
    private String initTextMap(String userId,String text){
        Map<String,Object> variables = new HashMap<>();
        Map<String,String> textContent = new HashMap<>();
        textContent.put("content",text);
        variables.put("touser",userId);
        variables.put("msgtype","text");
        variables.put("text",textContent);
        return gson.toJson(variables);
    }
}
