package com.ronlu.ratemyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Use this for initiate Rate Dialog
        RateMyApp.reteMeOnMarketDialog(this, this, R.mipmap.ic_launcher, new RateMyApp.FeedbackReceivedListener() {
            @Override
            public void onFeedbackReceived(String feedback) {
                // Handle your feedback here.
            }
        });
    }
















}