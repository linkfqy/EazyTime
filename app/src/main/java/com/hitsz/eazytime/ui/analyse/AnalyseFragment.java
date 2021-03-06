package com.hitsz.eazytime.ui.analyse;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.Attendance;
import com.hitsz.eazytime.model.FinishAttendance;
import com.hitsz.eazytime.model.FinishedTodo;
import com.hitsz.eazytime.model.Focus;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class AnalyseFragment extends Fragment {

    private AnalyseViewModel analyseViewModel;

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12,tv13,tv14;

    private LineChart lc1, lc2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        analyseViewModel =
                new ViewModelProvider(this).get(AnalyseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_analyse, container, false);

        tv1 = root.findViewById(R.id.tv_1);
        tv2 = root.findViewById(R.id.tv_2);
        tv3 = root.findViewById(R.id.tv_3);
        tv4 = root.findViewById(R.id.tv_4);
        tv5 = root.findViewById(R.id.tv_5);
        tv6 = root.findViewById(R.id.tv_6);
        tv7 = root.findViewById(R.id.tv_7);
        tv8 = root.findViewById(R.id.tv_8);
        tv9 = root.findViewById(R.id.tv_9);
        tv10 = root.findViewById(R.id.tv_10);
        tv11 = root.findViewById(R.id.tv_11);
        tv12 = root.findViewById(R.id.tv_12);
        tv13 = root.findViewById(R.id.tv_13);
        tv14 = root.findViewById(R.id.tv_14);

        lc1 = root.findViewById(R.id.lineChart1);
        lc2 = root.findViewById(R.id.lineChart2);

        List<Focus> list = LitePal.findAll(Focus.class);
        tv1.setText(list.size()+"???");
        float total=total(list);
        tv2.setText(String.format("%.2f",total)+"???");
        tv3.setText(String.format("%.2f",total/list.size())+"???");

        List<Focus> list1 = LitePal.where("date > ?", DateUtils.getTodayTime()+"").find(Focus.class);
        tv4.setText(list1.size()+"???");
        tv6.setText(String.format("%.2f",total(list1))+"???");
        List<Focus> list2 = LitePal.where("date > ? and success = ?", DateUtils.getTodayTime()+"","1").find(Focus.class);
        tv5.setText(list2.size()+"???");

        List<FinishedTodo> list3 = LitePal.where("time > ?", DateUtils.getTodayTime()+"").find(FinishedTodo.class);
        tv7.setText(list3.size()+"???");

        List<FinishedTodo> list4 = LitePal.where("time > ?", DateUtils.getWeeklyTime()+"").find(FinishedTodo.class);
        tv8.setText(list4.size()+"???");

        List<FinishedTodo> list5 = LitePal.where("time > ?", DateUtils.getMonthTime()+"").find(FinishedTodo.class);
        tv9.setText(list5.size()+"???");

        List<FinishedTodo> list6 = LitePal.findAll(FinishedTodo.class);
        tv10.setText(list6.size()+"???");

        List<FinishAttendance> list7 = LitePal.where("Time > ?", DateUtils.getTodayTime()+"").find(FinishAttendance.class);
        tv11.setText(list7.size()+"???");

        List<FinishAttendance> list8 = LitePal.where("Time > ?", DateUtils.getWeeklyTime()+"").find(FinishAttendance.class);
        tv12.setText(list8.size()+"???");

        List<FinishAttendance> list9 = LitePal.where("Time > ?", DateUtils.getMonthTime()+"").find(FinishAttendance.class);
        tv13.setText(list9.size()+"???");

        List<FinishAttendance> list10 = LitePal.findAll(FinishAttendance.class);
        tv14.setText(list10.size()+"???");

        initLineChart(lc1,getMonthData(),"??????????????????");
        initLineChart(lc2,getYearData(),"??????????????????");

        return root;
    }


    private float total(List<Focus> list){
        float total=0;
        for (Focus focus : list) {
            total += focus.getFocustime();
        }
        return total/60;
    }

    private ArrayList<Entry> getMonthData(){
        ArrayList<Entry> values = new ArrayList<Entry>();
        int maxDate = DateUtils.getCurrentMonthLastDay();
        long time ;
        for (int i = 0; i < maxDate; i++) {
            time = DateUtils.getMonthTime(i+1);
            Log.e("!!!!", DateUtils.getCurrentTime(time));
            List<Focus> list = LitePal.where("date > ? and date < ?", time+"",(time+24*60*60*1000)+"").find(Focus.class);
            values.add(new Entry(i+1, total(list)));
        }
        return values;
    }

    private ArrayList<Entry> getYearData(){
        ArrayList<Entry> values = new ArrayList<Entry>();
        long time ;
        for (int i = 0; i < 12; i++) {
            time = DateUtils.getMonthFirstTime(i);
            Log.e("!!!!", DateUtils.getCurrentTime(time));
            List<Focus> list = LitePal.where("date > ? and date < ?", time+"",(DateUtils.getMonthFirstTime(i+1))+"").find(Focus.class);
            values.add(new Entry(i+1, total(list)));
        }
        return values;
    }

    /**
     * ??????????????????????????????
     */
    private void initLineChart(LineChart lineChart,ArrayList<Entry> values,String title) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        IAxisValueFormatter xAxisFormatter;
        //??????????????????????????????X???
        if (title.contains("???")) {
            xAxisFormatter = new XYearAxisValueFormatter();
        } else {
            xAxisFormatter = new XAxisValueFormatter();
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        //??????????????????????????????Y???
        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        lineChart.getAxisRight().setEnabled(false);

        //??????????????????new??????LineDataSet?????????????????????????????????
        //?????????LineDataSet??????????????????????????????:???????????????LineDataSet???setComp1???setComp2???
        //?????????????????????????????????????????????????????????1 ??? ??????2 ?????????.?????????????????????
        LineDataSet setComp1 = new LineDataSet(values, title);
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(getResources().getColor(R.color.purple_500));
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);

        LineData lineData = new LineData(dataSets);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }


}