package mz.org.fgh.idartlite.service.drug;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_DESCRIPTION;


public interface ITherapeuthicLineService extends IBaseService {

    public void createTherapheuticLine(TherapeuticLine therapeuticLine) throws SQLException;

    public List<TherapeuticLine> getAll() throws SQLException;

    public TherapeuticLine getTherapeuticLineByCode(String code) throws SQLException;

    public boolean checkLine(Object line);

    public void saveLine(Object line);


}
