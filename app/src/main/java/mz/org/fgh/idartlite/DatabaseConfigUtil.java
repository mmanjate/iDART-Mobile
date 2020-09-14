package mz.org.fgh.idartlite;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import mz.org.fgh.idartlite.model.User;

public class DatabaseConfigUtil  extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            User.class

    };

    public static void main(String[] args) throws SQLException, IOException {

        // Provide the name of .txt file which you have already created and kept in res/raw directory
        //writeConfigFile();
        writeConfigFile("ormlite_config.txt", classes);
    }
}
