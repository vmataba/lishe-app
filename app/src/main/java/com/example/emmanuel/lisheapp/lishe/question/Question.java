package com.example.emmanuel.lisheapp.lishe.question;

import com.example.emmanuel.lisheapp.lishe.Lishe;
import com.example.emmanuel.lisheapp.user.User;

public class Question {

    private int id;
    private Lishe lishe;
    private User user;

    public Question (User user){
        this.user = user;
    }

    public Question(User user,Lishe lishe){
        this(user);
        this.lishe = lishe;
    }

    public Question(User user,int id, Lishe lishe){
        this(user,lishe);
        this.id = id;
    }

    public Lishe getLishe(){
        return this.lishe;
    }

    public int getId(){
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

}
