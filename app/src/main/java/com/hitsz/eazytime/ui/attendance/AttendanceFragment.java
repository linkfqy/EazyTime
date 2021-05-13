package com.hitsz.eazytime.ui.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.ui.attendance.AttendanceViewModel;

public class AttendanceFragment extends Fragment implements View.OnClickListener{

    private AttendanceViewModel attendanceViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        attendanceViewModel =
                new ViewModelProvider(this).get(AttendanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);
        final TextView textView = root.findViewById(R.id.text_attendance);
        attendanceViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        FloatingActionButton fab = root.findViewById(R.id.add_attendance);
        fab.setOnClickListener(this);
        return root;
    }
    @Override
    public void onClick(View view) {
//                Snackbar.make(view, "搞个DialogFragment出来", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
        new AddAttendanceDialog().show(getFragmentManager(), "call from attendance");
    }
}
