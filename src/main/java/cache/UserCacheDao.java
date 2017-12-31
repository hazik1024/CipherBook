package cache;

import constants.GlobalConfig;
import constants.RedisKey;
import entity.UserEntity;

public class UserCacheDao extends CacheDaoImpl<UserEntity> {

    public UserEntity getEntity() {
        return null;
    }

    public void setEntity(UserEntity entity) {

    }

    public void saveToken(int uid, String token) {
        String key = RedisKey.USER_LOGIN_TOKEN;
        String uidKey = RedisKey.USER_ID_PREFIX + uid;
        int expire = GlobalConfig.TOKEN_EXPIRE;
        super.save(key, uidKey, token, expire);
    }
}
