package actions;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 响应用户操作的数据包装类
 */
abstract class BaseAction {
    @JSONField(deserialize = false, serialize = false)
    String packData() {
        return JSONObject.toJSONString(this);
    }
}
