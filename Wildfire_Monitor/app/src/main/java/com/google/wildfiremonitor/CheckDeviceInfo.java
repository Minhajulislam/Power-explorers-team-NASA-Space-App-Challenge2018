package com.google.wildfiremonitor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckDeviceInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "CheckDeviceInfo";

    private ListView dInfoLV;
    private Button refreshBtn;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private String deviceNo;
    private int count;
    private Spinner deviceSpinner;
    ArrayAdapter<String> adapterDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_device_info);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();


        dInfoLV = findViewById(R.id.listView);
        refreshBtn = findViewById(R.id.refreshBtn);

        deviceSpinner = findViewById(R.id.spinnerDevice);
        deviceSpinner.setOnItemSelectedListener(this);


        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("device1");
        arrayList1.add("device2");
        arrayList1.add("device3");

        ArrayAdapter deviceAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList1);
        deviceSpinner.setAdapter(deviceAdapter);

    }


    public void showData(DataSnapshot dataSnapshot) {

        Toast.makeText(CheckDeviceInfo.this,deviceNo,Toast.LENGTH_SHORT).show();


        for (DataSnapshot ds : dataSnapshot.getChildren()){
            DeviceInfo deviceInfo = new DeviceInfo();

            deviceInfo.setDeviceID(ds.child(deviceNo).getValue(DeviceInfo.class).getDeviceID());
            deviceInfo.setDevLocation(ds.child(deviceNo).getValue(DeviceInfo.class).getDevLocation());
            deviceInfo.setTemperature(ds.child(deviceNo).getValue(DeviceInfo.class).getTemperature());
            deviceInfo.setSmoke(ds.child(deviceNo).getValue(DeviceInfo.class).getSmoke());
            deviceInfo.setFire(ds.child(deviceNo).getValue(DeviceInfo.class).getFire());
            deviceInfo.setStatus(ds.child(deviceNo).getValue(DeviceInfo.class).getStatus());


            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("ID: "+deviceInfo.getDeviceID());
            arrayList.add("Location: "+deviceInfo.getDevLocation());
            arrayList.add("Temperatue: "+deviceInfo.getTemperature());
            arrayList.add("Smoke: "+deviceInfo.getSmoke());
            arrayList.add("Fire: "+deviceInfo.getFire());
            arrayList.add("Status: "+deviceInfo.getStatus());

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
            dInfoLV.setAdapter(adapter);

        }
    }


    /*@Override
    public void onClick(View view) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (view == refreshBtn){
            Toast.makeText(this,"Refresh", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        deviceNo = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(CheckDeviceInfo.this,deviceNo+" Selected",Toast.LENGTH_SHORT).show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
