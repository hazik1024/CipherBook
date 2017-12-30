package enums;

public enum Topid {
    gateway(0, "gatewayservice"),
    keepalive(100000, "心跳"),
    //用户
    userlogin(100101, "登录"),
    userregister(100102, "注册"),
    usercancel(100103, "注销"),
    usermodifyPassword(100104, "修改登录密码")
    ;


    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    Topid(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
