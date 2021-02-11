package mz.org.fgh.idartlite.dao.user;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements IUserDao {

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
        return queryBuilder().where().eq(User.COLUMN_USER_NAME, user.getUserName()).and().eq(User.COLUMN_PASSWORD, Utilities.MD5Crypt(user.getPassword())).query().isEmpty();
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return queryForAll().isEmpty();
    }

    public User getByCredentials(User user) throws SQLException {
        return queryBuilder().where().eq(User.COLUMN_USER_NAME, user.getUserName()).and().eq(User.COLUMN_PASSWORD, Utilities.MD5Crypt(user.getPassword())).query().get(0);
    }
}
