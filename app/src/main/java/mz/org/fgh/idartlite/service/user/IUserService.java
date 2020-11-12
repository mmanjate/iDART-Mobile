package mz.org.fgh.idartlite.service.user;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;


public interface IUserService extends IBaseService {

    public boolean login(User user) throws SQLException;

    public boolean checkIfUsertableIsEmpty() throws SQLException ;

    public void saveUser(User user) throws SQLException ;

}
