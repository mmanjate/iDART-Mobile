package mz.org.fgh.idartlite.service.drug;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.User;

public class FormService extends BaseService<Form> implements IFormService {
    public FormService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public FormService(Application application) {
        super(application);
    }

    @Override
    public void save(Form record) throws SQLException {

    }

    @Override
    public void update(Form record) throws SQLException {

    }

    public void saveForm(Form form) throws SQLException {
        getDataBaseHelper().getFormDao().create(form);
    }

    public Form getFormByDescription(String description) throws SQLException {

        Form type = getDataBaseHelper().getFormDao().getFormByDescription(description);

        if (type != null) return type;

        return null;
    }

    public boolean checkForm(Object form) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) form;

        try {

            Form localForm = getFormByDescription((Objects.requireNonNull(itemresult.get("form")).toString()));

            if(localForm != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void saveOnForm(Object form){

        Form localForm = new Form();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) form;

            localForm.setRestId((int) Float.parseFloat(Objects.requireNonNull(itemresult.get("id")).toString()));
            localForm.setUnit((Objects.requireNonNull(itemresult.get("formlanguage1")).toString()));
            localForm.setDescription((Objects.requireNonNull(itemresult.get("form")).toString()));
            saveForm(localForm);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
