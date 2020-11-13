package mz.org.fgh.idartlite.dao.drug;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;

public interface IDrugDao extends IGenericDao<Drug, Integer> {


    public List<Drug> getAll() throws SQLException;

    public List<Drug> getAllByTherapeuticRegimen(Application application, TherapeuticRegimen therapeuticRegimen) throws SQLException;

    public Drug getDrugByFNMCode(String code) throws SQLException;

    public Drug getDrugByDescription(String description) throws SQLException ;

    public Drug getDrugByRestID(int restId) throws SQLException;

}
