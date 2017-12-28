package dao;

import java.util.List;
import java.util.Map;

public interface GenericDao<T> {
    void save(T entity);

    void delete(T entity);

    void update(T entity);

    T query(final String queryString);

    T query(final String queryString, final Map<String, Object> params);

    List<T> queryList(final String queryString);

    List<T> queryList(final String queryString, final Map<String, Object> params);
}
