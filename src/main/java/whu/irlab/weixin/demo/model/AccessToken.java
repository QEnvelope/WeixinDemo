package whu.irlab.weixin.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccessToken {

    private String access_token;

    private String expires_in;
}
