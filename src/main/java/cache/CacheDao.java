package cache;

import java.util.List;

public interface CacheDao<T> {
    boolean save(String key, String value);
    boolean save(String key, String value, int expire);
    boolean save(String key, T entity);
    boolean delete(String key);
    long delete(String... keys);
    boolean update(String key, String value);
    boolean update(String key, String value, int expire);
    boolean update(String key, T entity);
    String get(String key);
    T getEntity(String key);
    List<T> getList(String key);
}
