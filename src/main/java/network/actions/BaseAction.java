package network.actions;

import com.alibaba.fastjson.JSONObject;

public class BaseAction {

    private int bufferId;
    private Integer topid;
    private String sid;

    private JSONObject data;
    private JSONObject extdata;

    private String initialData;

    public String pack(){
        return "";
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
