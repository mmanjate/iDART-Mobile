package mz.org.fgh.idartlite.base;

import java.sql.SQLException;
import java.util.List;

public interface SearchPaginator<T extends BaseModel>{

    List<T> doSearch() throws SQLException;

    int getPageSize();
}
