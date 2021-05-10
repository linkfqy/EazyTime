package com.hitsz.eazytime.ui.todo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*
        此方法在视图View已经创建后返回的，但是这个view 还没有添加到父级中。
        我们在这里可以重新设定view的各个数据，但是不能修改对话框最外层的ViewGroup的布局参数。
        因为这里的view还没添加到父级中，我们需要在下面onStart生命周期里修改对话框尺寸参数
         */

    }

    @Override
    public void onStart() {
        /*
            因为View在添加后,对话框最外层的ViewGroup并不知道我们导入的View所需要的的宽度。 所以我们需要在onStart生命周期里修改对话框尺寸参数
         */
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
        super.onStart();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String desc=String.format("%d年%d月%d日",year,month+1,dayOfMonth);
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
        String desc=String.format("%d时%d分",hourOfDay,minute);
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
            if (needRemind.isChecked()){  //需要提醒
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
                if (!customBefore.getText().toString().equals("")){  //自定义不为空
                    Date dt=new Date();
                    dt.setTime(todo.getStartTime().getTime()-Integer.parseInt(customBefore.getText().toString())*3600000);
                    RemindTodo rt=new RemindTodo(todo,dt);
                    rt.save();
                    todo.addRemindTodo(rt);
                }
            }
            todo.save();
            Snackbar.make(super.getParentFragment().getView(), "已添加待办", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            TodoFragment.adapter.refresh();
            this.dismiss();
        }
    }
}