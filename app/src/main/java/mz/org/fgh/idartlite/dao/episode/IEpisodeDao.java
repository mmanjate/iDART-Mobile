package mz.org.fgh.idartlite.dao.episode;

import android.app.Application;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IEpisodeDao extends IGenericDao<Episode, Integer> {

    public List<Episode> getAllByPatient(Patient patient) throws SQLException;

    public Episode getLatestByPatient(Patient patient) throws SQLException;

    public Episode findEpisodeWithStopReasonByPatient(Patient patient) throws SQLException;

    public List<Episode> getAllEpisodeByStatus(String status) throws SQLException;


    public List<Episode> getAllStartEpisodesBetweenStartDateAndEndDate(Date start, Date end, long offset, long limit) throws SQLException;

}
