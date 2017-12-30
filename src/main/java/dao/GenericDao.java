package dao;

import java.util.List;
import java.util.Map;

public interface GenericDao<T> {
    void save(T entity);

    void delete(T entity);

    void update(T entity);

    T query(final String condition);

    T query(final String condition, final Map<String, Object> params);

    List<T> queryList();

    List<T> queryList(final String condition);

    List<T> queryList(final String condition, final Map<String, Object> params);
}
