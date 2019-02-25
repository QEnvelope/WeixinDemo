package whu.irlab.weixin.demo.service;

import whu.irlab.weixin.demo.model.QaEngineRequest;

/**
 * @Description: 问答service
 * @Author: qy
 **/
public interface QuestionAnswerService {

    String requestForRobotai(QaEngineRequest request);

}
