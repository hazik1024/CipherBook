package enums;

public enum RedisDataBaseNumber {
    userinfo(0, "用户信息");

    private int num;
    private String name;

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    RedisDataBaseNumber(int num, String name) {
        this.num = num;
        this.name = name;
    }
}
