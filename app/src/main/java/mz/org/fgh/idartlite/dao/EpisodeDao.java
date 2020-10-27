package mz.org.fgh.idartlite.dao;

import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public interface EpisodeDao extends GenericDao<Episode, Integer>{

    public List<Episode> getAllByPatient(Patient patient) throws SQLException;

    public Episode getLatestByPatient(Patient patient) throws SQLException;

    public Episode findEpisodeWithStopReasonByPatient(Patient patient) throws SQLException;

    public List<Episode> getAllEpisodeByStatus(String status) throws SQLException;

}
