package mz.org.fgh.idartlite.workSchedule.work.generic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;

public class StockAlertWorker extends BaseWorker<StockReportData> {
    private IDispenseService dispenseService;

    public StockAlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dispenseService = new DispenseService(BaseService.getApp());
    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        dispenseService.getStockAlertReportMonthPeriod();
    }

    @Override
    protected void doOnStart() {

    }

    @Override
    protected void doOnFinish() {

    }

    @Override
    protected void doSave(List<StockReportData> recs) {

    }
}
