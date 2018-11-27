package com.zxyl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";
    private static final String YYYYMMDD_DATE_FORMATE = "yyyyMMdd";
    @SuppressWarnings("unused")
	public static final String YYYYMMDDHHmiss_DATE_FORMATE = "yyyyMMdd HH:mm";
    public static final String YYYY_MM_DD_HHmiss_DATE_FORMATE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_yyyy_MM_dd_hh = "yyyy-MM-dd HH";
    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_HH_mm = "HH:mm";
    public static final String FORMAT_HH = "HH";
    public static final String FORMAT_MM月dd日 = "MM月dd日";
    public static final String FORMAT_dd日 = "dd日";
    public static final String FORMAT_YYYY年MM月dd日HHmm = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_yyyy年MM月dd日HH时mm分 = "yyyy年MM月dd日HH时mm分";
    public static final String FORMAT_yyyy年MM月dd日HH时 = "yyyy年MM月dd日HH时";
    public static final String FORMATyyyyMMddhhmm = "yyyyMMddHHmm";
    public static final String FORMATyyyyMMddhhmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String FORMATyyyyMMddhh = "yyyyMMddHH";
    public static final String FORMAMM月dd日 = "MM月dd日";
    public static final String FORMAMM_yyyy年MM月dd日 = "yyyy年MM月dd日";

    /**
     * 获取系统当前时间
     * 
     * @return Long
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static Long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取系统当前日期,日期格式为yyyy-MM-dd。
     * 
     * @return 返回字符串型的日期 String
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMATE);
        return sdf.format(getDate());
    }

    /**
     * 获取系统当前日期,日期格式为yyyy-MM-dd HH:mm:ss。
     *
     * @return 返回字符串型的日期 String
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static String getCurDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(getDate());
    }

    /**
     * 获取系统当前日期,日期格式为yyyyMMdd。
     * 
     * @return 返回字符串型的日期 String
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static String getYYYYMMDDDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD_DATE_FORMATE);
        return sdf.format(getDate());
    }

    /**
     * 获取系统指定格式当前日期
     * 
     * @param formate
     *            日期格式(比如：yyyy-MM-dd)
     * @return 返回字符串型的日期 String
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static String getCurrentDate(String formate) {
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(getDate());
    }

    /**
     * 获取系统当前默认日期
     * 
     * @return 返回日期 Date
     * @author zhuangruhai
     * @since 2007-9-27
     */
    public static Date getDate() {
        return new Date();
    }



    /**
     * 当前日期向前增加days天
     * 
     * @param date
     * @param days
     * @return
     */
    public static Date getDateDayAdd(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_YEAR);
        c.set(Calendar.DAY_OF_YEAR, day + days);
        Date d = c.getTime();
        return d;
    }

    /**
     * 当前日期向前增加months月
     * 
     * @param date
     * @return
     */
    public static Date getDateMonthAdd(Date date, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month + months);
        Date d = c.getTime();
        return d;
    }

    /**
     * 当前日期时间向前增加hour小时
     * 
     * @param date
     * @return
     */
    public static String getDateHourAdd(Date date, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int hour = c.get(Calendar.HOUR);
        c.set(Calendar.HOUR, hour + hours);
        Date d = c.getTime();
        return DateUtils.toString(d, DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss);
    }

    /**
     * 当前日期向前增加years年
     * 
     * @param date
     * @return
     */
    public static Date getDateYearAdd(Date date, int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        c.set(Calendar.YEAR, year + years);
        Date d = c.getTime();
        return d;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date getDate(String date, String format) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String getAge(String birthday) {
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            Date today = new Date();
            int year = (today.getYear() - date.getYear());
            if (year >=1) {
                return year+"岁";
            }else{
                int month = today.getMonth() - date.getMonth();
                if(month >= 1){
                    return month +"月";
                }else{
                    int day = today.getDay() - date.getDay();
                    return day + "天";
                }
            }
            //System.out.println("你的年龄是:" + age);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 当前时间向前增加second秒
     * 
     * @param date
     * @return
     */
    public static Date getDateSecondAdd(Date date, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int addSecond = c.get(Calendar.SECOND);
        c.set(Calendar.SECOND, addSecond + second);
        Date d = c.getTime();
        return d;
    }

    /**
     * 当前日期向前增加minute分
     * 
     * @param date
     * @param minutes
     * @return
     */

    public static String getDateMinuteAdd(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.MINUTE, minute + minutes);
        Date d = c.getTime();
        return DateUtils.toString(d, DateUtils.FORMATyyyyMMddhhmm);
    }

    public static Date getDateMinute(Date date, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        c.set(Calendar.MINUTE, minute + minutes);
        Date d = c.getTime();
        return d;
    }

    public static void main(String[] args) {
    	
//        System.out.println(getWeekOfDate(DateUtils.getDate()));
        getAge("19780809");
    }

    /*
     * 将字符格式转换成日期格式
     * @parm dateString 日期字符串 fromatString 日期格式
     * @author yanjie
     * @return date
     */
    public static Date toDate(String dateString, String formatString) {

        if (dateString == null || formatString == null) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        
    }
    /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /*
     * 将日期格式转换成字符格式
     * @parm date 日期类型 fromatString 日期格式
     * @author yanjie
     * @return String
     */
    public static String toString(Date date, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }
    
    public static boolean dateCompare(String dateStr1, String dateStr2, String pattern) {
        boolean bea = false;
        SimpleDateFormat sdf_d = new SimpleDateFormat(pattern);
        Date date1;
        Date date0;
        try {
            date1 = sdf_d.parse(dateStr1);
            date0 = sdf_d.parse(dateStr2);
            if (date0.after(date1)) {
                bea = true;
            }
        } catch (ParseException e) {
            bea = false;
        }
        return bea;
    }


    
}
