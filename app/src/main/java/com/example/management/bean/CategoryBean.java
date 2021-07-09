package com.example.management.bean;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 11:44
 * @Description : 描述
 */
public class CategoryBean {

    private String CategoryName;
    private int CategoryCount;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryCount() {
        return CategoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        CategoryCount = categoryCount;
    }
}
