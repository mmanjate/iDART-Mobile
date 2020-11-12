package mz.org.fgh.idartlite.listener.dialog;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.model.BaseModel;

public interface IListbleDialogListener {

    void remove(int position) throws SQLException;

    void remove(BaseModel baseModel);
}
