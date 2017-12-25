package enums;

public enum  ServiceCode {
    success(0, "成功");

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
