package dao.redis;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, K extends Serializable> {
    public long save(T entity) throws Exception;
    public boolean delete(T entity) throws Exception;
    public boolean delete(K id) throws Exception;
    public long update(T entity) throws Exception;

    public T get(K id) throws Exception;
    public T get(T entity) throws Exception;
    public T get(String key) throws Exception;
    public List<T> getAll() throws Exception;

}
