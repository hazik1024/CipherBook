package enums;

public enum ActionType {
    gateway(0, "gatewayservice"),
    keepalive(100000, "心跳"),
    login(100101, "登录")
    ;


    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    ActionType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
