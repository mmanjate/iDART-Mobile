package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.User;

public interface UserDao extends GenericDao<User, Integer> {
    public boolean login(User user) throws SQLException;

    public boolean checkIfUsertableIsEmpty() throws SQLException;
}
