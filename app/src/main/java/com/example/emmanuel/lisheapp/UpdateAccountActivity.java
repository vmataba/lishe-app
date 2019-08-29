package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.tools.Tool;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText userFname;
    private EditText userLname;
    private EditText userPhone;
    private EditText userEmail;
    private Spinner userRegion;
    private Spinner userAge;
    private RadioButton genderMale;
    private RadioButton genderFemale;
    private RadioButton genderOther;
    private Button btnUpdate;
    private ArrayAdapter<String> ageAdapter;
    private ArrayList<String> ages;
    private ArrayAdapter<String> regionAdapater;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        getSupportActionBar().setTitle("Boresha Akaunti");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    update();
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

    private void init(){

        user = UserDetails.getUser(getBaseContext());
        userFname = findViewById(R.id.userFName);
        userLname = findViewById(R.id.userLName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userRegion = findViewById(R.id.userRegion);
        userAge = findViewById(R.id.userAge);
        genderMale = findViewById(R.id.genderMale);
        genderFemale = findViewById(R.id.genderFemale);
        genderOther = findViewById(R.id.genderOther);
        btnUpdate = findViewById(R.id.btn_update);

        userFname.setText(user.getFname());
        userLname.setText(user.getLname());
        userPhone.setText(user.getPhone().replace("+255","").replace(" ",""));
        userEmail.setText(user.getEmail());

        regionAdapater = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.regions));
        userRegion.setAdapter(regionAdapater);
        userRegion.setSelection(Arrays.asList(getResources().getStringArray(R.array.regions)).indexOf(user.getRegion()));

        //Age Spinner
        ages = new ArrayList<>();
        ages.add("Chagua Umri wako ...");
        for (int i=18; i<=45; i++)
            ages.add(i+"");
        ageAdapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,ages);
        userAge.setAdapter(ageAdapter);
        userAge.setSelection(ages.indexOf(user.getAge()+""));

    }

    //For Form Validation
    private boolean validate(){
        boolean res = true;
        String error_empty = "Jaza Sehemu hii!";
        String error_short = "Andika Jina Sahihi!";
        String wrong_no = "Andika namba Sahihi!";
        String wrong_email = "Andika namba Sahihi!";
        //First name
        if (userFname.getText().toString().trim().isEmpty()){
            res = false;
            userFname.setError(error_empty);
        } else if (userFname.getText().toString().trim().length()<3){
            res = false;
            userFname.setError(error_short);
        } else if (userFname.getText().toString().trim().contains(" ")){
            res = false;
            userFname.setError(error_short);
        }
        //Last name
        if (userLname.getText().toString().trim().isEmpty()){
            res = false;
            userLname.setError(error_empty);
        } else if (userLname.getText().toString().trim().length()<3){
            res = false;
            userLname.setError(error_short);
        } else if (userLname.getText().toString().trim().contains(" ")){
            res = false;
            userLname.setError(error_short);
        }
        //Phone
        if (userPhone.getText().toString().trim().isEmpty()){
            res = false;
            userPhone.setError(error_empty);
        } else if (userPhone.getText().toString().trim().length()!=9){
            res = false;
            userPhone.setError(wrong_no);
        } else if (Integer.parseInt(userPhone.getText().toString().substring(0,1))<6){
            res = false;
            userPhone.setError(wrong_no);
        }
        //Email
        if (userEmail.getText().toString().trim().isEmpty()){
            res = false;
            userEmail.setError(error_empty);
        } else if (!userEmail.getText().toString().contains("@")){
            res = false;
            userEmail.setError(wrong_email);
        } else if (!userEmail.getText().toString().contains(".")){
            res = false;
            userEmail.setError(wrong_email);
        }
        //Region
        if (userRegion.getSelectedItemPosition() == 0){
            res = false;
            Toast.makeText(getBaseContext(),"Chagua Mkoa",Toast.LENGTH_LONG).show();
        }

        return res;
    }

    private void update(){

        Sender sender = new Sender();
        String url = Api.BASE_URL+"api/update-account";
        url += "&userid="+user.getId();
        url += "&region="+user.getRegion().replace(" ","%20");
        url += "&age="+user.getAge();
        url += "&phone="+user.getPhone().replace(" ","%20");
        url += "&email="+user.getEmail();

        url += "&vcode="+Api.VALIDATION_KEY.trim();

        // url = Api.BASE_URL+"api/text&name=Taba";

        sender.execute(url);


    }

    //For Posting Registration Details
    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(UpdateAccountActivity.this);
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
                JSONObject json = new JSONObject(s);

                int code = json.getInt("code");
                JSONObject jsonObject = json.getJSONObject("user");
                switch (code){
                    case 1:
                        Toast.makeText(getBaseContext(),"Taarifa zimeboreshwa!",Toast.LENGTH_LONG).show();

                        //Create UserDetails object
                        user.setId(jsonObject.getInt("userid"));
                        user.setFname(jsonObject.getString("fname"));
                        user.setLname(jsonObject.getString("lname"));
                        user.setRegion(jsonObject.getString("region"));
                        user.setAge(jsonObject.getInt("age"));
                        user.setSex(jsonObject.getString("sex"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setPhone(jsonObject.getString("phone"));

                        //Store User Details
                        UserDetails details = new UserDetails();
                        details.editUser(getBaseContext(),user);

                        //Call UserAccount Activity
                        Intent toUserAccount = new Intent(UpdateAccountActivity.this,UserAccountActivity.class);
                        startActivity(toUserAccount);
                        finish();

                        break;
                    case 0:
                        JSONObject error = jsonObject.getJSONObject("error");
                        JSONArray phone_error = error.getJSONArray("phone");
                        if (phone_error!=null){
                            userPhone.setError("Namba inatumika");
                            Toast.makeText(getBaseContext(),"Namba inatumika",Toast.LENGTH_LONG).show();
                        }

                        JSONArray email_error = error.getJSONArray("email");
                        if (email_error!=null) {
                            userEmail.setError("Email inatumika");
                            Toast.makeText(getBaseContext(),"Email inatumika",Toast.LENGTH_LONG).show();
                        }
                }


            } catch (JSONException e) {
                Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                //Toast.makeText(getBaseContext(),"Network Error",Toast.LENGTH_LONG).show();
            }


            if (dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPostExecute(s);
        }
    }
}
