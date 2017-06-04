package com.example.piotr.lab4;


import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity
    implements BlankFragment1.OnFragmentInteractionListener, BlankFragment2.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new BlankFragment1());
        setOnClickListeners();
    }

    private void setOnClickListeners()
    {
        findViewById(R.id.button_activity_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivity2(v);
            }
        });

        findViewById(R.id.button_frag_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new BlankFragment1(), true);
            }
        });

        findViewById(R.id.button_frag_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new BlankFragment2(), true);
            }
        });
    }

    private void changeFragment(Fragment replacement)
    {
        changeFragment(replacement, false);
    }

    private void changeFragment(Fragment replacement, boolean backStack)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentFrame, replacement);

        if (backStack) {
            transaction.addToBackStack("Changed fragments");
        }

        transaction.commit();
    }

    public void showActivity2(View view)
    {
        startActivity(new Intent(this, Activity2.class));
    }


    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }
}
