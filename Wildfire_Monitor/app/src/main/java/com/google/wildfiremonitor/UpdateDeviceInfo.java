package com.google.wildfiremonitor;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateDeviceInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference myRef;
    private int count;
    private Spinner spinner;
    private Button refreshBtn;

    private String selectedLocation;
    private String selectedDevice;

    private EditText temET,smokeET,fireET,statusET;
    private String a,b,c,d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_device_info);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        spinner = findViewById(R.id.spinnerLocation);
        spinner.setOnItemSelectedListener(this);

        refreshBtn = findViewById(R.id.refreshBtn);

        temET = findViewById(R.id.uTemET);
        smokeET = findViewById(R.id.uSmokeET);
        fireET = findViewById(R.id.uFireET);
        statusET = findViewById(R.id.uStatusET);

    }

    public void Refresh(View view) {
        refreshBtn.setEnabled(false);
        //updateData();
        CountChind();
        //SpinnerInfo();
    }

    public void UpdateDatabase(View view) {

        final String tem,smoke,fire,status;
        tem = temET.getText().toString();
        smoke = smokeET.getText().toString();
        fire = fireET.getText().toString();
        status = statusET.getText().toString();

        if(!tem.equals(a)){

            myRef.child("devices").child(selectedDevice).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("temperature").setValue(tem);
                    //dialog.dismiss();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.d("User", databaseError.getMessage());
                }
            });

        }
        if(!smoke.equals(b)){

            myRef.child("devices").child(selectedDevice).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("smoke").setValue(smoke);
                    //dialog.dismiss();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.d("User", databaseError.getMessage());
                }
            });

        }
        if(!fire.equals(c)){

            myRef.child("devices").child(selectedDevice).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("fire").setValue(fire);
                    //dialog.dismiss();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.d("User", databaseError.getMessage());
                }
            });

        }
        if(!status.equals(d)){

            myRef.child("devices").child(selectedDevice).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("status").setValue(status);
                    //dialog.dismiss();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Log.d("User", databaseError.getMessage());
                }
            });

        }

        Toast.makeText(this,"Update Success",Toast.LENGTH_SHORT).show();

    }

    public void SpinnerInfo(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showSnipperData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void CountChind() {
        //Toast.makeText(this,"Count",Toast.LENGTH_SHORT).show();

        this.myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.child("devices").getChildren()) {
                    //Log.e(snap.getKey(),snap.getChildrenCount() + "");
                    //Toast.makeText(UpdateDeviceInfo.this,snap.getKey(),Toast.LENGTH_SHORT).show();
                    count++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        SpinnerInfo();
    }

    public void showSnipperData(DataSnapshot dataSnapshot) {

        ArrayList<String> arrayList = new ArrayList<>();

        int i;
        for(i=1;i<count+1;i++){
            String d = "device"+i;
            for (DataSnapshot ds : dataSnapshot.getChildren()){
                SpinnerLocation spinnerLocation = new SpinnerLocation();

                spinnerLocation.setDevLocation(ds.child(d).getValue(DeviceInfo.class).getDevLocation());
                arrayList.add(spinnerLocation.getDevLocation());

            }
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        spinner.setAdapter(adapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedLocation = adapterView.getItemAtPosition(i).toString();
        SeeInfo();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void SeeInfo() {
        //Toast.makeText(UpdateDeviceInfo.this,"View",Toast.LENGTH_SHORT).show();

        Toast.makeText(UpdateDeviceInfo.this,selectedLocation,Toast.LENGTH_SHORT).show();
        if(selectedLocation.equals("Sundarban")){
            selectedDevice = "device1";
            //Toast.makeText(UpdateDeviceInfo.this,selectedDevice+selectedLocation,Toast.LENGTH_SHORT).show();

        }
        if(selectedLocation.equals("Bandarban")){
            selectedDevice = "device2";
            //Toast.makeText(UpdateDeviceInfo.this,selectedDevice+selectedLocation,Toast.LENGTH_SHORT).show();
        }
        if(selectedLocation.equals("Rangpur")){
            selectedDevice = "device3";
            //Toast.makeText(UpdateDeviceInfo.this,selectedDevice+selectedLocation,Toast.LENGTH_SHORT).show();
        }

        myRef.child("devices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                //Toast.makeText(UpdateDeviceInfo.this,snapshot.child(selectedDevice).child("devLocation").getValue().toString(),Toast.LENGTH_SHORT ).show();
                a = snapshot.child(selectedDevice).child("temperature").getValue().toString();
                b = snapshot.child(selectedDevice).child("smoke").getValue().toString();
                c = snapshot.child(selectedDevice).child("fire").getValue().toString();
                d = snapshot.child(selectedDevice).child("status").getValue().toString();

                //Toast.makeText(UpdateDeviceInfo.this,a+b+c+d,Toast.LENGTH_SHORT).show();
                //SetInfo();
                temET.setText(a);
                smokeET.setText(b);
                fireET.setText(c);
                statusET.setText(d);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
