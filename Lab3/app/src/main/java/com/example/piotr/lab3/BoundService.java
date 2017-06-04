package com.example.piotr.lab3;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class BoundService extends IntentService implements AsyncTaskService {
    private Integer count = 0;
    private BoundServiceBinder binder = new BoundServiceBinder();
    private ToastTask task = new ToastTask(this, 8000);

    public BoundService() {
        super("BoundService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(
            getApplicationContext(),
            "Your bound service has been started",
            Toast.LENGTH_SHORT
        ).show();

        task.execute("Your bound service is still working");

        return binder;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        task.cancel(true);
        return super.onUnbind(intent);
    }

    public Integer getCount() {
        return count;
    }

    public void handleProgress() {
        count++;
    }

    public class BoundServiceBinder extends Binder {
        public BoundService getService() {
            return BoundService.this;
        }
    }
}
