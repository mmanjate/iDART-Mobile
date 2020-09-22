package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.User;

public class FormService extends BaseService {
    public FormService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveForm(Form form) throws SQLException {
        getDataBaseHelper().getFormDao().create(form);
    }
}
