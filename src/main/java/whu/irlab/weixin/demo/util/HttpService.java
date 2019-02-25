package whu.irlab.weixin.demo.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import whu.irlab.weixin.demo.model.QaEngineRequest;
import whu.irlab.weixin.demo.model.QaEngineResponse;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: http请求
 * @Author: qy
 **/
@Service
public class HttpService {

    @Value("${robotai.url}")
    private String callCenterUrl;

    @Value("${qa.sender.original}")
    private String senderOriginal;

    private AtomicLong syncId = new AtomicLong(0);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 访问中控
     * @param
     * @return
     */
    public String getAnswerFromRobotai(QaEngineRequest request){
        //CloseableHttpResponse httpResponse;
        //HttpPost httpPost = new HttpPost(callCenterUrl);
        //List<NameValuePair> paras = new ArrayList<>();

        HttpEntity<QaEngineRequest> httpEntity = new HttpEntity<>(request);
        QaEngineResponse qaEngineResponse = restTemplate.postForObject(callCenterUrl, httpEntity, QaEngineResponse.class);
        //paras.add(new BasicNameValuePair("question",question));
        try {
            String answer = qaEngineResponse.getAnswer().getContent();
            if (answer != null) {
                return answer;
            }else {
                return "很抱歉，小布也不知道诶";
            }
            //httpPost.setEntity(new UrlEncodedFormEntity(paras,"utf-8"));
            //CloseableHttpClient httpClient = HttpClients.createDefault();
            //httpResponse = httpClient.execute(httpPost);
            //if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            //    String entityContent = EntityUtils.toString(httpResponse.getEntity());
            //    System.out.println("返回消息实体：" + entityContent);
            //    if(!entityContent.equals("")){
            //        //增加字段记录回答方式
            //        return entityContent;
            //    }
            //    return "很抱歉，小布也不知道诶";
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "很抱歉，小布现在无法提供咨询服务哦";
    }
}
