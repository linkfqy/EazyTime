package com.hitsz.eazytime.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**用于计算时间，数据库查数据时需要时间，主要完成对折线图的实现*/
    /**
     * 获取当日
     * @return
     */
    public static long getTodayTime(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取本周
     * @return
     */
    public static long getWeeklyTime(){
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_WEEK,c.getFirstDayOfWeek());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }
    /**
     * 获取本月
     * @return
     */
    public static long getMonthTime(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取本月第几天
     * @return
     */
    public static long getMonthTime(int day){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取第几个月月初时间戳
     * @return
     */
    public static long getMonthFirstTime(int month){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),1);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 格式化long类型的时间戳
     */
    public static String getCurrentTime(long dates) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date(dates));
        return date;
    }

    /**
     * 取得当月天数
     * */
    public static int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

}
