package network.actions;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import enums.ServiceCode;

public abstract class BaseAction {
    @JSONField(deserialize = false, serialize = false)
    public String pack() {
        return JSONObject.toJSONString(this);
    }
    @JSONField(deserialize = false, serialize = false)
    public String getExtDataPack() {
        return "";
    }
}
