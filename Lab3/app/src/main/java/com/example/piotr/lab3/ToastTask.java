package com.example.piotr.lab3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class ToastTask extends AsyncTask<String, String, String> {

    private Context context;
    private Integer delay;

    public ToastTask(Context context, Integer delay) {
        this.context = context;
        this.delay = delay;
    }

    @Override
    protected String doInBackground(String... messages) {
        while(true) {
            try {
                sleep(delay);
                publishProgress(messages[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... messages) {
        Toast.makeText(context, messages[0], Toast.LENGTH_SHORT).show();

        if (context instanceof AsyncTaskService) {
            ((AsyncTaskService) context).handleProgress();
        }

        super.onProgressUpdate(messages);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
