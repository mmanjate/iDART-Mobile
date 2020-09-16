package mz.org.fgh.idartlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.User;

public class IdartLiteDataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "idartlite.db";
    private static final int    DATABASE_VERSION = 1;
    private UserDao userDao;
    private GenericDao genericDao;
    private EpisodeDao episodeDao;
    private PrescriptionDao prescriptionDao;

    public GenericDao getGenericDao(BaseModel model) throws SQLException {
        if(genericDao == null){
            genericDao = getDao(model.getClass());
        }
        return genericDao;
    }

    public UserDao getUserDao() throws SQLException {
        if(userDao == null){
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public EpisodeDao getEpisodeDao() throws SQLException {
        if(episodeDao == null){
            episodeDao = getDao(Episode.class);
        }
        return episodeDao;
    }

    public PrescriptionDao getPrescriptionDao() throws SQLException {
        if(prescriptionDao == null){
            prescriptionDao = getDao(Prescription.class);
        }
        return prescriptionDao;
    }

    private static IdartLiteDataBaseHelper dataBaseHelper;

    private IdartLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public static IdartLiteDataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null){
            dataBaseHelper = new IdartLiteDataBaseHelper(context);
        }
        return dataBaseHelper;
    }

    public IdartLiteDataBaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, int configFileId) {
        super(context, databaseName, factory, databaseVersion, configFileId);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
