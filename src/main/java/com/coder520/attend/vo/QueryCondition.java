package com.coder520.attend.vo;

import com.coder520.common.page.PageQueryBean;

public class QueryCondition extends PageQueryBean{

    private long userId;

    private String startDate;

    private String endDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
