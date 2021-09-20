package com.example.extest.categoryList;

public class CategoryItem
{

    private String categoryname;
    private int resId;

    public CategoryItem(String categoryname, int resId){

        this.categoryname = categoryname;
        this.resId = resId;
    }


    public int getResId(){return resId;}
    public String getCategoryName() {return categoryname;}
    public void setCategoryName(String categoryname) {this.categoryname=categoryname;}

    public void setResId(int resId) {
        this.resId = resId;
    }
}
