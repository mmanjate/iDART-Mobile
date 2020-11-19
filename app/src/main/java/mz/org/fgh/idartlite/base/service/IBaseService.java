package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.model.User;


public interface IBaseService<T extends BaseModel> {

    void init(Application application, User currentUser);

    void save(T record) throws SQLException;


    void update(T relatedRecord) throws SQLException;

    void deleteRecord(T selectedRecord) throws SQLException;


}
