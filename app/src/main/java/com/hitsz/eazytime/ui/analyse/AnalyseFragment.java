package com.hitsz.eazytime.ui.analyse;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hitsz.eazytime.R;
import com.hitsz.eazytime.model.FinishedTodo;
import com.hitsz.eazytime.model.Focus;
import com.hitsz.eazytime.utils.DateUtils;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyseFragment extends Fragment {

    private AnalyseViewModel analyseViewModel;

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;

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
        lc1 = root.findViewById(R.id.lineChart1);
        lc2 = root.findViewById(R.id.lineChart2);

        List<Focus> list = LitePal.findAll(Focus.class);
        tv1.setText(list.size()+"次");
        float total=total(list);
        tv2.setText(String.format("%.2f",total)+"分");
        tv3.setText(String.format("%.2f",total/list.size())+"分");

        List<Focus> list1 = LitePal.where("date > ?", DateUtils.getTodayTime()+"").find(Focus.class);
        tv4.setText(list1.size()+"次");
        tv6.setText(String.format("%.2f",total(list1))+"分");
        List<Focus> list2 = LitePal.where("date > ? and success = ?", DateUtils.getTodayTime()+"","1").find(Focus.class);
        tv5.setText(list2.size()+"次");

        List<FinishedTodo> list3 = LitePal.where("time > ?", DateUtils.getTodayTime()+"").find(FinishedTodo.class);
        tv7.setText(list3.size()+"件");

        List<FinishedTodo> list4 = LitePal.where("time > ?", DateUtils.getWeeklyTime()+"").find(FinishedTodo.class);
        tv8.setText(list4.size()+"件");

        List<FinishedTodo> list5 = LitePal.where("time > ?", DateUtils.getMonthTime()+"").find(FinishedTodo.class);
        tv9.setText(list5.size()+"件");

        List<FinishedTodo> list6 = LitePal.findAll(FinishedTodo.class);
        tv10.setText(list6.size()+"件");

        initLineChart(lc1,getMonthData(),"月度专注时长");
        initLineChart(lc2,getYearData(),"年度专注时长");

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
     * 初始化折线图控件属性
     */
    private void initLineChart(LineChart lineChart,ArrayList<Entry> values,String title) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setBackgroundColor(Color.WHITE);

        IAxisValueFormatter xAxisFormatter;
        //自定义适配器，适配于X轴
        if (title.contains("年")) {
            xAxisFormatter = new XYearAxisValueFormatter();
        } else {
            xAxisFormatter = new XAxisValueFormatter();
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xAxisFormatter);

        //自定义适配器，适配于Y轴
        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        lineChart.getAxisRight().setEnabled(false);

        //这里，每重新new一个LineDataSet，相当于重新画一组折线
        //每一个LineDataSet相当于一组折线。比如:这里有两个LineDataSet：setComp1，setComp2。
        //则在图像上会有两条折线图，分别表示公司1 和 公司2 的情况.还可以设置更多
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