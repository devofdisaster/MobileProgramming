package com.example.piotr.lab3;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class UnboundService extends Service implements AsyncTaskService {
    public UnboundService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Your service has been started", Toast.LENGTH_SHORT).show();
        new ToastTask(this, 5000).execute("Your service is still working");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void handleProgress() {

    }
}
