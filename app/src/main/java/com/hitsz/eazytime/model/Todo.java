package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Todo extends LitePalSupport implements Comparable<Todo>{
    private int id;
    private String title;
    private Date startTime;
    private Date endTime;
    private int priority;
    private boolean needRemind;
    private List<RemindTodo> remindTodoList;

    public Todo(){
        this.title=null;
        this.startTime=new Date();
        this.endTime=new Date();
        this.priority=0;
        this.needRemind=false;
        this.remindTodoList=new ArrayList<>();
    }

    @Override
    public int compareTo(Todo x){
        if (this.priority>x.priority) return -1;
        if (this.startTime.getTime()<x.startTime.getTime()) return -1;
        return 1;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isNeedRemind() {
        return needRemind;
    }

    public void setNeedRemind(boolean needRemind) {
        this.needRemind = needRemind;
    }

    public List<RemindTodo> getRemindTodoList() {
        return remindTodoList;
    }

    public void setRemindTodoList(List<RemindTodo> remindTodoList) {
        this.remindTodoList = remindTodoList;
    }

    public void addRemindTodo(RemindTodo rt){
        remindTodoList.add(rt);
    }

}
