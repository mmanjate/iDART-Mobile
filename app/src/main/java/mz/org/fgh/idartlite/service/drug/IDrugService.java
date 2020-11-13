package mz.org.fgh.idartlite.service.drug;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;

import static java.util.Objects.requireNonNull;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_DESCRIPTION;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_FNMCODE;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_REST_ID;


public interface IDrugService extends IBaseService {

    public void saveDrug(Drug drug) throws SQLException;

    public List<Drug> getAll() throws SQLException;

    public List<Drug> getAllByTherapeuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException;

    public Drug getDrugByFNMCode(String code) throws SQLException;

    public Drug getDrugByDescription(String description) throws SQLException ;

    public Drug getDrugByRestID(int restId) throws SQLException;

    public boolean checkDrug(Object drug) ;

    public void saveOnDrug(Object drug);

    public List<Drug> getDrugsWithoutRectParanthesis(List<Drug> drugs) throws SQLException ;


}
