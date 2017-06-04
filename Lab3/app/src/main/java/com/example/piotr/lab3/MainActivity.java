package com.example.piotr.lab3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    BoundService boundService;
    boolean isServiceBound;
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            boundService = ((BoundService.BoundServiceBinder) service).getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, UnboundService.class));
        bindService(new Intent(this, BoundService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        if (isServiceBound) {
            unbindService(conn);
            isServiceBound = false;
        }

        super.onStop();
    }

    public void startNewActivity(View view) {
        if (isServiceBound) {
            unbindService(conn);
            isServiceBound = false;
        }

        startActivity(new Intent(this, ListActivity.class));
    }

    public void getCount(View view) {
        Toast.makeText(
            this,
            (isServiceBound ? boundService.getCount() : "No service is bound") + "",
            Toast.LENGTH_SHORT
        ).show();
    }
}
