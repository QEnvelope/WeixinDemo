package whu.irlab.weixin.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Roger
 * @date 2018/5/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QaEngineResponse {

    private String status;
    private String errcode;
    private String syncId;
    private String coefficient;
    private String cmd;
    private Answer answer;
    private String msg;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Answer{
        private String type;
        private String content;
    }
    private String from;
    private String group;
    private String questionId;
}
/**
 *
 * {
 *     "status": "true",
 *     "errcode": "0x0000",
 *     "syncId": "oaaa",
 *     "coefficient": "0.8453333333333333",
 *     "cmd": "text",
 *     "msg": "申报截止日期是什么时候",
 *     "answer": {
 *         "type": "plain",
 *         "content": "感谢您的咨询，本次服务即将结束，祝您生活愉快[愉快]"
 *     },
 *     "from": "mvlstm",
 *     "group": "244",
 *     "questionId": "100081"
 * }
 *
 *
 */
