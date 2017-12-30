package enums;

public enum  ServiceCode {
    success(0, "成功"),
    dberror(-1000, "数据库操作异常"),
    //用户服务
    usernameexist(1000, "用户名已存在"),
    passworderror(1001, "密码错误"),
    usernameerror(1002, "用户名错误")
    ;

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ServiceCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
