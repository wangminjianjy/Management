package com.example.management.bean;

import java.util.List;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 11:43
 * @Description : 描述
 */
public class MainBean {

    private int CumulativeCount;
    private int TodayCount;
    private int ThisMonthCount;
    private List<CategoryBean> CategoryStatistics;

    public int getCumulativeCount() {
        return CumulativeCount;
    }

    public void setCumulativeCount(int cumulativeCount) {
        CumulativeCount = cumulativeCount;
    }

    public int getTodayCount() {
        return TodayCount;
    }

    public void setTodayCount(int todayCount) {
        TodayCount = todayCount;
    }

    public int getThisMonthCount() {
        return ThisMonthCount;
    }

    public void setThisMonthCount(int thisMonthCount) {
        ThisMonthCount = thisMonthCount;
    }

    public List<CategoryBean> getCategoryStatistics() {
        return CategoryStatistics;
    }

    public void setCategoryStatistics(List<CategoryBean> categoryStatistics) {
        CategoryStatistics = categoryStatistics;
    }
}
