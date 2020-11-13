package mz.org.fgh.idartlite.service.drug;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.util.Utilities;

import static mz.org.fgh.idartlite.model.TherapeuticRegimen.COLUMN_DESCRIPTION;
import static mz.org.fgh.idartlite.model.TherapeuticRegimen.COLUMN_REGIMEN_CODE;


public interface ITherapheuticRegimenService extends IBaseService {

    public void createTherapheuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException;

    public List<TherapeuticRegimen> getAll() throws SQLException ;

    public TherapeuticRegimen getTherapeuticRegimenByDescription(String description) throws SQLException ;

    public TherapeuticRegimen getTherapeuticRegimenByCode(String code) throws SQLException ;

    public boolean checkRegimen(Object regimen) ;

    public void saveRegimen(Object regimen);

}
