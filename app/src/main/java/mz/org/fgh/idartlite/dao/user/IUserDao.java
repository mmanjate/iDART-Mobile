package mz.org.fgh.idartlite.dao.user;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.User;

public interface IUserDao extends IGenericDao<User, Integer> {
    public boolean login(User user) throws SQLException;

    public boolean checkIfUsertableIsEmpty() throws SQLException;

    User getByCredentials(User user) throws SQLException;

    List<User> getAllUsers() throws SQLException;
}
