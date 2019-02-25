package whu.irlab.weixin.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor

public class QaEngineRequest {
    
    private String userId;
    private String msg;
    private String original;
    private String syncId;

    /**
     * 是否推送推荐问题。ture：是 false：否
     */
    private boolean isRecommended;

    public QaEngineRequest(String userId, String msg) {
        this.userId = userId;
        this.msg = msg;
    }

    public void setOriginal(String original){
        this.original=original;
    }

    public void setSyncId(String syncId){
        this.syncId=syncId;
    }

    public void setRecommended(boolean recommended) {
        isRecommended = recommended;
    }
}
