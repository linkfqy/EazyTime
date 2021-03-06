package com.hitsz.eazytime.ui.todo;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.RemindTodo;
import com.hitsz.eazytime.model.Todo;

import java.util.Date;
import java.util.List;

public class AddTodoDialog extends DialogFragment implements
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener{

    TextView dateText,timeText;
    EditText title,customBefore;
    Spinner priority;
    CheckBox needRemind,noBefore,tenMinBefore,oneDayBefore;
    Todo todo;
    LinearLayout ll;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_add_todo, container, false);
        //do sth

        dateText= root.findViewById(R.id.dateText);
        timeText= root.findViewById(R.id.timeText);
        title= root.findViewById(R.id.todo_title);
        priority= root.findViewById(R.id.todo_priority);
        needRemind= root.findViewById(R.id.need_remind_cb);
        noBefore= root.findViewById(R.id.no_before_cb);
        tenMinBefore= root.findViewById(R.id.ten_min_before_cb);
        oneDayBefore= root.findViewById(R.id.one_day_before_cb);
        customBefore= root.findViewById(R.id.custom_before);
        priority.setSelection(2);
        ll= root.findViewById(R.id.remind_setting);
        ll.setVisibility(View.GONE);
        dateText.setOnClickListener(this);
        timeText.setOnClickListener(this);
        needRemind.setOnClickListener(this);
        noBefore.setOnClickListener(this);
        tenMinBefore.setOnClickListener(this);
        oneDayBefore.setOnClickListener(this);
        customBefore.setOnClickListener(this);
        todo=new Todo();
        Button okButton = root.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        final Window window=getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        window.setLayout(-1,-2);  //????????????????????????
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
        params.gravity= Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(params);
        super.onStart();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc=String.format("%d???%d???%d???",year,month+1,dayOfMonth);
        Calendar cal=Calendar.getInstance();
        cal.setTime(todo.getStartTime());
        cal.set(year,month,dayOfMonth,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE));
        todo.setStartTime(cal);
        dateText.setText(desc);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc=String.format("%d???%d???",hourOfDay,minute);
        Calendar cal=Calendar.getInstance();
        cal.setTime(todo.getStartTime());
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
                hourOfDay,minute);
        todo.setStartTime(cal);
        timeText.setText(desc);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.dateText){
            Calendar calendar=Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(getContext(),this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }
        if (v.getId()==R.id.timeText){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog=new TimePickerDialog(getContext(),this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }
        if (v.getId()==R.id.need_remind_cb){
            if (needRemind.isChecked()){
                ll.setVisibility(View.VISIBLE);
                todo.setNeedRemind(true);
            }else{
                ll.setVisibility(View.GONE);
                todo.setNeedRemind(false);
            }
        }
        if (v.getId()==R.id.ok_button) {
            todo.setTitle(title.getText().toString());
            todo.setPriority(priority.getSelectedItemPosition());
            if (needRemind.isChecked()){  //????????????
                if (noBefore.isChecked()){
                    Date dt=new Date();
                    dt.setTime(todo.getStartTime().getTime());
                    RemindTodo rt=new RemindTodo(todo,dt);
                    rt.save();
                    todo.addRemindTodo(rt);
                }
                if (tenMinBefore.isChecked()){
                    Date dt=new Date();
                    dt.setTime(todo.getStartTime().getTime()-600000);
                    RemindTodo rt=new RemindTodo(todo,dt);
                    rt.save();
                    todo.addRemindTodo(rt);
                }
                if (oneDayBefore.isChecked()){
                    Date dt=new Date();
                    dt.setTime(todo.getStartTime().getTime()-86400000);
                    RemindTodo rt=new RemindTodo(todo,dt);
                    rt.save();
                    todo.addRemindTodo(rt);
                }
                if (!customBefore.getText().toString().equals("")){  //??????????????????
                    Date dt=new Date();
                    dt.setTime(todo.getStartTime().getTime()-Integer.parseInt(customBefore.getText().toString())*3600000);
                    RemindTodo rt=new RemindTodo(todo,dt);
                    rt.save();
                    todo.addRemindTodo(rt);
                }
            }
            todo.save();
            Snackbar.make(super.getParentFragment().getView(), "???????????????", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            TodoFragment.adapter.refresh();
            this.dismiss();
        }
    }
}