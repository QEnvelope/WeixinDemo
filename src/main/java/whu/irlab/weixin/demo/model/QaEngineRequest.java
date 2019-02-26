package whu.irlab.weixin.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor

public class QaEngineRequest {

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 同步ID
     */
    private String syncId;
    /**
     * 用户发过来的消息，通常是问句。
     */
    private String msg;

    private String original;
    /**
     * 是否推送推荐问题。ture：是 false：否
     */
    private boolean recommended;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public boolean isRecommended() {
        return recommended;
    }

    public void setRecommended(boolean recommended) {
        this.recommended = recommended;
    }
}
