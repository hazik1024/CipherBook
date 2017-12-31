package cache;

import java.util.List;

public abstract class CacheDaoImpl<T> implements CacheDao<T> {
    public abstract T getEntity();
    public abstract void setEntity(T entity);

    public void save(String key, String value) {

    }

    public void save(String key, String value, int expire) {

    }

    public void save(String key, String subKey, String value, int expire) {

    }

    public void save(String key, T value) {

    }

    public void delete(String key) {

    }

    public void update(String key, Object value) {

    }

    public T get(String key) {
        return null;
    }

    public List<T> getList(String key) {
        return null;
    }
}
