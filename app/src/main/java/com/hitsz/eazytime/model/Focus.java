package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;


public class Focus extends LitePalSupport {

    private int id;
    private int focustime;
    private boolean success;
    private Date date;

    public Focus(int ftime,boolean fsuccess,Date fdate){
        focustime=ftime;
        success=fsuccess;
        date=fdate;
    }

    public int getId(){return id;}

    public int getFocustime(){return focustime;}

    public void setFocustime(int time){this.focustime=time;}

    public boolean getSuccess(){return success;}

    public void setSuccess(boolean success){this.success=success;}

    public void setFocusDate(Date date){this.date=date;}

    public Date getFocusDate(){return date;}

}