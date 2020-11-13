package mz.org.fgh.idartlite.dao.drug;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.DiseaseType;

public interface IDiseaseTypeDao extends IGenericDao<DiseaseType, Integer> {

    public List<DiseaseType> getAllDiseaseTypes() throws SQLException;

    public DiseaseType getDiseaseTypeByCode(String code) throws SQLException ;
}
