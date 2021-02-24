package mz.org.fgh.idartlite.workSchedule.work.generic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class AbstractWorker extends Worker {

    protected ServiceWatcher watcher;

    public AbstractWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.watcher = ServiceWatcher.fastCreate();
    }

    @NonNull
    @Override
    public Result doWork() {
        return null;
    }

    protected void issueNotification(String channel) throws InterruptedException {
        Thread.sleep(6000);

        if (watcher.hasUpdates()) Utilities.issueNotification(NotificationManagerCompat.from(getApplicationContext()), getApplicationContext(), watcher.getUpdates(), channel);
    }
}
