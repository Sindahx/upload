package com.leaihealth.cloud.util.date;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd";
    public static final String YYYYMMDD_DATE_FORMATE = "yyyyMMdd";
    @SuppressWarnings("unused")
    public static final String YYYYMMDDHHmiss_DATE_FORMATE = "yyyyMMdd HH:mm";
    public static final String YYYY_MM_DD_HHmiss_DATE_FORMATE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_yyyy_MM_dd_hh = "yyyy-MM-dd HH";
    public static final String FORMAT_yyyy_MM_dd = "yyyy-MM-dd";
    public static final String FORMAT_yyyy = "yyyy";
    public static final String FORMAT_HH_mm = "HH:mm";
    public static final String FORMAT_HH = "HH";
//    public static final String FORMAT_MM月dd日 = "MM月dd日";
//    public static final String FORMAT_dd日 = "dd日";
//    public static final String FORMAT_YYYY年MM月dd日HHmm = "yyyy年MM月dd日 HH:mm";
//    public static final String FORMAT_yyyy年MM月dd日HH时mm分 = "yyyy年MM月dd日HH时mm分";
//    public static final String FORMAT_yyyy年MM月dd日HH时 = "yyyy年MM月dd日HH时";
    public static final String FORMATyyyyMMddhhmm = "yyyyMMddHHmm";
    public static final String FORMATyyyyMMddhhmmss = "yyyyMMddHHmmss";
    public static final String FORMATyyyyMMddhh = "yyyyMMddHH";
