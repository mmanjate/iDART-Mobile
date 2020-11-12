package mz.org.fgh.idartlite.common;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.model.BaseModel;

public interface ListbleDialogListener  {

    void remove(int position) throws SQLException;

    void remove(BaseModel baseModel);
}
