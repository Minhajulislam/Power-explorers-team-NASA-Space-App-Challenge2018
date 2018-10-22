package com.google.wildfiremonitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
    }

    public void CheckPage(View view) {
        Intent myIntent = new Intent(MainActivity.this, CheckDeviceInfo.class);
        //myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
    }

    public void UpdatePage(View view) {
        progressDialog.show();
        Intent myIntent = new Intent(MainActivity.this, UpdateDeviceInfo.class);
        //myIntent.putExtra("key", value); //Optional parameters
        UpdateDeviceInfo updateDeviceInfo = new UpdateDeviceInfo();
        MainActivity.this.startActivity(myIntent);
        progressDialog.dismiss();
    }
}
