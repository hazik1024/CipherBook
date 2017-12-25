package network.actions;

import com.alibaba.fastjson.annotation.JSONField;

public class KeepAliveAction extends BaseAction {

    @JSONField(name="time")
    private long time;
    @JSONField(name="bufferid")
    private int bufferId;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBufferId() {
        return bufferId;
    }

    public void setBufferId(int bufferId) {
        this.bufferId = bufferId;
    }
}