//    public static final String FORMAMM月dd日 = "MM月dd日";
//    public static final String FORMAMM_yyyy年MM月dd日 = "yyyy年MM月dd日";

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
    public static String getCurDate() {
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
     * @param formate 日期格式(比如：yyyy-MM-dd)
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
     * 获得一天的结束时间，主要用于查询是的结束时间条件 例如：输入2007-09-27 08:09:10，输出得到 2007-09-28
     * 00:00:00
     *
     * @param date 原始日期
     * @return Date
     * @author Liaoke
     * @since 2007-9-28
     */
    public static Date getEndOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23); // 强行设置为23点，为下步四舍五入做准备
        Date result = org.apache.commons.lang.time.DateUtils.round(c.getTime(),
                Calendar.DATE);
        return result;
    }


    public static Date getCurrentDateAdd(int days) {
        return getDateDayAdd(new Date(), days);
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
     * 当前日期向前减少days天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date getDateDayDel(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_YEAR);
        c.set(Calendar.DAY_OF_YEAR, day - days);
        Date d = c.getTime();
        return d;
    }

    /**
     * 当前日期向前增加months月
     *
     * @param date
     * @param months
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
     * @param hours
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
     * @param years
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

    /**
     * 当前时间向前增加second秒
     *
     * @param date
     * @param second
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

    public static SeasonBean getSeason(Integer type, String year) throws Exception {

        String startDateStr = "";
        String endDateStr = "";
        if (type == 1) {
            startDateStr = year + "-01-01 00:00:00";
            endDateStr = year + "-03-31 23:59:59";
        } else if (type == 2) {
            startDateStr = year + "-04-01 00:00:00";
            endDateStr = year + "-06-30 23:59:59";
        } else if (type == 3) {
            startDateStr = year + "-07-01 00:00:00";
            endDateStr = year + "-09-30 23:59:59";
        } else if (type == 4) {
            startDateStr = year + "-10-01 00:00:00";
            endDateStr = year + "-12-31 23:59:59";
        } else {
            throw new Exception("season 参数错误");
        }
        Date startDate = DateUtils.getDate(startDateStr, FORMAT_yyyy_MM_dd_hh_mm_ss);
        Date endDate = DateUtils.getDate(endDateStr, FORMAT_yyyy_MM_dd_hh_mm_ss);
        SeasonBean season = new SeasonBean();
        season.setStartDate(startDate);
        season.setEndDate(endDate);

        return season;
    }

    public static void main(String[] args) {
        Date strDate = getStrOrDate("2016-12-31", DateUtils.FORMAT_yyyy_MM_dd);
        System.out.println(format(getDateDayAdd(strDate, -0), FORMAT_yyyy_MM_dd));
//        System.out.println(getWeekOfDate(DateUtils.getDate()));
        System.out.println(daysBetween("217-10-27 09:51:22", "217-10-27 10:51:44", DateUtils.FORMAT_yyyy_MM_dd_hh_mm_ss, 60000));
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
        java.util.Date date1;
        java.util.Date date0;
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

    /**
     * string 转化为date
     *
     * @param date
     * @param formatString
     * @return
     */
    public static Date getStrOrDate(String date, String formatString) {
        SimpleDateFormat sdf_d = new SimpleDateFormat(formatString);
        try {
            return sdf_d.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断两个时间大小  bddate - smdata
     *
     * @param smdate
     * @param bdate
     * @return
     */
    public static int daysBetween(String smdate, String bdate) {
        long between_days = 1l;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(smdate);
            Date d2 = sdf.parse(bdate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean validityOf(Date startData, Date endDate) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(startData);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        cal.setTime(new Date());
        long time3 = cal.getTimeInMillis();

        if (time1 < time3 || time3 < time2) {
            return true;
        }

        return false;
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 判断两个时间大小  bddate - smdata 根据指定格式放回
     *
     * @param smdate
     * @param bet    返回天  月 年 时  分 秒  1000*3600*24 天   1000*60 分
     * @return
     */
    public static long daysBetween(String smdate, String bdate, String famt, int bet) {
        long between_days = 1l;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(famt);
            Date d1 = sdf.parse(smdate);
            Date d2 = sdf.parse(bdate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (bet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return between_days;
    }


    public static String getAge(String birthday, String sourceFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(birthday, pos);

        Calendar cal = Calendar.getInstance();

        if (cal.before(strtodate)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(strtodate);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;
        if (age != 0) {
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) age--;
                } else {
                    age--;
                }
            }
            return age + "岁";
        } else {
            age = monthNow - monthBirth;

            return age + "月";


        }


    }

    /**
     * 获取系统当前时间后一个小时long格式的时间
     *
     * @return
     */
    public static Long getNowDateLong() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);
        long date = c.getTimeInMillis();
        return date;
    }

    /**
     * 从map获取指定时间的long格式时间
     *
     * @param map
     * @param strTime
     * @return
     */
    public static Long getDateLongByMap(Map<String, Object> map, String strTime) {
        String startTime = map.get("" + strTime + "").toString();
        Date time = DateUtils.getStrOrDate(startTime, YYYY_MM_DD_HHmiss_DATE_FORMATE);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        long time1 = c.getTimeInMillis();
        return time1;
    }

    /**
     * 判断当直播时间大于系统时间后一个小时则可以删除修改操作
     *
     * @param listMap
     * @return
     */
    public static List<Map<String, Object>> insertType(List<Map<String, Object>> listMap, String StrTime) {
        try {
            long date = getNowDateLong();
            for (int i = 0; i < listMap.size(); i++) {
                Map<String, Object> map = listMap.get(i);
                long time1 = getDateLongByMap(map, StrTime);
                if (time1 > date) {
                    //可以修改删除
                    map.put("isDelete", "1");
                } else {
                    map.put("isDelete", "0");
                }
            }
            return listMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDayDate(Date date) {
        Date date2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long time = date.getTime() + 86400000;
        String d = format.format(time);
        try {
            date2 = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    public static Integer compareDate(String date1, String date2,String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            Date date3 = format.parse(date1);
            Date date4 = format.parse(date2);
            return compareDate(date3,date4);//方式一
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param date1
     * @param date2
     * @Author: wangxer
     * @Description: 方式一 用Date的before，after方法
     * @Date: Created in 11:35 on 2017/9/30.
     */
    public static Integer compareDate(Date date1, Date date2) {
        if (date1.before(date2)) {
//            System.out.println(date1 + "在" + date2 + "前面");
            return 1;
        } else if (date1.after(date2)) {
//            System.out.println(date1 + "在" + date2 + "后面");
            return -1;
        } else {
//            System.out.println("是同一天的同一时间");
            return 0;
        }
    }
}
