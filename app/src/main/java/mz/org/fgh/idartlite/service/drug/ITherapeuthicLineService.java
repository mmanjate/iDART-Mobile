package mz.org.fgh.idartlite.service.drug;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.TherapeuticLine;


public interface ITherapeuthicLineService extends IBaseService<TherapeuticLine> {

    public void createTherapheuticLine(TherapeuticLine therapeuticLine) throws SQLException;

    public List<TherapeuticLine> getAll() throws SQLException;

    public TherapeuticLine getTherapeuticLineByCode(String code) throws SQLException;

    public boolean checkLine(Object line);

    public void saveLine(Object line);


}
