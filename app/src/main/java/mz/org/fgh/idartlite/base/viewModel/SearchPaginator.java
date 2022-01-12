package mz.org.fgh.idartlite.base.viewModel;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;

public interface SearchPaginator<T extends BaseModel>{

    List<T> doSearch(long offset, long limit) throws SQLException;

    void displaySearchResults();

    AbstractSearchParams<T> initSearchParams();

    default void doOnlineSearch(long offset, long limit) throws SQLException {}

    default void createPdfDocument() {}

    default Collection<?> doBeforeDisplay(List<T> objects) {
        return null;
    }

}
