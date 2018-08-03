package com.edu.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tangzh on 2018/8/3.
 */
public class DateUtil {
    private final static String YYYYMMDD = "yyyyMMdd";
    private final static String YYYYMMDDHH = "yyyy-MM-dd:HH:mm:ss";


    public static String getCurDateForDay(){
        return forMatDate(new Date(),YYYYMMDD);
    }
    public static String getCurDateForHour(){
        return forMatDate(new Date(),YYYYMMDDHH);
    }

    //格式化日期
    public static String forMatDate(Date date,String fromat){
        return DateFormatUtils.format(date,fromat);
    }

    //获取日期的前-N/后+N天
    public static Date getPreOrNextDay(Date date,int n){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH,n);
        return instance.getTime();
    }

    //获取日期的前-M/后+M月
    public static Date getPreOrNextMonth(Date date,int m){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH,m);
        return instance.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getCurDateForDay());
        System.out.println(getCurDateForHour());
        System.out.println(forMatDate(getPreOrNextDay(new Date(),2),YYYYMMDDHH));
        System.out.println(forMatDate(getPreOrNextMonth(new Date(),2),YYYYMMDD));
    }


}
