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

public class focusing extends AppCompatActivity implements View.OnClickListener {
    private int focustime=0,  focusmin, t=0;
    private Button startfocus, stopfocus,continucefocus;
    private EditText time;
    private TextView showtime;
    private Timer timer = null;
    private TimerTask task = null;
    private  Date date;
    Focus focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focusing);
        getit();
    }

    private void getit() {
        time = findViewById(R.id.time);
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
                if(focustime!=0)break;
                t=1;
                focusmin = Integer.parseInt(time.getText().toString());
                focustime = focusmin * 60;
                StartFocus();
                if(focustime==0)t=0;
                break;
            case R.id.continucefocus:
                if(focustime!=0)Conti();
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


    public void StartFocus() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                if (focustime > 0) {
                    focustime--;
                    Message message = mHandler.obtainMessage();
                    message.arg1 = focustime;
                    mHandler.sendMessage(message);
                }
                else{
                    date=new Date(System.currentTimeMillis());
                    focus=new Focus(focusmin*60,true,date);
                    focus.save();
                }
            }
        };
        timer.schedule(task, 1000);
    }

    public void StopFocus(){
        date=new Date(System.currentTimeMillis());
        focus=new Focus(focusmin*60-focustime,false,date);
        focus.save();
        focustime=0;
        t=0;
        Message message = mHandler.obtainMessage();
        message.arg1 = focustime;
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
        List<ActivityManager.RunningAppProcessInfo>appProcesses=activityManager.getRunningAppProcesses();
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
        if(focustime>0)
            if(!IsFocusing()){
                Toast.makeText(getApplicationContext(),"专注失败",Toast.LENGTH_LONG).show();
                StopFocus();
            }
    }
}