package enums;

public enum ActionType {
    gateway(0, "gatewayservice"),
    keepalive(100000, "心跳");


    private int topid;
    private String name;

    public int getTopid() {
        return topid;
    }

    public String getName() {
        return name;
    }

    ActionType(int topid, String name) {
        this.topid = topid;
        this.name = name;
    }
}
