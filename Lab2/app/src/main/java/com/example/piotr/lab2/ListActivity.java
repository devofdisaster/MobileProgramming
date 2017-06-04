package com.example.piotr.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = (ListView) findViewById(R.id.listView);

        ArrayList<Student> studenty = new ArrayList<>();
        studenty.add(new Student("Wiktor", "Traktor"));
        studenty.add(new Student("Smok", "Wawelski"));
        studenty.add(new Student("Tytus", "deZoo"));
        studenty.add(new Student("Don", "Pedro"));


        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                studenty
        );



        list.setAdapter(adapter);
    }
}
