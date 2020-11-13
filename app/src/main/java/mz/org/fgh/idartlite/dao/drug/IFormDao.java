package mz.org.fgh.idartlite.dao.drug;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Form;

public interface IFormDao extends IGenericDao<Form, Integer> {

    public Form getFormByDescription(String description) throws SQLException;
}
