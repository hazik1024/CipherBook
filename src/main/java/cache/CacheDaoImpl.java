package cache;

import util.JedisUtil;

import java.util.List;
import java.util.Map;

public abstract class CacheDaoImpl<T> implements CacheDao<T> {
    private static final String OK = "OK";
    public abstract T mapToEntity(Map<String, String> map);
    public abstract Map<String, String> entityToMap(T entity);

    public boolean save(String key, String value) {
        String result = JedisUtil.set(key, value);
        return OK.equals(result);
    }

    public boolean save(String key, String value, long expire) {
        String result = JedisUtil.set(key, value, expire);
        return OK.equals(result);
    }

    public boolean save(String key, T entity) {
        Map<String, String> map = entityToMap(entity);
        String result = JedisUtil.hmset(key, map);
        return OK.equals(result);
    }

    public boolean delete(String key) {
        long count = JedisUtil.del(key);
        return count == 1;
    }

    public long delete(String... keys) {
        return JedisUtil.del(keys);

    }

    public boolean update(String key, String value) {
        return this.save(key, value);
    }

    public boolean update(String key, T entity) {
        return this.save(key, entity);
    }

    public boolean update(String key, String value, long expire) {
        return this.save(key, value, expire);
    }

    public String get(String key) {
        return JedisUtil.get(key);
    }

    public T getEntity(String key) {
        Map<String, String> map =  JedisUtil.hgetAll(key);
        if (map == null || map.size() < 1) {
            return null;
        }
        return mapToEntity(map);
    }

    public List<T> getList(String key) {
        return null;
    }
}
