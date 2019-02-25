package whu.irlab.weixin.demo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whu.irlab.weixin.demo.model.QaEngineRequest;
import whu.irlab.weixin.demo.service.QuestionAnswerService;
import whu.irlab.weixin.demo.util.HttpService;

/**
 * @Description
 * @Author: qy
 **/
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    private HttpService httpService;

    @Override
    public String requestForRobotai(QaEngineRequest request) {
        return httpService.getAnswerFromRobotai(request);
    }
}
