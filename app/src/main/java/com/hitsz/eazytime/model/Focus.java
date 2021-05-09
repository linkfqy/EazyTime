package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;


public class Focus extends LitePalSupport {

    private int focustime;
    private boolean success;
    public Focus(){
        focustime=0;
        success=false;
    }

    public int getFocustime(){return focustime;}

    public void setFocustime(int time){this.focustime=time;}

    public boolean getSuccess(){return success;}

    public void setSuccess(boolean success){this.success=success;}
}