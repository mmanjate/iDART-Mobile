package mz.org.fgh.idartlite.service.drug;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.DispenseType;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_CODE;


public interface IDiseaseTypeService extends IBaseService {

    public void saveDiseaseType(DiseaseType diseaseType) throws SQLException;

    public List<DiseaseType> getAllDiseaseTypes() throws SQLException ;

    public DiseaseType getDiseaseTypeByCode(String code) throws SQLException ;

    public boolean checkDisease(Object disease);

    public void saveDisease(Object disease);


}
