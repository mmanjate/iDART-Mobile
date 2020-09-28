package mz.org.fgh.idartlite.dao;

import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

public interface EpisodeDao extends GenericDao<Episode, Integer>{

    public List<Episode> getAllByPatient(Patient patient) throws SQLException;

    public Episode getLatestByPatientAndSanitryUuid(Patient patient,String uuid) throws SQLException;
}
