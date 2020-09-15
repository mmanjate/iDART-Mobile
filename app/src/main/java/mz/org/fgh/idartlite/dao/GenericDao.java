package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T, ID> extends Dao<T, ID> {
    public List findAllGenericObjectByClass(Class clazz) throws SQLException;

    public List findAllGenericObjectByQuery(PreparedQuery query, Class clazz) throws SQLException;

    public List findAllGenericObjectById(Class clazz, Long id) throws SQLException;

    public List findAllGenericObjectByField(Class clazz, String Field, String value) throws SQLException;

    public List findAllGenericObjectByField(Class clazz, String Field, int value) throws SQLException;

    public void saveGenericObjectByClass(T object) throws SQLException;

    public void deleteGenericObjectByClass(T object) throws SQLException;

    public void updateGenericObjectByClass(T object) throws SQLException;
}
