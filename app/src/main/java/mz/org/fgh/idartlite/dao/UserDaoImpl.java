package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.User;

public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements UserDao {

    public UserDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public UserDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public boolean login(User user) throws SQLException {
        return queryBuilder().where().eq(User.COLUMN_USER_NAME, user.getUserName()).and().eq(User.COLUMN_PASSWORD, user.getPassword()).query().isEmpty();
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return queryForAll().isEmpty();
    }
}
