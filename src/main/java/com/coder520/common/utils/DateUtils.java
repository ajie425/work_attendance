package com.coder520.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static Calendar calendar = Calendar.getInstance();

    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/12 0012 13:38
     *@Description 得到今天是周几
     */
    public static int getTodayWeek(){

        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(week<0) week=7;
        return week;
    }
    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/12 0012 13:39
     *@Description 计算时间差 分钟数
     */
    public static int getMunite(Date startDate,Date endDate){

        long start = startDate.getTime();
        long end = endDate.getTime();
        int munite = (int)(end-start)/(1000*60);
        return munite;
    }
    /**
     *@Author Ajie [1134846386@qq.com]
     *@Date 2018/6/12 0012 13:53
     *@Description 获取当天某个时间
     */
    public static Date getDate(int hour,int munite){

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,munite);
        return calendar.getTime();

    }
}
