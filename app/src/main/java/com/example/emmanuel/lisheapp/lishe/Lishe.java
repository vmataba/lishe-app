package com.example.emmanuel.lisheapp.lishe;

import java.io.Serializable;
import java.util.List;

public class Lishe implements Serializable {

    private int id;
    private String title;
    private int numView;
    private LisheCat cat;
    private List<String> contents;

    public Lishe(int id,String title, int numView){
        this.id = id;
        this.title = title;
        this.numView = numView;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setNumView(int numView){
        this.numView = numView;
    }

    public void setCat(LisheCat cat){
        this.cat = cat;
    }

    public void setContents(List<String> contents){
        this.contents = contents;
    }

    public String getTitle(){
        return this.title;
    }

    public int getNumView(){
        return this.numView;
    }

    public LisheCat getCat(){
        return this.cat;
    }

    public int getId(){
        return this.id;
    }

    public List<String> getContents(){
        return this.contents;
    }
}
