package com.hitsz.eazytime.ui.focus;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.Focus;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Tomato_Working extends AppCompatActivity implements View.OnClickListener {
    private int time=0,  focusmin,relaxmin,focusnum=0,focusnum2,t=0;
    private boolean focusing=false;
    private Button startfocus, stopfocus,continucefocus;
    private EditText focus_min,relax_min,focus_num;
    private TextView showtime;
    private Timer timer = null;
    private TimerTask task = null;
    private Date date;
    Focus focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato__working);
        getit();
    }

    private void getit() {
        focus_min= findViewById(R.id.focustime);
        relax_min= findViewById(R.id.relaxtime);
        focus_num=findViewById(R.id.focusnum);
        startfocus = findViewById(R.id.startfocus);
        stopfocus = findViewById(R.id.stopfocus);
        continucefocus=findViewById(R.id.continucefocus);
        showtime = findViewById(R.id.showtime);
        startfocus.setOnClickListener(this);
        stopfocus.setOnClickListener(this);
        continucefocus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startfocus:
                if(time!=0||focusnum!=0)break;
                t=1;
                focusmin = Integer.parseInt(focus_min.getText().toString());
                relaxmin = Integer.parseInt(relax_min.getText().toString());
                focusnum2=focusnum=Integer.parseInt(focus_num.getText().toString());
                Start();
                if(time==0)t=0;
                break;
            case R.id.continucefocus:
                if(time!=0||focusnum!=0)Conti();
                break;
            case R.id.stopfocus:
                StopFocus();
                break;
            default:
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            showtime.setText(msg.arg1 + "");
            if(t==1) StartFocus();
        }

        ;
    };

    public void Start(){
        if(focusnum!=0){
            if(focusing){
                time = relaxmin * 60;
                focusing=false;
            }
            else {
                time = focusmin * 60;
                focusnum--;
                focusing=true;
            }
            StartFocus();
        }
        else {
            focusing=false;
            date=new Date(System.currentTimeMillis());
            focus=new Focus(focusmin*focusnum2*60,true,date);
            focus.save();
        }
    }

    public void StartFocus() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                if (time > 0) {
                    time--;
                    Message message = mHandler.obtainMessage();
                    message.arg1 = time;
                    mHandler.sendMessage(message);
                }
                else
                    Start();
            }
        };
        timer.schedule(task, 1000);
    }

    public void StopFocus(){
        if(focusing){
            date=new Date(System.currentTimeMillis());
            focus=new Focus(focusmin*(focusnum2-focusnum)*60-time,false,date);
            focus.save();
        }
        else{
            date=new Date(System.currentTimeMillis());
            focus=new Focus(focusmin*(focusnum2-focusnum)*60,false,date);
            focus.save();
        }
        time=0;
        t=0;
        Message message = mHandler.obtainMessage();
        message.arg1 = time;
        mHandler.sendMessage(message);
        timer.cancel();
    }

    public void Conti() {
        if (t == 1) {
            t = 0;
            timer.cancel();
        }
        else{
            t=1;
            StartFocus();
        }
    }
    public boolean IsFocusing(){
        ActivityManager activityManager=(ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName=getApplicationContext().getPackageName();//获取所有正在运行的app
        List<ActivityManager.RunningAppProcessInfo> appProcesses=activityManager.getRunningAppProcesses();
        if(appProcesses==null)return false;
        for(ActivityManager.RunningAppProcessInfo appProcess : appProcesses){
            if(appProcess.processName.equals(packageName) && appProcess.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onPause(){
        super.onPause();
        if(focusing)
            if(!IsFocusing()){
                Toast.makeText(getApplicationContext(),"专注失败",Toast.LENGTH_LONG).show();
                StopFocus();
            }
    }
}