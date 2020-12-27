package com.example.Ohm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MainScreen myScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            myScreen = new MainScreen();
            getSupportFragmentManager().beginTransaction().add(R.id.main, myScreen).commit();
        }
    }
}