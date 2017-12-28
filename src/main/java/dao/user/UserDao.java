package dao.user;

import dao.GenericDao;
import entity.UserEntity;

import java.util.List;

public class UserDao extends GenericDao<UserEntity, Long> {
    public List<UserEntity> getAll() {
        return super.queryList("from UserEntity");
    }

    public UserEntity queryById(int uid) {
        return super.query("from UserEntity where uid=" + uid);
    }

    public int getCount() {
        return super.queryCount("from UserEntity");
    }
}
