package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class RemindTodo extends LitePalSupport{
    private int id;
    private Todo todo;
    private Date time;

    public RemindTodo(Todo todo,Date time){
        this.todo=todo;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
