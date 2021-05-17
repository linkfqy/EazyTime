package com.hitsz.eazytime.ui.analyse;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XYearAxisValueFormatter implements IAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        return position+"月";
    }
}
