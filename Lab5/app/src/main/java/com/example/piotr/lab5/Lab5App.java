package com.example.piotr.lab5;


import android.app.Application;

import com.example.piotr.lab5.database.OpenHelper;
import com.example.piotr.lab5.database.SQLiteDataManager;

public class Lab5App extends Application {

    private OpenHelper dbHelper;
    private SQLiteDataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new OpenHelper(this);
        dataManager = new SQLiteDataManager(dbHelper);
    }

    @Override
    public void onTerminate() {
        dbHelper.close();
        super.onTerminate();
    }

    public SQLiteDataManager getDataManager() {
        return dataManager;
    }
}
