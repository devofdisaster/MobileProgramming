package com.example.piotr.lab4;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Activity2 extends AppCompatActivity {
    private ChargeReceiver receiver = new ChargeReceiver();
    private static final String ACTION_FAKE_POWER_CONNECTED = "ACTION_FAKE_POWER_CONNECTED";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        setListeners();
        registerPowerConnectedReceiver();
        fillInput();
    }

    private void setListeners()
    {

        findViewById(R.id.button_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPowerConnectedEvent();
            }
        });

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInput();
            }
        });
    }

    private void fillInput() {
        String saved = getPreferences(MODE_PRIVATE).getString("myInput", "");

        ((EditText) findViewById(R.id.editText)).setText(saved);
    }

    private void registerPowerConnectedReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(ACTION_FAKE_POWER_CONNECTED);
        registerReceiver(receiver, filter, Manifest.permission.BLUETOOTH, null);
    }

    public void sendPowerConnectedEvent()
    {
        sendBroadcast(new Intent(ACTION_FAKE_POWER_CONNECTED));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void saveInput() {
        SharedPreferences storage = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();

        editor.putString("myInput", ((EditText) findViewById(R.id.editText)).getText().toString());
        editor.commit();
    }
}
