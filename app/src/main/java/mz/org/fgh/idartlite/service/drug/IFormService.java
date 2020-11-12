package mz.org.fgh.idartlite.service.drug;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_DESCRIPTION;


public interface IFormService extends IBaseService {

    public void saveForm(Form form) throws SQLException ;

    public Form getForm(String code) throws SQLException ;

    public boolean checkForm(Object form);

    public void saveOnForm(Object form);


}
