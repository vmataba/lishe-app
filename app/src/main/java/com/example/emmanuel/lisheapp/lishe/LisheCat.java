package com.example.emmanuel.lisheapp.lishe;

import java.util.List;

public class LisheCat {

    private int id;
    private String name;
    private List<Lishe> lishe;

    public LisheCat(List<Lishe> lishe){
        this.lishe = lishe;
    }

    public LisheCat(int id, String name,List<Lishe> lishe){
        this(lishe);
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public List<Lishe> getLishe(){
        return this.lishe;
    }



}
