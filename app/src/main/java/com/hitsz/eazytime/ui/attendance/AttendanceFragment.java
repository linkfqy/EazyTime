package com.hitsz.eazytime.ui.attendance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hitsz.eazytime.ui.attendance.AttendanceViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.Attendance;


import org.litepal.LitePal;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AttendanceFragment extends Fragment implements View.OnClickListener {

    private AttendanceViewModel attendanceViewModel;

    public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {//适配器

        private List<Attendance> dataList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, time ,interval;
            Button bt_delete;

            public ViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.attendance_item_title);
                time = view.findViewById(R.id.attendance_item_time);
                interval = view.findViewById(R.id.attendance_item_interval);
                bt_delete = view.findViewById(R.id.delete_attendance_item);
            }
        }
        public AttendanceAdapter() {
            dataList = new ArrayList<>();
        }

        public AttendanceAdapter(List<Attendance> dataList) {
            this.dataList = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attendance,parent,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Attendance y = dataList.get(position);
            holder.title.setText(y.getTitle());

            holder.time.setText(new SimpleDateFormat("HH:mm:ss").format(y.getTime()));

            holder.interval.setText(y.getRemindinterval());

            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LitePal.delete(Attendance.class, y.getId());
                    refresh();
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void refresh() {
            dataList = LitePal.findAll(Attendance.class);
            notifyDataSetChanged();
        }
    }


    static AttendanceAdapter adapter;
    RecyclerView attendanceRV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        attendanceViewModel =
                new ViewModelProvider(this).get(AttendanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);

        adapter = new AttendanceAdapter(new ArrayList<>());
        adapter.refresh();
        attendanceRV = root.findViewById(R.id.attendance_rv);
        attendanceRV.setAdapter(adapter);
        attendanceRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        FloatingActionButton bu = root.findViewById(R.id.add_attendance);
        bu.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_attendance) {
            new AddAttendanceDialog().show(getFragmentManager(), "call from attendance");
        }
    }
}