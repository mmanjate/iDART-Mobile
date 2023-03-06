package mz.org.fgh.idartlite.service.user;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.User;


public interface IUserService extends IBaseService<User> {

    void authenticate(User record) throws SQLException;

    public boolean login(User user) throws SQLException;

    public boolean checkIfUsertableIsEmpty() throws SQLException ;

    public void saveUser(User user) throws SQLException ;

    User getByUserNameAndPassword(User currentUser);

    List<User> getAllUsers() throws SQLException;
}
