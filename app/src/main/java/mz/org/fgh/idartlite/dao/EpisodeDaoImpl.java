package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.Episode;

import java.sql.SQLException;

public class EpisodeDaoImpl extends GenericDaoImpl<Episode, Integer> implements EpisodeDao{

    public EpisodeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public EpisodeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public EpisodeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
