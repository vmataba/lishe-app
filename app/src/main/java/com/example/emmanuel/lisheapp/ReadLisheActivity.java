package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReadLisheActivity extends AppCompatActivity {

    private ListView readLisheList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> readLisheArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_lishe);

        getSupportActionBar().setTitle("Lishe nilizosoma");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(0);

        //testing
        User user = UserDetails.getUser(getBaseContext());
        String url = Api.BASE_URL+"api/user-lishe&userid="+user.getId()+"&vcode="+Api.VALIDATION_KEY;
        Sender sender = new Sender();
        sender.execute(url);


        init();
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
        readLisheList = findViewById(R.id.readLisheList);
        readLisheArray = new ArrayList<>();
    }

    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(ReadLisheActivity.this);
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
                        //Toast.makeText(getBaseContext(),"Maoni yametumwa",Toast.LENGTH_LONG).show();
                        JSONObject lisheObject = jsonObject.getJSONObject("lishe");
                        for (int i=1; i<=lisheObject.length(); i++){
                            String title = lisheObject.getString(i+"");
                            readLisheArray.add(title);
                        }
                        adapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,readLisheArray);
                        readLisheList.setAdapter(adapter);
                        break;
                    case 0:
                        //Toast.makeText(getBaseContext(),"Maoni hayajatumwa, jaaribu tena baadae!",Toast.LENGTH_LONG).show();
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
