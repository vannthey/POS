package com.example.pos.category;

public class CategoryModel {
    String iccategory;
    String no;

    public String getIccategory() {
        return iccategory;
    }

    public String getNo() {
        return no;
    }

    public CategoryModel(String iccategory, String no) {
        this.iccategory = iccategory;
        this.no = no;
    }
}
