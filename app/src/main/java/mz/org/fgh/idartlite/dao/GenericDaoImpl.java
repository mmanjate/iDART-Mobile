package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

public class GenericDaoImpl<T, ID> extends BaseDaoImpl<T, ID> implements GenericDao<T, ID> {

    public GenericDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List findAllGenericObjectByClass(Class clazz) throws SQLException {
        return queryForAll();
    }

    public List findAllGenericObjectByQuery(PreparedQuery query, Class clazz) throws SQLException {
        return query(query);
    }

    public List findAllGenericObjectById(Class clazz, Long id) throws SQLException {
        return queryForEq("id", id);
    }

    public List findAllGenericObjectByField(Class clazz, String Field, String value) throws SQLException {
        return queryForEq(Field, value);
    }

    public List findAllGenericObjectByField(Class clazz, String Field, int value) throws SQLException {
        return queryForEq(Field, value);
    }

    public void saveGenericObjectByClass(T object) throws SQLException {
        create(object);
    }

    public void deleteGenericObjectByClass(T object) throws SQLException {
        delete(object);
    }

    public void updateGenericObjectByClass(T object) throws SQLException {
        update(object);
    }
}
