package mz.org.fgh.idartlite.workSchedule.work.get;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.rest.service.Stock.RestStockService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_1_ID;

public class StockWorker extends BaseWorker<Stock> {

    public StockWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void doOnStart() {
        try {
            issueNotification(CHANNEL_1_ID, "Sincronização de Stock Iniciada",true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {

        try {
            RestStockService.getStock(this,offset,limit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnFinish() {
        try {
            issueNotification(CHANNEL_1_ID, "Sincronização de Stock Terminada",false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(List<Stock> recs) {

    }
}
