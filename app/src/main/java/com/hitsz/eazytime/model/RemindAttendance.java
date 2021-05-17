package com.hitsz.eazytime.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class RemindAttendance extends LitePalSupport{
    private int id;
    private Attendance attendance;
    private Date time;

    public RemindAttendance(Attendance attendancettendance,Date time){
        this.attendance=attendance;
        this.time=time;
    }

    public int getId() {
        return id;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
