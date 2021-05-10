package com.hitsz.eazytime.model;

import android.widget.FrameLayout;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class FinishedTodo extends LitePalSupport {
    private int id;
    private String title;
    private Date time;
    private int priority;

    public FinishedTodo(String title,Date time,int priority){
        this.title=title;
        this.time=time;
        this.priority=priority;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
