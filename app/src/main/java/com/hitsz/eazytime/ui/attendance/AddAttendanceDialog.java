package com.hitsz.eazytime.ui.attendance;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.hitsz.eazytime.R;

public class AddAttendanceDialog extends DialogFragment implements
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener{
    TextView timeText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_add_attendance, container, false);
        //do sth

        timeText=(TextView) root.findViewById(R.id.timeText);
        timeText.setText("打卡提醒时间");
        timeText.setOnClickListener(this);

        Button okButton = (Button)root.findViewById(R.id.ok_button);
//        okButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Snackbar.make(v, "已添加待办", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        okButton.setOnClickListener(this);
        return root;
    }


    @Override
    public void onStart() {
        /*
            因为View在添加后,对话框最外层的ViewGroup并不知道我们导入的View所需要的的宽度。 所以我们需要在onStart生命周期里修改对话框尺寸参数
         */
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onStart();
    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc=String.format("%d时%d分",hourOfDay,minute);
        timeText.setText(desc);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.timeText){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog=new TimePickerDialog(getContext(),this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }
        if (v.getId()==R.id.ok_button) {
            Snackbar.make(v, "已新建打卡", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}


