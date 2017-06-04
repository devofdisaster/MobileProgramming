package com.example.piotr.lab4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;

public class ChargeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence msg;

        if (intent.getAction() == Intent.ACTION_POWER_CONNECTED) {
            msg = context.getResources().getText(R.string.notification_charging);
        } else {
            msg = context.getResources().getText(R.string.notification_charging_fake);
        }

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
