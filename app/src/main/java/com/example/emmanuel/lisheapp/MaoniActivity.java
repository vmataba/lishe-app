package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.tools.Tool;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MaoniActivity extends AppCompatActivity {


    private Spinner maoniOptions;
    private String maoniOptionsArray[];
    private ArrayAdapter<String> adapter;
    private TextView maoniLabel;
    private EditText maoniToSubmit;
    private Button btnSendMaoni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maoni);

        getSupportActionBar().setTitle("Maoni");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize variables
        init();

        //Spinner Items population
        adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_dropdown_item_1line,maoniOptionsArray);
        maoniOptions.setAdapter(adapter);


        //Changing maoniLabel text according to Item selected from maoniOptions
        maoniOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    maoniLabel.setText(Html.fromHtml("Tafadhali tuandikie <b>Tatizo</b> lako hapa"));
                    maoniToSubmit.setHint("Andika Tatizo lako hapa ...");
                    btnSendMaoni.setText("Tuma Tatizo");
                } else if (position == 1){
                    maoniLabel.setText(Html.fromHtml("Tafadhali tuandikie mapendekezo ya <b>Maboresho</b> yako hapa"));
                    maoniToSubmit.setHint("Andika mapendekezo yako ya maboresho hapa ...");
                    btnSendMaoni.setText("Tuma Mapendekezo ya Mabaoresho");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Send Manoni
        btnSendMaoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (validate()){
                   User user = UserDetails.getUser(MaoniActivity.this);
                String url = Api.BASE_URL+"api/post-opinion&userid="+user.getId();
                url += "&cat="+maoniOptions.getSelectedItemPosition();
                url += "&opinion="+maoniToSubmit.getText().toString().trim().replace(" ","%20");
                url += "&vcode="+Api.VALIDATION_KEY;
                Sender sender = new Sender();
                sender.execute(url);
               }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

    private void init(){
        maoniOptions = findViewById(R.id.maoniOptions);
        maoniOptionsArray = new String[2];
        maoniOptionsArray[0] = "Tatizo";
        maoniOptionsArray[1] = "Maboresho";
        maoniLabel = findViewById(R.id.maoniLabel);
        maoniToSubmit = findViewById(R.id.maoniCollected);
        btnSendMaoni = findViewById(R.id.btnTumaMaoni);
    }

    //Validates Contents to be sent
    private boolean validate(){
        if (maoniToSubmit.getText().toString().trim().isEmpty()){
            maoniToSubmit.setError("Jaza sehemu hii!");
            return false;
        }

        return true;
    }

    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(MaoniActivity.this);
            dialog.setMessage("Maoni yanatumwa, Tafadhali subiri ...");
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
                        Toast.makeText(getBaseContext(),"Maoni yametumwa",Toast.LENGTH_LONG).show();
                        maoniToSubmit.setText("");
                        break;
                    case 0:
                        Toast.makeText(getBaseContext(),"Maoni hayajatumwa, jaaribu tena baadae!",Toast.LENGTH_LONG).show();
                        break;

                }

            } catch (JSONException e) {
                //Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(),"Network Error",Toast.LENGTH_LONG).show();

            }


            if (dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPostExecute(s);
        }
    }
}
