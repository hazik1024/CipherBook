package cache;

import java.util.List;

public interface CacheDao<T> {
    void save(String key, String value);
    void save(String key, T entity);
    void delete(String key);
    void update(String key, Object value);
    T get(String key);
    List<T> getList(String key);
}
