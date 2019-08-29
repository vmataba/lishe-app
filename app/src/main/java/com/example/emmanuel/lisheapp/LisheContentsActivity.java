package com.example.emmanuel.lisheapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.lishe.Lishe;

import java.util.ArrayList;

public class LisheContentsActivity extends AppCompatActivity {

    private ListView contentsList;
    private ArrayAdapter<String> contentsAdapter;
    private String[] contents;
    private ArrayList<String> lisheContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lishe_contents);

        Lishe lishe = (Lishe) getIntent().getSerializableExtra("lishe");
        //Toast.makeText(getBaseContext(),lishe.getTitle(),Toast.LENGTH_LONG).show();
        lisheContents = (ArrayList<String>) lishe.getContents();

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        //Toast.makeText(getBaseContext(),getIntent().getIntExtra("code",0)+"",Toast.LENGTH_LONG).show();

        contents = getResources().getStringArray(R.array.mama_na_mtoto_1);

                switch (getIntent().getIntExtra("pos",0)){
                    case 1:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_1);
                        break;
                    case 2:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_2);
                        break;
                    case 3:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_3);
                        break;
                    case 4:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_4);
                        break;
                    case 5:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_5);
                        break;
                    case 6:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_6);
                        break;
                    case 7:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_7);
                        break;
                    case 8:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_8);
                        break;
                    case 9:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_9);
                        break;
                    case 10:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_10);
                        break;
                    case 11:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_11);
                        break;
                    case 12:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_12);
                        break;
                    case 13:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_13);
                        break;
                    case 14:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_14);
                        break;
                    case 15:
                        contents = getResources().getStringArray(R.array.mama_na_mtoto_15);
                        break;
                }

        contentsList = findViewById(R.id.contentsList);
        contentsAdapter = new ArrayAdapter<>(getBaseContext(),R.layout.lishe_contents_layout,lisheContents);
        contentsList.setAdapter(contentsAdapter);

        if (contentsAdapter.isEmpty()){
            setContentView(R.layout.nothing_to_display);
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
}
