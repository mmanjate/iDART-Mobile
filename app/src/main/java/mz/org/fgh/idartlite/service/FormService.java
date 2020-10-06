package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_DESCRIPTION;

public class FormService extends BaseService {
    public FormService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveForm(Form form) throws SQLException {
        getDataBaseHelper().getFormDao().create(form);
    }

    public Form getForm(String code) throws SQLException {

        List<Form> typeList = getDataBaseHelper().getFormDao().queryForEq(COLUMN_DESCRIPTION,code);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public boolean checkForm(Object form) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) form;

        try {

            Form localForm = getForm((Objects.requireNonNull(itemresult.get("form")).toString()));

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

            localForm.setId((int) Float.parseFloat(Objects.requireNonNull(itemresult.get("regimeid")).toString()));
            localForm.setUnit((Objects.requireNonNull(itemresult.get("formlanguage1")).toString()));
            localForm.setDescription((Objects.requireNonNull(itemresult.get("form")).toString()));
            saveForm(localForm);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
