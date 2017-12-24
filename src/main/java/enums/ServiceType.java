package enums;

public enum ServiceType {
    gateway(0, "前置服务"),
    business(1, "业务服务");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ServiceType(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
