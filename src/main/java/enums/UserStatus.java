package enums;

public enum UserStatus {
    normal(100, "正常"),
    freeze(200, "冻结"),
    cancel(300, "注销")
    ;

    private final int value;
    private final String name;
    public int getValue() {
        return value;
    }
    public String getName() {
        return name;
    }

    UserStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
