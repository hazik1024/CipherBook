package enums;

public enum SocketStatus {
    disconnect(0, "未连接"),
    connecting(1, "连接中"),
    connected(2, "已连接");

    private int status;
    private String name;

    SocketStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }


}
