package enums;

public enum ServiceStatus {
    stop(0, "停止"),
    ready(1, "准备"),
    start(2, "开始"),
    running(3, "运行"),
    suspend(4, "暂停"),
    close(5, "关闭");

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    ServiceStatus(int type, String description) {
        this.type = type;
        this.description = description;
    }
}
