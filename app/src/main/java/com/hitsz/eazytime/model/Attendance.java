package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Attendance extends LitePalSupport {
    private int id;
    private String title;
    private Date Time;
    private int Remindinterval;
    private List<RemindAttendance> remindAttendanceList;

    public Attendance(){
        this.title=null;
        this.Time=new Date();
        this.Remindinterval=1;
        this.remindAttendanceList=new ArrayList<>();
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
        return Time;
    }

    public void setTime(Date Time) {
        this.Time = Time;
    }

    public void setTime(Calendar cal) {
        this.Time = cal.getTime();
    }

    public int getRemindinterval() { return  Remindinterval;}

    public void setRemindinterval(int Remindinterval) { this.Remindinterval=Remindinterval;}

    public List<RemindAttendance> getRemindAttendanceList() {
        return remindAttendanceList;
    }

    public void setRemindAttendanceList(List<RemindAttendance> remindAttendanceList) {
        this.remindAttendanceList = remindAttendanceList;
    }

    public void addRemindAttendance(RemindAttendance rt){
        remindAttendanceList.add(rt);
    }

}
