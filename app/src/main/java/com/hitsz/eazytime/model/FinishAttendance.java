package com.hitsz.eazytime.model;

import android.widget.FrameLayout;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class FinishAttendance extends LitePalSupport {
    private int id;
    private String title;
    private Date time;

    public FinishAttendance(String title,Date time){
        this.title=title;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
