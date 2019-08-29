package com.example.emmanuel.lisheapp.lishe;


import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.emmanuel.lisheapp.LisheContentsActivity;
import com.example.emmanuel.lisheapp.R;
import com.example.emmanuel.lisheapp.SwaliActivity;
import com.example.emmanuel.lisheapp.local.UserDetails;
import com.example.emmanuel.lisheapp.tools.Api;
import com.example.emmanuel.lisheapp.user.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by victor on 10/8/2017.
 */

public class LisheOptionAdapter extends RecyclerView.Adapter<LisheOptionAdapter.ViewHolder>{


  private ArrayList<String> titles;
  private ArrayList<Lishe> lishes;
  private Context context;
  private int code;//1: Mama na Mtoto, 2: Magonjwa sugu 3: Uzito Mkubwa


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView titleView;
        public TextView numView;
        public CardView optionCard;

        public ViewHolder(View view) {
            super(view);
            this.view = view;

            titleView = view.findViewById(R.id.item_id);
            numView = view.findViewById(R.id.numView);
            optionCard = view.findViewById(R.id.optionCard);


        }
    }

    public LisheOptionAdapter(Context context,ArrayList<Lishe> lishes,int code) {
        this.context = context;
        this.lishes = lishes;
        this.code = code;
    }

    //Getter Method for Context
    public Context getContext () {
        return this.context;
    }

    @Override
    public LisheOptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View lisheView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.lishe_option, parent, false);

        ViewHolder vh = new ViewHolder(lisheView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Lishe lishe = lishes.get(position);
        holder.titleView.setText(lishe.getTitle());
        holder.numView.setText("IMESOMWA "+lishe.getNumView());

        //Item Click Listener
        holder.optionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Increment numView
                User user = UserDetails.getUser(context);
                String url = Api.BASE_URL+"api/read-lishe&userid="+user.getId()+"&lishe="+lishe.getId();
                url += "&vcode="+Api.VALIDATION_KEY;
                Sender sender = new Sender();
                sender.execute(url);
                //View Contents
                Intent toContents = new Intent(v.getContext(), LisheContentsActivity.class);
                toContents.putExtra("title",lishe.getTitle());
                toContents.getIntExtra("code",code);
                toContents.putExtra("pos",position+1);
                toContents.putExtra("lishe",lishe);
                v.getContext().startActivity(toContents);
            }
        });

    }


    @Override
    public int getItemCount() {
        return lishes.size();
    }


    private class Sender extends AsyncTask<String,String,String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(context);
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
                Toast.makeText(context,s,Toast.LENGTH_LONG).show();
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
                        //Toast.makeText(context,"Swali limetumwa",Toast.LENGTH_LONG).show();

                        break;
                    case 0:
                        //Toast.makeText(context,"Swali halijatumwa, jaaribu tena baadae!",Toast.LENGTH_LONG).show();
                        break;

                }

            } catch (JSONException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }


            if (dialog.isShowing()){
                dialog.dismiss();
            }
            super.onPostExecute(s);
        }
    }

}
