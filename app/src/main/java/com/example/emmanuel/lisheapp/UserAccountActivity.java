package com.example.emmanuel.lisheapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.user.User;

public class UserAccountActivity extends AppCompatActivity {


    private TextView userFullName;
    private TextView userPhone;
    private TextView userEmail;
    private TextView regDate;
    private LinearLayout userRedLishe;
    private LinearLayout userQuestions;
    private Button btnUpdateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        getSupportActionBar().setTitle("Akaunti yangu");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //View UserAccount Details
        init();

        //To User Questions
        userQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toUserQuestions = new Intent(UserAccountActivity.this,MaswaliYanguActivity.class);
                toUserQuestions.putExtra("code",2);
                toUserQuestions.putExtra("title","Maswali yangu");
                startActivity(toUserQuestions);
            }
        });

        //TO User Red Lishe list
        userRedLishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReadLishe = new Intent(UserAccountActivity.this,ReadLisheActivity.class);
                startActivity(toReadLishe);
            }
        });

        btnUpdateAccount = findViewById(R.id.btnUpdateAccount);
        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext(),"Implementation is still on progress",Toast.LENGTH_LONG).show();
               //return;
                //Intent intent = new Intent(UserAccountActivity.this,UpdateAccountActivity.class);
                //startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Views UserAccount Related Information
    private void init(){

        userQuestions = findViewById(R.id.userQuestions);
        userRedLishe = findViewById(R.id.userReadLishe);

        User user = UserDetails.getUser(UserAccountActivity.this);
        //Full name
        userFullName = findViewById(R.id.userFullName);
        userFullName.setText(user.getFname()+" "+user.getLname());
        //Phone
        userPhone = findViewById(R.id.userPhone);
        userPhone.setText(user.getPhone());
        //Email
        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(user.getEmail());
        //Registration Date
        regDate = findViewById(R.id.regDate);
        regDate.setText(user.getRegDate());

    }
}
