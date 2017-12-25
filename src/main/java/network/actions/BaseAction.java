package network.actions;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

public class BaseAction {
    @JSONField(serialize=false)
    private int bufferId;
    @JSONField(name = "topid")
    private Integer topid;
    private String sid;

    @JSONField(name = "data")
    private JSONObject data;
    @JSONField(name = "extdata")
    private JSONObject extdata;
    @JSONField(serialize=false)
    private String initialData;

    public String pack(){
        return this.getInitialData();
    }

    public void unpack(){

    }


    public int getBufferId() {
        return bufferId;
    }

    public void setBufferId(int bufferId) {
        this.bufferId = bufferId;
    }

    public Integer getTopid() {
        return topid;
    }

    public void setTopid(Integer topid) {
        this.topid = topid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public JSONObject getExtdata() {
        return extdata;
    }

    public void setExtdata(JSONObject extdata) {
        this.extdata = extdata;
    }

    public String getInitialData() {
        return initialData;
    }

    public void setInitialData(String initialData) {
        this.initialData = initialData;
    }
}
