package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SwaliActivity extends AppCompatActivity {

    private Spinner swaliOptions;
    private String swaliOptionsArray[];
    private ArrayAdapter<String> adapter;
    private TextView swaliLabel;
    private EditText swaliToSubmit;
    private Button btnSendSwali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swali);

        getSupportActionBar().setTitle("Swali");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       init();

        //Spinner Items population
        adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_dropdown_item_1line,swaliOptionsArray);
        swaliOptions.setAdapter(adapter);

        swaliOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    swaliLabel.setText("");
                    swaliToSubmit.setHint("");
                    swaliToSubmit.setEnabled(false);
                    btnSendSwali.setEnabled(false);
                } else{
                    swaliLabel.setText(Html.fromHtml("Tafadhali, tuulize swali kuhusu, <b>"+swaliOptionsArray[position]+"</b>"));
                    swaliToSubmit.setEnabled(true);
                    btnSendSwali.setEnabled(true);
                    swaliToSubmit.setHint("Andika swali kuhusu "+swaliOptionsArray[position]+" hapa ...");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Send UserDetails Question
        btnSendSwali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()){
                    User user = UserDetails.getUser(SwaliActivity.this);
                    String url = Api.BASE_URL+"api/ask-question&userid="+user.getId();
                    url += "&lishe="+swaliOptions.getSelectedItemPosition();
                    url += "&content="+swaliToSubmit.getText().toString().trim().replace(" ","%20");
                    url += "&vcode="+Api.VALIDATION_KEY;
                    SwaliActivity.Sender sender = new SwaliActivity.Sender();
                    sender.execute(url);
                }

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

    //Initializes Variables
    private void init(){
        swaliOptions = findViewById(R.id.swaliOptions);
        swaliOptionsArray = new String[4];
        swaliOptionsArray[0] = "CHAGUA AINA YA LISHE";
        swaliOptionsArray[1] = "LISHE YA MAMA NA MTOTO";
        swaliOptionsArray[2] = "LISHE YA MAGONJWA SUGU YASIYOAMBUKIZWA";
        swaliOptionsArray[3] = "LISHE YA KUPUNGUZA UZITO MKUBWA";

        swaliLabel = findViewById(R.id.swaliLabel);
        swaliToSubmit = findViewById(R.id.swaliCollected);
        btnSendSwali = findViewById(R.id.btnTumaSwali);
    }


    private boolean validate(){
        if (swaliToSubmit.getText().toString().isEmpty()){
            swaliToSubmit.setError("Jaza sehemu hii!");
            return false;
        }
        return true;
    }


    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(SwaliActivity.this);
            dialog.setMessage("Swali linatumwa, Tafadhali subiri ...");
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
                        Toast.makeText(getBaseContext(),"Swali limetumwa",Toast.LENGTH_LONG).show();
                        swaliToSubmit.setText("");
                        swaliOptions.setSelection(0);
                        break;
                    case 0:
                        Toast.makeText(getBaseContext(),"Swali halijatumwa, jaaribu tena baadae!",Toast.LENGTH_LONG).show();
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
