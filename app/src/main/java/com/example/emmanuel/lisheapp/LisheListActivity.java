package com.example.emmanuel.lisheapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.emmanuel.lisheapp.lishe.Lishe;
import com.example.emmanuel.lisheapp.lishe.LisheOptionAdapter;
import com.example.emmanuel.lisheapp.tools.Api;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class LisheListActivity extends AppCompatActivity {

    private TextView lisheTitle;
    private RelativeLayout.LayoutParams layoutParams;
    private ImageView btnHelp;

    private RecyclerView optionList;
    private LisheOptionAdapter lisheAdapter;
    private ArrayList<String> optionTitle;
    private ArrayList<Lishe> optionLishe;
    //Stores Lishe and Contents List
    private HashMap<Lishe,List<String>> lisheHashMap;
    //Store Lishe Items Temporarily
    private ArrayList<Lishe> tempLisheList;
    private ArrayList<String> tempContentList;

    @SuppressLint({"WrongConstant", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishe_list);

        TextView titleView = findViewById(R.id.title);
        titleView.setText(getIntent().getStringExtra("title"));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.logo);

        getSupportActionBar().setElevation(0);

        //Init
        lisheHashMap = new HashMap<>();
        tempLisheList = new ArrayList<>();
        tempContentList = new ArrayList<>();


        //Testing
        int cat = getIntent().getIntExtra("code",0);
        String url = Api.BASE_URL+"api/get-lishe&cat="+cat+"&vcode="+Api.VALIDATION_KEY;
        Sender sender = new Sender();
        sender.execute(url);


        //Help View
        btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMsaada = new Intent(LisheListActivity.this, MsaadaActivity.class);
                startActivity(toMsaada);
            }
        });

        //Display Options List
        optionTitle = new ArrayList<>();

        optionLishe = new ArrayList<>();

        optionLishe = tempLisheList;

       /* switch (getIntent().getIntExtra("code",0)){
            case 1:
                String[] titlesMamaNaMoto = getResources().getStringArray(R.array.lisheMamaNaMtoto);
                for (String title: titlesMamaNaMoto){
                    Lishe lishe = new Lishe(0,title,10);//Testing
                    optionLishe.add(lishe);
                }
                lisheAdapter = new LisheOptionAdapter(this.getBaseContext(),optionLishe,1);
                break;
            case 2:
                String[] titlesMagonjwaSugu = getResources().getStringArray(R.array.magonjwaSugu);
                for (String title:titlesMagonjwaSugu){
                    Lishe lishe = new Lishe(0,title,20);//Testing
                    optionLishe.add(lishe);
                }
                lisheAdapter = new LisheOptionAdapter(this.getBaseContext(),optionLishe,2);
                break;
            case 3:

                String[] titlesUzitoMkubwa = getResources().getStringArray(R.array.lisheKupunguzaUzito);
                for (String title: titlesUzitoMkubwa){
                    Lishe lishe = new Lishe(0,title,30);//testing
                    optionLishe.add(lishe);
                }
                lisheAdapter = new LisheOptionAdapter(this.getBaseContext(),optionLishe,3);
                break;
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lishelist,menu);

        // Associate searchable configuration with the SearchView
        /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_maoni:
                Intent toMaoni = new Intent(LisheListActivity.this, MaoniActivity.class);
                startActivity(toMaoni);
                break;
            case R.id.menu_akaunti_yangu:
                Intent toMyAccount = new Intent(LisheListActivity.this,UserAccountActivity.class);
                startActivity(toMyAccount);
                break;
            case R.id.menu_maswali_bora:
                Intent toMaswaliYangu = new Intent(LisheListActivity.this,MaswaliYanguActivity.class);
                toMaswaliYangu.putExtra("title","Maswali Bora");
                toMaswaliYangu.putExtra("code",1);
                startActivity(toMaswaliYangu);
                break;
            case R.id.menu_maswali_yangu:
                Intent toMaswaliBora = new Intent(LisheListActivity.this,MaswaliYanguActivity.class);
                toMaswaliBora.putExtra("title","Maswali Yangu");
                toMaswaliBora.putExtra("code",2);
                startActivity(toMaswaliBora);
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


    //For Online Loading of Lishe Entities
    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(LisheListActivity.this);
            dialog.setMessage("Tafadhali subiri ...");
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            Api api = new Api();
            return api.get(5000,5000,strings[0],"");
        }

        @Override
        protected void onPostExecute(String s) {

            //Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();


            if (s.equals(Api.FAILED_PROCESS_MESSAGE)){
                Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
                return;
            }
            //Display Success or Failure Message
            try {
                JSONObject jsonObject = new JSONObject(s);
                int code = jsonObject.getInt("code");
                switch (code){
                    case 1:
                        JSONArray lisheArray = jsonObject.getJSONArray("lishe");

                        for (int i=0; i<lisheArray.length(); i++){
                            JSONObject lisheObject = lisheArray.getJSONObject(i);
                            int id = lisheObject.getInt("lishe_id");
                            String title = lisheObject.getString("title");
                            int numViews = lisheObject.getInt("num_views");
                            Lishe lishe = new Lishe(id,title,numViews);
                            tempLisheList.add(lishe);
                        }
                        //Lishe Contents
                       JSONObject contentObject =  jsonObject.getJSONObject("contents");
                        for (Lishe lishe: tempLisheList){
                            JSONArray contentArray = contentObject.getJSONArray(lishe.getId()+"");
                            for(int i=0; i<contentArray.length(); i++){
                                JSONObject object = contentArray.getJSONObject(i);
                                String content = object.getString("content");
                                tempContentList.add(content);
                            }
                            lishe.setContents(tempContentList);
                            tempContentList = new ArrayList<>();

                        }

                        optionList = findViewById(R.id.lisheListView);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LisheListActivity.this);
                        optionList.addItemDecoration(new DividerItemDecoration(LisheListActivity.this,LinearLayoutManager.VERTICAL));
                        lisheAdapter = new LisheOptionAdapter(LisheListActivity.this,optionLishe,getIntent().getIntExtra("code",0));
                        optionList.setLayoutManager(layoutManager);
                        optionList.setAdapter(lisheAdapter);

                        break;
                    case 0:
                        Toast.makeText(getBaseContext(),"Maoni hayajatumwa, jaaribu tena baadae!",Toast.LENGTH_LONG).show();
                        break;

                }

            } catch (JSONException e) {
                //Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(),"Network Error",Toast.LENGTH_LONG).show();
                setContentView(R.layout.network_error);
            }


            if (dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPostExecute(s);
        }
    }
}
