package cache;

import constants.GlobalConfig;
import constants.RedisKey;
import entity.UserEntity;
import enums.UserStatus;
import util.DateFormatUtil;

import java.util.HashMap;
import java.util.Map;

public class UserCacheDao extends CacheDaoImpl<UserEntity> {

    public UserEntity mapToEntity(Map<String, String> map) {
        UserEntity entity = new UserEntity();
        entity.setUid(Integer.valueOf(map.get("uid")));
        entity.setUsername(map.get("username"));
        entity.setPassword(map.get("password"));
        entity.setMobile(map.get("mobile"));
        entity.setTruename(map.get("truename"));
        entity.setIdcard(map.get("idcard"));
        entity.setRegistertime(DateFormatUtil.stringToDate(map.get("registertime")));
        entity.setLastlogintime(DateFormatUtil.stringToDate(map.get("lastlogintime")));
        entity.setStatus(UserStatus.valueOf(map.get("status")));
        return null;
    }

    public Map<String, String> entityToMap(UserEntity entity) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", String.valueOf(entity.getUid()));
        map.put("username", entity.getUsername());
        map.put("password", entity.getPassword());
        map.put("mobile", entity.getMobile());
        map.put("truename", entity.getTruename());
        map.put("idcard", entity.getIdcard());
        map.put("registertime", DateFormatUtil.dateToString(entity.getRegistertime()));
        map.put("lastlogintime", DateFormatUtil.dateToString(entity.getLastlogintime()));
        map.put("status", entity.getStatus().toString());
        return map;
    }

    public boolean saveToken(int uid, String token) {
        String key = RedisKey.USER_LOGIN_TOKEN_USER_ID_PREFIX + uid;
        int expire = GlobalConfig.TOKEN_EXPIRE;
        return super.save(key, token, expire);
    }
}
