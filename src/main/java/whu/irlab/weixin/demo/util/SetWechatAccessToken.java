package whu.irlab.weixin.demo.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import whu.irlab.weixin.demo.model.AccessToken;
import whu.irlab.weixin.demo.util.redis.RedisUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时获取access_token，并存入redis
 * @author lisf
 * @date 2019/10/09
 */
@Component
@Configuration
@EnableScheduling
public class SetWechatAccessToken {
    private static final Logger log = LoggerFactory.getLogger(SetWechatAccessToken.class);

    @Value("${wechat.tokenUrl}")
    private String url;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.appsecret}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 定时更新access_token
     */
    @Scheduled(fixedRate = 3600000)
    private void updateAccessToken(){
        String accessToken = getAccessToken();
        if(accessToken!=null){
            boolean updated = redisUtils.set("access_token",accessToken);
            if(updated){
                log.info("成功更新access_token！");
            }
            else {
                log.error("更新access_token失败！");
            }
        }
    }

    private String getAccessToken(){
        url = initRequestAccessTokenUrl(url);
        AccessToken accessToken = restTemplate.getForObject(url,AccessToken.class);
        try{
            String token = accessToken.getAccess_token();
            log.info("获得新的微信access_token，新的token为："+token);
            return token;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String initRequestAccessTokenUrl(String url){
        url = url+appId+"&secret="+appSecret;

        return url;
    }
}
