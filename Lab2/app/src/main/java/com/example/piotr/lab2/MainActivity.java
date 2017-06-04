package com.example.piotr.lab2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	Context context;	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

//        View view = findViewById(R.id.button);
//        view.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                startNewActivity(v);
//            }
//        });
        
    }

    /**
     *
     * @param view
     */
    public void startNewActivity(View view) {
        Intent intent = new Intent(context, ListActivity.class);
        startActivity(intent);
    }
}
