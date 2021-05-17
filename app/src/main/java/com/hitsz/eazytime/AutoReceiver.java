package com.hitsz.eazytime;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hitsz.eazytime.ui.todo.TodoFragment;
import com.hitsz.eazytime.ui.todo.TodoViewModel;

import org.litepal.LitePal;

public class AutoReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_FLAG = 1;
    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("VIDEO_TIMER")){
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);
            Notification notify = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_todo_black_24dp)
                    .setTicker("TickerText:"+"您有新的提醒呦")
                    .setContentTitle("叮叮叮")
                    .setContentText("是忘记了什么重要的事情吧")
                    .setContentIntent(pendingIntent).setNumber(1).build();
            notify.flags |=Notification.FLAG_AUTO_CANCEL;
            NotificationManager manager=(NotificationManager)context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(NOTIFICATION_FLAG, notify);
        }
    }

}