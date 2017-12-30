package cache;

import constants.GlobalConfig;
import constants.RedisKeyPrefix;
import entity.UserEntity;

public class UserCacheDao extends CacheDaoImpl<UserEntity> {

    public UserEntity getEntity() {
        return null;
    }

    public void setEntity(UserEntity entity) {

    }

    public void saveToken(int uid, String token) {
        String key = RedisKeyPrefix.USER_TOKEN + uid;
        int expire = GlobalConfig.TOKEN_EXPIRE;
        super.save(key, token, expire);
    }
}
