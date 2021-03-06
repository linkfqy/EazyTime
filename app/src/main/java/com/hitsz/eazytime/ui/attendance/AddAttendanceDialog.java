package com.hitsz.eazytime.ui.attendance;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.Attendance;
import com.hitsz.eazytime.model.RemindAttendance;

import java.util.Date;

public class AddAttendanceDialog extends DialogFragment implements
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener{
    TextView timeText;
    EditText title,remindinterval;
    CheckBox remindperday,remindperweek;
    Attendance attendance;
    LinearLayout ll;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_add_attendance, container, false);
        //do sth


        timeText= root.findViewById(R.id.remind_time);
        title= root.findViewById(R.id.attendance_title);
        remindperday= root.findViewById(R.id.remind_per_day);
        remindperweek= root.findViewById(R.id.remind_per_week);
        remindinterval= root.findViewById(R.id.remind_interval);
        ll= root.findViewById(R.id.reminding);
        timeText.setOnClickListener(this);
        attendance=new Attendance();


        Button okButton = (Button)root.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        ??????????????????View???????????????????????????????????????view ??????????????????????????????
        ?????????????????????????????????view?????????????????????????????????????????????????????????ViewGroup??????????????????
        ???????????????view????????????????????????????????????????????????onStart??????????????????????????????????????????
         */

    }

    @Override
    public void onStart() {
        /*
            ??????View????????????,?????????????????????ViewGroup???????????????????????????View???????????????????????? ?????????????????????onStart??????????????????????????????????????????
         */
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onStart();
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc=String.format("%d???%d???",hourOfDay,minute);
        Calendar cal=Calendar.getInstance();
        cal.setTime(attendance.getTime());
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                hourOfDay,minute);
        attendance.setTime(cal);
        timeText.setText(desc);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.remind_time){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog=new TimePickerDialog(getContext(),this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }
        if (v.getId()== R.id.ok_button) {
            attendance.setTitle(title.getText().toString());
                if (remindperday.isChecked()){
                    Date dt=new Date();
                    dt.setTime(attendance.getTime().getTime());
                    attendance.setRemindinterval(1);
                    RemindAttendance rt=new RemindAttendance(attendance,dt);
                    rt.save();
                    attendance.addRemindAttendance(rt);
                }
                if (remindperweek.isChecked()){
                    Date dt=new Date();
                    dt.setTime(attendance.getTime().getTime());
                    attendance.setRemindinterval(7);
                    RemindAttendance rt=new RemindAttendance(attendance,dt);
                    rt.save();
                    attendance.addRemindAttendance(rt);
                }
                if (!remindinterval.getText().toString().equals("")){  //??????????????????
                    Date dt=new Date();
                    dt.setTime(attendance.getTime().getTime()-Integer.parseInt(remindinterval.getText().toString()));
                    String value= remindinterval.getText().toString();
                    int finalValue=Integer.parseInt(value);
                    attendance.setRemindinterval(finalValue);
                    RemindAttendance rt=new RemindAttendance(attendance,dt);
                    rt.save();
                    attendance.addRemindAttendance(rt);
                }
            attendance.save();
            Snackbar.make(v, "???????????????", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            AttendanceFragment.adapter.refresh();
            this.dismiss();
        }
    }
}