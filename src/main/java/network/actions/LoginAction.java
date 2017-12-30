package network.actions;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginAction extends BaseAction {
    @JSONField(name = "token")
    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
