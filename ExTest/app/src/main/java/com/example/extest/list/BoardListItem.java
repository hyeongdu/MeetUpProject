package com.example.extest.list;

public class BoardListItem
{
    private String title;
    private String startdate;
    private String enddate;
    private int tfile;
    private String tid;

    private String categoryName;

    public BoardListItem(String title, String startdate, String enddate, int tfile,String tid){
        this.title = title;
        this.startdate = startdate;
        this.enddate = enddate;
        this.tfile = tfile;
        this.tid = tid;
    }

    public BoardListItem(String categoryName){
        this.categoryName = categoryName;

    }

    public String getTitle(){ return title ;}
    public void setTitle(String title) { this.title = title;}
    public String getStartdate(){ return startdate;}
    public void setStartdate(String startdate) {this.startdate=startdate;}
    public int getTfile(){return tfile;}
    public String getEnddate() {return enddate;}
    public void setEnddate(String star) {this.enddate=enddate;}

    public void setTfile(int tfile) { this.tfile = tfile; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName;}
    public String getCategoryName(){return categoryName;}
    public void setTid(String tid) { this.tid = tid;}
    public String getTid(){return tid;}
}
