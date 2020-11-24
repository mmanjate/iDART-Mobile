package mz.org.fgh.idartlite.base.viewModel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class SearchVM<T extends BaseModel> extends BaseViewModel implements SearchPaginator<T> {

    public static final int PAGE_SIZE = 20;

    public static final int RECORDS_PER_SEARCH = 100;

    protected List<T> searchResults;

    protected List<T> allDisplyedRecords;

    protected int pageSize;

    public SearchVM(@NonNull Application application) {
        super(application);

        this.allDisplyedRecords = new ArrayList<>();
        this.searchResults = new ArrayList<>();
    }

    public void initSearch() throws SQLException {
        this.pageSize = getPageSize();

        this.allDisplyedRecords.clear();
            this.searchResults = doSearch(0, RECORDS_PER_SEARCH);
        if (Utilities.listHasElements(this.searchResults)) {
            loadFirstPage();

            displaySearchResults();
        }else {
            doOnNoRecordFound();
        }
    }

    protected abstract void doOnNoRecordFound();

    private void loadFirstPage() {
        if (getSearchResults().size() < pageSize){
           pageSize = getSearchResults().size();
        }

        for (int i = 0; i <= pageSize -1; i++){
            getAllDisplyedRecords().add(getSearchResults().get(i));
        }
    }

    private void getNextRecordsToDisplay() throws SQLException {

        int end;

        if ((allDisplyedRecords.size()+pageSize) > this.searchResults.size()){
            List<T> recs = doSearch(getSearchResults().size(), RECORDS_PER_SEARCH);

            if (Utilities.listHasElements(recs)){
                this.searchResults.addAll(recs);
                if ((allDisplyedRecords.size()+pageSize) > this.searchResults.size()){
                    end = this.searchResults.size() - 1;
                }else {
                    end = allDisplyedRecords.size()+pageSize-1;
                }
            } else {
                end = this.searchResults.size() - 1;
            }
        }else {
            end = allDisplyedRecords.size()+pageSize-1;
        }

        for (int i = allDisplyedRecords.size(); i <= end; i++){
            allDisplyedRecords.add(this.searchResults.get(i));
        }
    }

    public List<T> getSearchResults() {
        return searchResults;
    }

    public List<T> getAllDisplyedRecords() {
        return allDisplyedRecords;
    }

    private int getPageSize() {
        return PAGE_SIZE;
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

                    try {
                        getNextRecordsToDisplay();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    adapter.notifyDataSetChanged();
                    adapter.setLoaded();

                }
            }, 3000);
        }
    }
}
