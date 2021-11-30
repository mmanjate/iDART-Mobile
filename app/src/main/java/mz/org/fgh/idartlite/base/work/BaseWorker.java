package mz.org.fgh.idartlite.base.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.SearchPaginator;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class BaseWorker<T extends BaseModel> extends Worker implements SearchPaginator<T>, RestResponseListener<T> {


    public static final int RECORDS_PER_SEARCH = 100;
    private int offset = 0;
    public static final String WORK_STATUS_PERFORMING = "PERFORMING";
    public static final String WORK_STATUS_FINISHED = "FINISHED";
    public static final String WORK_STATUS_STARTING = "STARTING";
    protected String workStatus;

    public BaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setWorkStatus(WORK_STATUS_STARTING);
    }

    @Override
    public Result doWork() {
        try {
            changeStatusToPerforming();
            fullLoadRecords();
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    @Override
    public List<T> doSearch(long offset, long limit) throws SQLException {
        return null;
    }

    @Override
    public void displaySearchResults() {

    }

    @Override
    public AbstractSearchParams<T> initSearchParams() {
        return null;
    }

    protected void fullLoadRecords() throws SQLException {
        doOnlineSearch(this.offset, RECORDS_PER_SEARCH);
    }

    protected void doAfterSearch(List<T> recs) throws SQLException {
        if (Utilities.listHasElements(recs)) {
            this.offset = this.offset + recs.size();
            doSave(recs);
            fullLoadRecords();
        } else {
            changeStatusToFinished();
            doOnFinish();
        }
    }

    protected abstract void doOnFinish();

    protected abstract void doSave(List<T> recs);

    @Override
    public void doOnRestSucessResponse(String flag) {

    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }

    @Override
    public void doOnRestSucessResponseObjects(String flag, List<T> objects) {

    }

    @Override
    public void doOnResponse(String flag, List<T> objects) {
        try {
            doAfterSearch(objects);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void changeStatusToPerforming () {
        this.setWorkStatus(WORK_STATUS_PERFORMING);
    }

    protected void changeStatusToFinished () {
        this.setWorkStatus(WORK_STATUS_FINISHED);
    }

    protected boolean isRunning () {
        return this.workStatus.equals(WORK_STATUS_PERFORMING);
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }
}
