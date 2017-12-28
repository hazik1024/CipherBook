package dao.user;

import dao.GenericDaoImpl;
import entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao extends GenericDaoImpl<UserEntity> {

    public UserDao() {
        super(UserEntity.class);
    }
    public List<UserEntity> getAll() {
        return super.queryList("from UserEntity");
    }

    public UserEntity queryById(int uid) {
        return super.query("from UserEntity where uid=" + uid);
    }

    public Integer getCount() {
        return super.queryList("from UserEntity").size();
    }

    public UserEntity queryUser(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        return super.query("FROM UserEntity", map);
    }
}
