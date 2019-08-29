package com.example.emmanuel.lisheapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.R;
import com.example.emmanuel.lisheapp.lishe.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MsaadaActivity extends AppCompatActivity {


    private static final int CALL_PHONE_CODE = 1;

    private ExpandableListView expMsaadaTitle;
    private ExpandableListAdapter listAdapter;
    private List<String> msaadaTitles;
    private HashMap<String,List<String>> msaada;
    private LinearLayout makeCall;
    private LinearLayout sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msaada);

        getSupportActionBar().setTitle("Msaada");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       expMsaadaTitle = findViewById(R.id.msaadaList);
       msaadaTitles = new ArrayList<>();
       for (String msaadaTitle:getResources().getStringArray(R.array.msaadaList)){
           msaadaTitles.add(msaadaTitle);
       }
       List<String> answers = new ArrayList<>();
       for (String answ:getResources().getStringArray(R.array.mada_za_msaada)){
           answers.add(answ);
       }
       msaada = new HashMap<>();
       //List<String> answer = new ArrayList<>();
       int count = 0;
       for (String title: msaadaTitles){
          List<String> answer = new ArrayList<>();
          answer.add(answers.get(count++));
           msaada.put(title,answer);

       }
       listAdapter = new ExpandableListAdapter(getBaseContext(),msaadaTitles,msaada);
       expMsaadaTitle.setAdapter(listAdapter);


       //Make Call
        makeCall = findViewById(R.id.phoneCall);
        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });

        //Send Email
        sendEmail = findViewById(R.id.sendEmail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"jmwakajeba@gmail.com "});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SWALI/MAONI KUHUSU LISHE");
                startActivity(Intent.createChooser(emailIntent, "Tuma email kupitia..."));

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case CALL_PHONE_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    makeCall();
                return;
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Makes Phone Call
    private void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0716810817"));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //Request For CALL_PHONE Permission
            ActivityCompat.requestPermissions(MsaadaActivity.this,new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_CODE);
            return;
        }
        startActivity(callIntent);
    }
}
