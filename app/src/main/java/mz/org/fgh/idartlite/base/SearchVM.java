package mz.org.fgh.idartlite.base;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.adapter.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class SearchVM<T extends BaseModel> extends BaseViewModel implements SearchPaginator<T>{

    protected List<T> searchResults;

    protected List<T> allDisplyedRecords;

    protected int pageSize;

    public SearchVM(@NonNull Application application) {
        super(application);

        this.allDisplyedRecords = new ArrayList<>();
    }

    public void initSearch() throws SQLException {
        this.pageSize = getPageSize();

        this.allDisplyedRecords.clear();

        this.searchResults = doSearch();

        if (Utilities.listHasElements(this.searchResults)) {
            loadFirstPage();

            displaySearchResults();
        }else {
            Utilities.displayAlertDialog(getRelatedActivity(),"Não foram encontrados resultados para a sua pesquisa").show();
        }
    }

    private void loadFirstPage() {

        if (getSearchResults().size() < pageSize){
           pageSize = getSearchResults().size();
        }

        for (int i = 0; i <= pageSize -1; i++){
            getAllDisplyedRecords().add(getSearchResults().get(i));
        }
    }

    protected abstract void displaySearchResults();

    public List<T> getNextRecordsToDisplay(){
        List<T> morePatients = new ArrayList<>();
        int end;

        if ((allDisplyedRecords.size()+pageSize) > this.searchResults.size()){
            end = this.searchResults.size() - 1;
        }else {
            end = allDisplyedRecords.size()+pageSize-1;
        }

        for (int i = allDisplyedRecords.size(); i <= end; i++){
            morePatients.add(this.searchResults.get(i));

        }
        return morePatients;
    }

    public List<T> getSearchResults() {
        return searchResults;
    }

    public List<T> getAllDisplyedRecords() {
        return allDisplyedRecords;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    public void loadMoreRecords(RecyclerView rv, AbstractRecycleViewAdapter adapter) {
        if (getAllDisplyedRecords().size() < getSearchResults().size()){
            getAllDisplyedRecords().add(null);
            rv.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyItemInserted(getAllDisplyedRecords().size() - 1);
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getAllDisplyedRecords().remove(getAllDisplyedRecords().size() - 1);
                    adapter.notifyItemRemoved(getAllDisplyedRecords().size());

                    getAllDisplyedRecords().addAll(getNextRecordsToDisplay());

                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();

                }
            }, 3000);
        }
    }
}
