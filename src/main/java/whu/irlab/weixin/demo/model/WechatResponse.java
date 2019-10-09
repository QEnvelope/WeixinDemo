package whu.irlab.weixin.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WechatResponse {

    private int errorCode;

    private String errorMsg;
}
