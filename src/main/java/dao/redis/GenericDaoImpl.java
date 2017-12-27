package dao.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GenericDaoImpl<T, K extends Serializable> implements GenericDao<T, K> {

    public boolean delete(K id) throws Exception {
        return GenericCommonDao.del(getKey() + ":" + id);
    }

    public long update(T entity) throws Exception {
        return save(entity);
    }

    public T get(K id) throws Exception {
        return get(getKey() + ":" + id);
    }

    public List<T> getAll() throws Exception {
        List<T> list = new ArrayList<T>();
        Set<String> cluster = GenericCommonDao.keys(getPattern());
        for (String key : cluster) {
            T entity = get(key);
            list.add(entity);
        }
        return list;
    }

    public abstract String getKey();
    public abstract String getPattern();
}
