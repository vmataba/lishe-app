package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.lishe.ExpandableListAdapter;
import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaswaliYanguActivity extends AppCompatActivity {

    private ExpandableListView expMaswali;
    private ExpandableListAdapter listAdapter;
    private List<String> maswali;
    private HashMap<String,List<String>> majibu;
    private HashMap<String,String> questionSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maswali_yangu);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        switch(getIntent().getIntExtra("code",0)){

            case 1://Maswali Bora
                String url1 = Api.BASE_URL+"api/best-questions&vcode="+Api.VALIDATION_KEY;
                Sender sender1 = new Sender();
                sender1.execute(url1);

                break;

            case 2://Maswali Yangu
                //Testing
                User user = UserDetails.getUser(MaswaliYanguActivity.this);
                String url = Api.BASE_URL+"api/user-questions&userid="+user.getId()+"&vcode="+Api.VALIDATION_KEY;
                Sender sender = new Sender();
                sender.execute(url);

                break;
        }




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

    private void prepMaswaliBora(){
        prepMaswaliYangu();
    }

    private void prepMaswaliYangu(){

        HashMap<String,ArrayList<String>> temp_answers = new HashMap<>();
        majibu = new HashMap<>();
        maswali = new ArrayList<>();
        for (String question: questionSet.keySet()){
            maswali.add(question);
            ArrayList<String> temp_ans = new ArrayList<>();
            temp_ans.add(questionSet.get(question));
            majibu.put(question,temp_ans);
        }
    }

    //For Querying from Remote Server
    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(MaswaliYanguActivity.this);
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

            //Toast.makeText(MaswaliYanguActivity.this,s,Toast.LENGTH_LONG).show();

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
                        questionSet = new HashMap<>();
                        //Toast.makeText(getBaseContext(),"Maoni yametumwa",Toast.LENGTH_LONG).show();
                        JSONObject result = new JSONObject(s);
                        JSONArray questions = result.getJSONArray("questions");
                        for (int i=0; i<questions.length(); i++){
                            String question = questions.getJSONObject(i).getString("content");
                            String answer = questions.getJSONObject(i).getString("answer");
                            String ans = answer.equals("null")? "Halijajibiwa":answer;
                            questionSet.put(question,ans);
                        }

                        //Temporal stay
                        expMaswali = findViewById(R.id.maswaliYangu);
                        switch(getIntent().getIntExtra("code",0)){

                            case 1://Maswali Bora
                                prepMaswaliBora();
                                break;

                            case 2://Maswali Yangu
                                prepMaswaliYangu();
                                break;
                        }

                        listAdapter = new ExpandableListAdapter(getBaseContext(),maswali,majibu);
                        expMaswali.setAdapter(listAdapter);
                        if (listAdapter.isEmpty()){
                            setContentView(R.layout.nothing_to_display);
                        }

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
