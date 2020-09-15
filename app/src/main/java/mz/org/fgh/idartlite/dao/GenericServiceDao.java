package mz.org.fgh.idartlite.dao;

import android.app.Application;

import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;

public class GenericServiceDao extends BaseService {

    public GenericServiceDao(Application application) {
        super(application);
    }

    public List findAllGenericObjectByClass(Class clazz) throws SQLException {
        return getDataBaseHelper().getDao(clazz).queryForAll();
    }

    public List findAllGenericObjectByQuery(PreparedQuery query, Class clazz) throws SQLException {
        return getDataBaseHelper().getDao(clazz).query(query);
    }

    public List findAllGenericObjectById(Class clazz, Long id) throws SQLException {
        return getDataBaseHelper().getDao(clazz).queryForEq("id", id);
    }

    public List findAllGenericObjectByField(Class clazz, String Field, String value) throws SQLException {
        return getDataBaseHelper().getDao(clazz).queryForEq(Field, value);
    }

    public List findAllGenericObjectByField(Class clazz, String Field, int value) throws SQLException {
        return getDataBaseHelper().getDao(clazz).queryForEq(Field, value);
    }

    public void saveGenericObjectByClass(Class clazz, Object obj) throws SQLException {
        getDataBaseHelper().getDao(clazz).create(obj);
    }

    public void deleteGenericObjectByClass(Class clazz) throws SQLException {
        getDataBaseHelper().getDao(clazz).delete(clazz);
    }

    public void updateGenericObjectByClass(Class clazz) throws SQLException {
        getDataBaseHelper().getDao(clazz).update(clazz);
    }
}
