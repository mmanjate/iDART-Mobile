package mz.org.fgh.idartlite.base.viewModel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.adapter.recyclerview.generic.AbstractRecycleViewAdapter;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class SearchVM<T extends BaseModel> extends BaseViewModel implements SearchPaginator<T> {

    public static final int PAGE_SIZE = 20;

    public static final int RECORDS_PER_SEARCH = 100;

    protected boolean paginatedSearch;

    protected List<T> searchResults;

    protected List<T> allDisplyedRecords;

    protected AbstractSearchParams<T> searchParams;

    protected int pageSize;

    public SearchVM(@NonNull Application application) {
        super(application);

        this.allDisplyedRecords = new ArrayList<>();
        this.searchResults = new ArrayList<>();

        activatePaginatedSearch();

        this.searchParams = initSearchParams();
    }

    public void initSearch() {
        try {
        String errors = validateBeforeSearch();
        if (Utilities.stringHasValue(errors)) {
            displayErrors(errors);
            return;
        }

        this.pageSize = getPageSize();

        this.allDisplyedRecords.clear();

        if (isPaginatedSearch()) {
            this.searchResults = doSearch(0, RECORDS_PER_SEARCH);
        }else this.searchResults = doSearch(0, 0);

        if (Utilities.listHasElements(this.searchResults)) {
            loadFirstPage();

            displaySearchResults();
        }else {
            doOnNoRecordFound();
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        notifyChange();
    }

    public String validateBeforeSearch(){
        if (getSearchParams() == null) return  null;

        if (getSearchParams().isByDateInterval()) return validateDateInterval();

        return null;
    }

    private String validateDateInterval() {
        if (DateUtilities.dateDiff(getSearchParams().getEndDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data inicio deve ser menor que a data fim.";
        }
        return null;
    }

    private void displayErrors(String errors) {
        Utilities.displayAlertDialog(getRelatedActivity(), errors).show();
    }

    protected abstract void doOnNoRecordFound();

    private void loadFirstPage() {
        if (isPaginatedSearch()) {
            if (getSearchResults().size() < pageSize) {
                pageSize = getSearchResults().size();
            }

            for (int i = 0; i <= pageSize - 1; i++) {
                getAllDisplyedRecords().add(getSearchResults().get(i));
            }
        }else {
            getAllDisplyedRecords().clear();
            getAllDisplyedRecords().addAll(getSearchResults());
        }
    }

    public AbstractSearchParams<T> getSearchParams() {
        return searchParams;
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

    @Bindable
    public List<T> getAllDisplyedRecords() {
        return allDisplyedRecords;
    }

    private int getPageSize() {
        return PAGE_SIZE;
    }

    public void loadMoreRecords(RecyclerView rv, AbstractRecycleViewAdapter adapter) {
        if (isPaginatedSearch() && getAllDisplyedRecords().size() < getSearchResults().size()){
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
            }, 8000);
        }
    }

    @Bindable
    public String getStartDate() {
        return DateUtilities.formatToDDMMYYYY(getSearchParams().getStartdate());
    }

    public void setStartDate(String startDate) {
        getSearchParams().setStartdate(DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT));
        notifyPropertyChanged(BR.startDate);
    }

    @Bindable
    public String getEndDate() {
        return DateUtilities.formatToDDMMYYYY(getSearchParams().getEndDate());
    }

    public void setEndDate(String endDate) {
        getSearchParams().setEndDate(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT));
        notifyPropertyChanged(BR.endDate);
    }

    public void deActivatePaginatedSearch(){
        this.paginatedSearch = false;
    }

    public void activatePaginatedSearch(){
        this.paginatedSearch = true;
    }

    public boolean isPaginatedSearch(){
        return this.paginatedSearch;
    }

    public void generatePDF() throws SQLException {
        deActivatePaginatedSearch();
        getSearchResults().clear();
        this.searchResults = doSearch(0, 0);
        getAllDisplyedRecords().clear();
        getAllDisplyedRecords().addAll(getSearchResults());

        activatePaginatedSearch();
    }
}
