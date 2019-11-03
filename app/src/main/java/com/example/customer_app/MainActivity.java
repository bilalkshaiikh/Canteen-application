package com.example.customer_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        check_internet();
    }

    public void check_internet() {
        if (isWorkingInternetPersent()) {
            splash_timer();
            return;
        }
        String str = "Exit";
        dialog = new AlertDialog.Builder(this).setTitle("Connection Failed").setMessage("Please Check Your Internet Connection").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                check_internet();
            }
        }).setNegativeButton(str, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).create();
        dialog.show();
    }

    public void splash_timer() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(MainActivity.this, sign_in_option.class));
                finish();
            }
        }, 5000);
    }

    public boolean isWorkingInternetPersent() {
        @SuppressLint("WrongConstant") ConnectivityManager connectivityManager = (ConnectivityManager) getBaseContext().getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo state : info) {
                    if (state.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
