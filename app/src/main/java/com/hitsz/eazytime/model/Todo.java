package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import android.icu.util.Calendar;
import java.util.Date;

public class Todo extends LitePalSupport {
    private String title;
    private Date startTime;
    private Date endTime;
    private int id;

    public Todo(){
        title=null;
        startTime=new Date();
        endTime=new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public void setStartTime(Calendar cal) {
        this.startTime = cal.getTime();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }
}
