package com.example.emmanuel.lisheapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.App;
import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.tools.Tool;
import com.example.emmanuel.lisheapp.user.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    private TextView barTitle;
    private RelativeLayout.LayoutParams layoutParams;

    private EditText userFname;
    private EditText userLname;
    private EditText userPhone;
    private EditText userEmail;
    private Spinner userRegion;
    private Spinner userAge;
    private RadioButton genderMale;
    private RadioButton genderFemale;
    private RadioButton genderOther;
    private Button btnRegister;
    private ArrayAdapter<String> ageAdapter;
    private ArrayList<String> ages;
    private ArrayAdapter<String> regionAdapater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (UserDetails.getUser(getBaseContext())!=null){
            Intent intent = new Intent(RegistrationActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        //Set Toolbar title
        barTitle = new TextView(getApplicationContext());
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        barTitle.setLayoutParams(layoutParams);
        barTitle.setText(Html.fromHtml("<b>Karibu LisheApp</b>"));
        barTitle.setTextColor(Color.WHITE);
        barTitle.setGravity(Gravity.CENTER);
        barTitle.setTextSize(20);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(barTitle);


        //Initialize Variables
        init();

        //Region Spinner
        regionAdapater = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.regions));
        userRegion.setAdapter(regionAdapater);

        //Age Spinner
        ages = new ArrayList<>();
        ages.add("Chagua Umri wako ...");
        for (int i=18; i<=45; i++)
            ages.add(i+"");
        ageAdapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_list_item_1,ages);
        userAge.setAdapter(ageAdapter);

        //Registration
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()){
                    return;
                }
                register();
                //startActivity(new Intent(RegistrationActivity.this,DashboardActivity.class));
            }
        });
    }

    //For Variables Initialization
    private void init(){
        userFname = findViewById(R.id.userFName);
        userLname = findViewById(R.id.userLName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userRegion = findViewById(R.id.userRegion);
        userAge = findViewById(R.id.userAge);
        genderMale = findViewById(R.id.genderMale);
        genderFemale = findViewById(R.id.genderFemale);
        genderOther = findViewById(R.id.genderOther);
        btnRegister = findViewById(R.id.btn_register);
    }

    //For Phone number Digits Separation
    private void separatePhone(){
        //userPhone.setOn
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
        //Age
        if (userAge.getSelectedItemPosition() == 0){
            res = false;
            Toast.makeText(getBaseContext(),"Chagua Umri",Toast.LENGTH_LONG).show();
        }
        //Sex
        if (!genderMale.isChecked() && !genderFemale.isChecked() && !genderOther.isChecked()){
            res = false;
            Toast.makeText(getBaseContext(),"Chagua Jinsia",Toast.LENGTH_LONG).show();
        }

        return res;
    }

    //For UserDetails Sex
    private String getSex(){
        String sex = "NYINGINE";
        if (genderMale.isChecked()){
            sex = "ME";
        } else if (genderFemale.isChecked()){
            sex = "KE";
        }
        return sex;
    }

    //For Registering new UserDetails
    private void register(){
        Registration registration = new Registration();
        String url = Api.BASE_URL+"api/register-user";

        url += "&fname="+userFname.getText().toString().trim();
        url += "&lname="+userLname.getText().toString().trim();
        url += "&region="+getResources().getStringArray(R.array.regions)[userRegion.getSelectedItemPosition()].trim().replace(" ","%20");
        url += "&age="+ages.get(userAge.getSelectedItemPosition()).trim();
        url += "&sex="+getSex().trim();
        url += "&email="+userEmail.getText().toString().trim();
        url += "&phone=+255"+userPhone.getText().toString().trim();
        url += "&vcode="+Api.VALIDATION_KEY.trim();

       // url = Api.BASE_URL+"api/text&name=Taba";

        registration.execute(url);

    }

    //For Posting Registration Details
    private class Registration extends AsyncTask<String,String,String>{

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(RegistrationActivity.this);
            dialog.setMessage("Usajili unaendelea, Tafadhali subiri ...");
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
                        Toast.makeText(getBaseContext(),"Usajili umekamilika",Toast.LENGTH_LONG).show();

                        //Create UserDetails object
                        User user = new User(jsonObject.getInt("userid"));
                        user.setFname(jsonObject.getString("fname"));
                        user.setLname(jsonObject.getString("lname"));
                        user.setRegion(jsonObject.getString("region"));
                        user.setAge(jsonObject.getInt("age"));
                        user.setSex(jsonObject.getString("sex"));
                        user.setEmail(jsonObject.getString("email"));
                        user.setPhone(jsonObject.getString("phone"));
                        //TimeStamp
                        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                        user.setRegDate(Tool.formatDate(timeStamp));


                        //Store User Details
                        UserDetails details = new UserDetails(RegistrationActivity.this,user);

                        //Call DashBoard Activity
                        Intent toDashboard = new Intent(RegistrationActivity.this,DashboardActivity.class);
                        toDashboard.putExtra("user",user);
                        startActivity(toDashboard);
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
