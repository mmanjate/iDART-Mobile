package mz.org.fgh.idartlite.dao.drug;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.TherapeuticLine;

public interface ITherapeuticLineDao extends IGenericDao<TherapeuticLine, Integer> {

    public List<TherapeuticLine> getAll() throws SQLException;

    public TherapeuticLine getTherapeuticLineByCode(String code) throws SQLException;
}
