package enums;

public enum SocketType {
    async(0, "异步"),
    sync(1, "同步");

    private int type;
    private String name;

    SocketType(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
