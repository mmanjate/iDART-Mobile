package mz.org.fgh.idartlite.service.drug;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Form;


public interface IFormService extends IBaseService<Form> {

    public void saveForm(Form form) throws SQLException ;

    public Form getFormByDescription(String description) throws SQLException ;

    public boolean checkForm(Object form);

    public void saveOnForm(Object form);


}
