package com.example.extest.mainpage;

public class SingerItem
{
    private String title;
    private String good;
    private String star;
    private int resId;

    public SingerItem(String title, String good, String star, int resId){
        this.title = title;
        this.good = good;
        this.star = star;
        this.resId = resId;
    }

    public String getTitle(){ return title ;}
    public void setTitle(String title) { this.title = title;}
    public String getGood(){ return good;}
    public void setGood(String good) {this.good=good;}
    public int getResId(){return resId;}
    public String getstar() {return star;}
    public void setStar(String star) {this.star=star;}

    public void setResId(int resId) {
        this.resId = resId;
    }
}
