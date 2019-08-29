package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public class DashboardActivity extends AppCompatActivity {

    private CardView lisheMamaNaMtoto;
    private CardView lisheMagonjwaSugu;
    private CardView lisheKupunguzaUzito;
    private CardView lisheSwali;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_dashboard);
        getSupportActionBar().setElevation(0);

        prepSession();

        /**Instance Variables initialization**/
        lisheMamaNaMtoto = findViewById(R.id.lishe_mama_na_mtoto);
        lisheMagonjwaSugu = findViewById(R.id.lishe_magonjwa_sugu);
        lisheKupunguzaUzito = findViewById(R.id.lishe_kupunguza_uzito);
        lisheSwali = findViewById(R.id.lishe_uliza_swali);

        /**Intent Firing**/
        toLisheList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_maoni:
                Intent toMaoni =  new Intent(DashboardActivity.this,MaoniActivity.class);
                startActivity(toMaoni);
                break;
            case R.id.menu_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "LisheApp");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Pakua LisheApp hapa https://play.google.com/store/apps/details?id=com.lisheapp.lisheapp kwa taarifa mbalimbali muhimu zinazohusu lishe bora kwa afya ya mama na mtoto");
                startActivity(Intent.createChooser(sharingIntent, "Tuma kupitia ..."));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**Launches LisheListActivity**/
    private void toLisheList(){
        final Intent toLisheList = new Intent(DashboardActivity.this,LisheListActivity.class);
        //Mama na mtoto
        lisheMamaNaMtoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLisheList.putExtra("title", getResources().getString(R.string.lishe_1));
                toLisheList.putExtra("code",1);
                startActivity(toLisheList);
            }
        });
        //Magonjwa Sugu
        lisheMagonjwaSugu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLisheList.putExtra("title",getResources().getString(R.string.lishe_2));
                toLisheList.putExtra("code",2);
                startActivity(toLisheList);
            }
        });
        //Kupunguza Uzito
        lisheKupunguzaUzito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLisheList.putExtra("title",getResources().getString(R.string.lishe_3));
                toLisheList.putExtra("code",3);
                startActivity(toLisheList);
            }
        });
        //Kuuliza Swali
        lisheSwali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(DashboardActivity.this, SwaliActivity.class);
               startActivity(intent);
            }
        });

    }

    //Prepares Session for a UserDetails
    private void prepSession(){
        User user = UserDetails.getUser(DashboardActivity.this);
        String fullname = user.getFname()+" "+user.getLname();
        Toast.makeText(getBaseContext(),"Karibu, "+fullname,Toast.LENGTH_LONG).show();

    }

}
