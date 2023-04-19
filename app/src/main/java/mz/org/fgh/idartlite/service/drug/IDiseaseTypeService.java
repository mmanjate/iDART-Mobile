package mz.org.fgh.idartlite.service.drug;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DiseaseType;


public interface IDiseaseTypeService extends IBaseService<DiseaseType> {

    public void saveDiseaseType(DiseaseType diseaseType) throws SQLException;

    public List<DiseaseType> getAllDiseaseTypes() throws SQLException ;

    public DiseaseType getDiseaseTypeByCode(String code) throws SQLException ;

    public boolean checkDisease(Object disease);

    public void saveDisease(Object disease);


}
