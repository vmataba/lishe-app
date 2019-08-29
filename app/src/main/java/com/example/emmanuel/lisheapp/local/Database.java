package com.example.emmanuel.lisheapp.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.emmanuel.lisheapp.user.User;

import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    //Questions and Answers pair
    private HashMap<String,String> questions;
    //Context
    private Context context;
    //Database name
    private static String DB_NAME = "local_db.db";
    //Database Version
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Table question
        String question;
        question = "CREATE TABLE QUESTION (question_id INT PRIMARY KEY AUTO_INCREMENT,";
        question += "userid INT NOT NULL, question TEXT NOT NULL,";
        question += "answer TEXT NULL)";
        db.execSQL(question);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS question");
        onCreate(db);

    }

    //Returns Questions
    public HashMap<String,String> getQuestions(){
        HashMap<String,String> questions = new HashMap<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM question",null);
        if (cursor.moveToFirst()){
            do {
                questions.put(cursor.getString(cursor.getColumnIndexOrThrow("question")),cursor.getString(cursor.getColumnIndexOrThrow("answer")));

            } while (cursor.moveToNext());
        } cursor.close();

        return questions;
    }

    //Populates Questions
    public long setQuestions(HashMap<String,String> questions){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        User user = UserDetails.getUser(context);
        for (String question: questions.keySet()){
            values.put("userid",user.getId());
            values.put("question",question);
            values.put("answer",questions.get(question));
        }
        long res = database.insert("question",null,values);
        return res;
    }

    //Clears table question
    public void clearTableQuestion () {
        String query = "DELETE FROM question";
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(query);
    }


}
