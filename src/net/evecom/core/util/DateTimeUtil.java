/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.evecom.core.util;

import net.evecom.platform.system.service.WorkdayService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * DateTimeUtil类
 * 
 * @author Flex Hu
 * @version 1.0
 */
public class DateTimeUtil {
    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(DateTimeUtil.class);
    /**
     * 属性一月最大天数
     */
    private static final int MAXDATE=0;// 一月最大天数
    /**
     * 属性一年最大天数
     */
    private static final int MAXYEAR=0;// 一年最大天数
    /**
     * 格式化年月日时分秒
     */
    public static final String PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    /**
     * workdayService
     */
    @Resource
    private WorkdayService workdayService;

    /**
     * 根据传入的季度值获取到该季度开始月份的值
     * 
     * @author Flex Hu
     * @param season
     * @return
     */
    public static int getBeginMonthOfSeason(int season) {
        switch (season) {
            case 1:
                return 1;
            case 2:
                return 4;
            case 3:
                return 7;
            case 4:
                return 10;
            default:
                ;
        }
        return 1;
    }

    /**
     * 根据传入的季度值获取到该季度结束月份的值
     * 
     * @author Flex Hu
     * @param season
     * @return
     */
    public static int getEndMonthOfSeason(int season) {
        switch (season) {
            case 1:
                return 3;
            case 2:
                return 6;
            case 3:
                return 9;
            case 4:
                return 12;
            default:
                ;
        }
        return 3;
    }

    /**
     * 获取当前年份
     * 
     * @return
     */
    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     * 
     * @return
     */
    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取传入月份所处季度的第一天
     * 
     * @param month
     *            :传入的月份
     * @return
     */
    public static String getThisSeasonFirstDay(int month) {
        String array[][] = {{"01", "02", "03" },{ "04", "05", "06" },{ "07", "08", "09" },{ "10", "11", "12" } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        String start_month = array[season - 1][0];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        String start_days = "01";// years+"-"+String.valueOf(start_month)+"-1";
        // getLastDayOfMonth(years_value,start_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days;
        return seasonDate;
    }

    /**
     * 获取传入月份所处季度的最后一天
     * 
     * @param month
     *            :传入月份
     * @return
     */
    public static String getThisSeasonEndDay(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int end_month = array[season - 1][2];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + end_month + "-" + end_days;
        return seasonDate;
    }

    /**
     * 获取某季度的第一天
     * 
     * @param years_value
     *            年份
     * @param season
     *            季度
     * @return
     */
    public static String getFirstDayBySeason(String years_value, String season) {
        String array[][] = { { "01", "02", "03" }, { "04", "05", "06" },{ "07", "08", "09" }, { "10", "11", "12" } };
        String start_month = array[Integer.valueOf(season) - 1][0];
        String start_days = "01";
        String seasonDate = years_value + "-" + start_month + "-" + start_days;
        return seasonDate;
    }

    /**
     * 获取某季度的第一天
     * 
     * @param years_value
     *            年份
     * @param season
     *            季度
     * @return
     */
    public static String getEndDayBySeason(String years_value, String season) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int end_month = array[Integer.valueOf(season) - 1][2];
        int end_days = getLastDayOfMonth(Integer.valueOf(years_value),
                end_month);
        String seasonDate = years_value + "-" + end_month + "-" + end_days;
        return seasonDate;
    }

    /**
     * 获取传入月份所处季度的起始时间和结束时间
     * 
     * @param month
     * @return
     */
    public static String getThisSeasonTime(int month) {
        int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
        int season = 1;
        if (month >= 1 && month <= 3) {
            season = 1;
        }
        if (month >= 4 && month <= 6) {
            season = 2;
        }
        if (month >= 7 && month <= 9) {
            season = 3;
        }
        if (month >= 10 && month <= 12) {
            season = 4;
        }
        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);

        int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days
                + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;

    }

    /**
     * 获取某年某月的最后一天
     * 
     * @param year
     *            年
     * @param month
     *            月
     * @return 最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        }
        return 0;
    }

    /**
     * 是否闰年
     * 
     * @param year
     *            年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 根据日期获取季度
     * 
     * @param cal
     * @return
     */
    public static int getSeason(Date date) {
        int month = date.getMonth();
        return (month + 3) / 3;
    }

    /**
     * 日期格式化
     * 
     * @param c
     * @param pattern
     * @return
     */
    public static Date format(String strDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        //ParsePosition pos = new ParsePosition(0);
        // Date strtodate = formatter.parse(strDate, pos);
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     * 
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //ParsePosition pos = new ParsePosition(0);
        // Date strtodate = formatter.parse(strDate, pos);
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return strtodate;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     * 
     * @param dateDate
     * @return
     */
    public static String dateToStr(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为 pattern形式的字符串
     * 
     * @param dateDate
     * @param pattern
     * @return
     */
    public static String dateToStr(Date dateDate, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 取得两个时间段的间隔天数
     * 
     * @param beginDate
     *            开始时间
     * @param endDate
     *            结束时间
     * @return 间隔天数
     * @throws ParseException
     */
    public static int getDaysBetween(String beginDate, String endDate) {
        Date bDate = strToDate(beginDate);
        Date eDate = strToDate(endDate);
        Calendar d1 = new GregorianCalendar();
        Calendar d2 = new GregorianCalendar();
        if (bDate.before(eDate)) {
            d1.setTime(bDate);
            d2.setTime(eDate);
        } else {
            d1.setTime(eDate);
            d2.setTime(bDate);
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);// 得到当年的实际天数
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        if (bDate.after(eDate)) {
            days = -days;
        }
        return days;
    }

    /**
     * 取得两个时间段的间隔月数
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getMonthsBetween(String beginDate, String endDate) {
        Date bDate = strToDate(beginDate);
        Date eDate = strToDate(endDate);
        Calendar d1 = new GregorianCalendar();
        Calendar d2 = new GregorianCalendar();
        if (bDate.before(eDate)) {
            d1.setTime(bDate);
            d2.setTime(eDate);
        } else {
            d1.setTime(eDate);
            d2.setTime(bDate);
        }
        int months = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                months += 12;
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }

        if (bDate.after(eDate)) {
            months = -months;
        }
        return months;
    }

    /**
     * 取得两个时间段的间隔年数
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getYearsBetween(String beginDate, String endDate) {
        Date bDate = strToDate(beginDate);
        Date eDate = strToDate(endDate);
        Calendar d1 = new GregorianCalendar();
        Calendar d2 = new GregorianCalendar();
        if (bDate.before(eDate)) {
            d1.setTime(bDate);
            d2.setTime(eDate);
        } else {
            d1.setTime(eDate);
            d2.setTime(bDate);
        }
        int years = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
        if (bDate.after(eDate)) {
            years = -years;
        }
        return years;
    }

    /**
     * 输出两个间隔时间段的所有季度的开始时间和结束时间
     * 
     * @param beginYear
     *            开始年份 yyyy
     * @param beginDate
     *            开始日期 MM-dd
     * @param endYear
     *            结束年份 yyyy
     * @param endDate
     *            结束日期 MM-dd
     * @return
     * @throws ParseException
     */
    public static List<String> getSeasonBeginTime(String beginYear, String beginDate, 
            String endYear, String endDate)throws ParseException {

        String array[] = { "01-01", "04-01", "07-01", "10-01" };
        String begin = beginYear + "-" + beginDate;
        String end = endYear + "-" + endDate;
        // 开始时间在第几季度
        int bQuar = getSeason(strToDate(begin));

        // 结束时间在第几季度
        int eQuar = getSeason(strToDate(end));

        List<String> times = new ArrayList<String>();

        int days = 0;// 时间段的天数
        days = getDaysBetween(begin, end);

        // 大于一个季度的天数
        if (days > 91) {
            // 计算间两时间段相隔几年
            int years = Integer.parseInt(endYear) - Integer.parseInt(beginYear);

            if (years == 0) {// 间隔年份在一年内
                for (int i = bQuar; i < array.length; i++) {
                    Date d1 = strToDate(endYear + "-" + array[i]);// 季度时间
                    if (d1.getTime() > strToDate(end).getTime()) {// 剩余季度的开始时间如果大于结束的时间
                        times.add(end);
                        return times;
                    } else {
                        times.add(endYear + "-" + array[i]);
                    }
                    ;

                }
                times.add(end);
                return times;
            } else {// 间隔年份超过1年

                // 添加开始时间所在年份的剩余季度开始时间
                for (int i = bQuar; i < array.length; i++) {
                    times.add(beginYear + "-" + array[i]);
                }
                // 添加间隔年份的所有季度开始时间
                int flag = years;
                for (int i = 0; i < years; i++, flag--) {
                    int temp = array.length;
                    if (flag == 1) {
                        temp = eQuar;
                    }
                    for (int j = 0; j < temp; j++) {
                        Integer aTemp = Integer.parseInt(beginYear) + i + 1;
                        times.add(aTemp.toString() + "-" + array[j]);
                    }
                }
                boolean check = true;
                for (int i = 0; i < array.length; i++) {
                    if (endDate.equals(array[i])) {
                        check = false;
                        break;
                    }
                }
                if (check)
                    times.add(end);

                return times;
            }

        } else {// 小于一个季度
            times.add(beginYear + "-" + array[bQuar]);
            return times;
        }

    }

    /**
     * 得到一个时间延后或前移几天的时间
     * 
     * @param nowdate
     *            当前时间
     * @param delay
     *            前移或后延的天数
     * @return
     */
    public static Date getNextDay(Date nowdate, int delay) {
        Calendar c = new GregorianCalendar();
        c.setTime(nowdate);
        c.add(Calendar.DATE, delay);
        return c.getTime();
    }

    /**
     * 得到一个时间延后或前移几月的时间
     * 
     * @param nowdate
     *            当前时间
     * @param delay
     *            前移或后延的月数
     * @return
     */
    public static Date getNextMonth(Date nowdate, int delay) {
        Calendar c = new GregorianCalendar();
        c.setTime(nowdate);
        c.add(Calendar.MONTH, delay);
        return c.getTime();
    }

    /**
     * 得到一个时间延后或前移几年的时间
     * 
     * @param nowdate
     *            当前时间
     * @param delay
     *            前移或后延的年数
     * @return
     */
    public static Date getNextYear(Date nowdate, int delay) {
        Calendar c = new GregorianCalendar();
        c.setTime(nowdate);
        c.add(Calendar.YEAR, delay);
        return c.getTime();
    }

    /**
     * 得到一个时间前或后n 分钟/小时/小时的时间（前时传递n的参数为负数）
     * 
     * @param date
     * @param n
     *            偏移量
     * @param type
     *            偏移类型：m 分钟 h小时 d天数
     * @return
     */
    public static Date getBeforeNDate(Date date, int n, String type) {
        // 偏移量，给定n天的毫秒数
        long offset =1000L * n * 24 * 60 * 60; // 默认是天

        if ("m".equals(type)) { // 分钟
            offset =1000L * n * 60;
        } else if ("h".equals(type)) {// 小时
            offset =1000L *n * 60 * 60 ;
        } else if ("d".equals(type)) { // 天
            offset = 1000L*n * 24 * 60 * 60 ;
        }
        Calendar c = Calendar.getInstance();

        if (date != null) {
            c.setTime(date);
            c.setTimeInMillis(c.getTimeInMillis() + offset);
        }
        return c.getTime();
    }

    /**
     * 返回提醒间隔的时间长整型
     * 
     * @param cycleInterval
     *            间隔时间（数字）
     * @param intervalType
     *            分 小时 天
     * @return
     */
    public static Long getInterval(String cycleInterval, String intervalType) {
        // 计算时间间隔
        long interval = 0;
        if ("m".equals(intervalType)) {// 分钟
            interval = NumberUtils.toLong(cycleInterval) * 1000 * 60;
        } else if ("h".equals(intervalType)) {// 小时
            interval = NumberUtils.toLong(cycleInterval) * 1000 * 60 * 60;
        } else if ("d".equals(intervalType)) {// 天
            interval = NumberUtils.toLong(cycleInterval) * 1000 * 60 * 60 * 24;
        }
        return interval;
    }

    /**
     * 根据一个日期，返回是星期几的字符串
     * 
     * @param sdate
     * @return
     */
    public static String getWeek(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 根据一个日期，返回是星期几
     * 
     * @param sdate
     * @return
     */
    public static int getWeekNum(String sdate) {
        // 再转换为时间
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekNum = c.get(Calendar.DAY_OF_WEEK);
        // weekNum中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        if (weekNum - 1 == 0) { // 转化成 1=星期一 2=星期二,其他类推
            weekNum = 7;
        } else {
            weekNum = weekNum - 1;
        }
        return weekNum;
    }

    /**
     * 获取当前月的第一天
     * 
     * @return
     */
    public static String getCurrentMonthFirstDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 计算当月最后一天,返回字符串
     * 
     * @return
     */
    public static String getCurrentMonthLastDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取上月第一天
     * 
     * @return
     */
    public static String getPreviousMonthFirstDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
        // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }
    
   

    /**
     * 获得当前日期与本周日相差的天数
     * 
     * @return
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            return 0;
        } else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周星期日的日期
     * 
     * @return
     */
    public static String getSundayOfCurrentWeekday() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());

    }

    /**
     * 获得本周一的日期
     * 
     * @return
     */
    public static String getMondayOfCurrentWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * 获得上周星期日的日期
     * 
     * @return
     */
    public static String getSundayOfPreviousWeek() {
        int weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得上周星期一的日期
     * 
     * @return
     */
    public static String getMondayOfPreviousWeek() {
        int weeks = 0;
        weeks--;
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期一的日期
     * 
     * @return
     */
    public static String getMondayOfNextWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得下周星期日的日期
     * 
     * @return
     */
    public static String getSundayOfNextWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    /**
     * 获得传入日期的 上月
     * 
     * @param yearMonth
     *            如：（2014-04）
     * @return 如：（2014-03）
     */
    public static String getPreviousMonth(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(yearMonth);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime());
    }

    /**
     * 获得传入日期的 下月
     * 
     * @param yearMonth
     *            如：（2014-04）
     * @return 如：（2014-05）
     */
    public static String getNextMonth(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(yearMonth);
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        return sdf.format(c.getTime());
    }

    /**
     * 获得上月最后一天的日期
     * 
     * @return
     */
    public static String getPreviousMonthEndDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, -1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月第一天的日期
     * 
     * @return
     */
    public static String getNextMonthFirstDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 减一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得下个月最后一天的日期
     * 
     * @return
     */
    public static String getNextMonthEndDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年最后一天的日期
     * 
     * @return
     */
    public static String getNextYearEndDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        lastDate.roll(Calendar.DAY_OF_YEAR, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获得明年第一天的日期
     * 
     * @return
     */
    public static String getNextYearFirstDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.YEAR, 1);// 加一个年
        lastDate.set(Calendar.DAY_OF_YEAR, 1);
        str = sdf.format(lastDate.getTime());
        return str;

    }

    /**
     * 获得本年有多少天
     * 
     * @return
     */
    private static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        return MaxYear;
    }

    /**
     * 方法getYearPlus
     * 
     * @return 返回值int
     */
    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
        cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
        cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
        int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
        if (yearOfNumber == 1) {
            return -MaxYear;
        } else {
            return 1 - yearOfNumber;
        }
    }

    /**
     * 获得本年第一天的日期
     * 
     * @return
     */
    public static String getCurrentYearFirstDay() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    /**
     * 获得本年最后一天的日期
     * 
     * @return
     */
    public static String getCurrentYearEndDay() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    /**
     * 获得去年第一天的日期
     * 
     * @return
     */
    public static String getPreviousYearFirstDay() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        years_value--;
        return years_value + "-1-1";
    }

    /**
     * 获得上年最后一天的日期
     * 
     * @return
     */
    public static String getPreviousYearEndDay() {
        int weeks = 0;
        weeks--;
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, yearPlus + MAXYEAR * weeks
                + (MAXYEAR - 1));
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        getThisSeasonTime(11);
        return preYearDay;
    }

    /**
     * 获取当前月的上一季度的第一天
     * 
     * @param month
     * @return
     */
    public static String getLastSeasonFirstDay(int month) {
        int targetYear = DateTimeUtil.getCurrentYear();
        String targetMonth = null;
        if (month >= 1 && month <= 3) {
            targetYear -= 1;
            targetMonth = "10";
        } else if (month >= 4 && month <= 6) {
            targetMonth = "01";

        } else if (month >= 7 && month <= 10) {
            targetMonth = "04";
        } else {
            targetMonth = "07";
        }
        return targetYear + "-" + targetMonth + "-01";
    }

    /**
     * 获取Date类型格式化后的字符串 time:传入的时间 format:传入的格式化样式:比如:yyyy-MM-dd HH:mm:ss
     */
    public static String getStrOfDate(Date time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    /**
     * 根据传入的时间和格式获取而得到时间类型
     * 
     * @param time
     * @param format
     * @return
     */
    public static Date getDateOfStr(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        return date;
    }

    /**
     * 获取两个时间的间隔分钟
     * 
     * @param beginTime
     * @param endTime
     * @return
     */
    public static long getIntervalMinute(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = sdf.parse(beginTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        // 默认为毫秒，除以1000是为了转换成秒
        long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
        long minute = interval / 60;// 分钟
        return minute;
    }
    /**
     * 
     * 描述 获取两个时间的间隔分钟(工作时间)
     * @author Rider Chen
     * @created 2017年3月2日 上午9:49:41
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public long getWorkMinute(String beginTime, String endTime) {
        if (workdayService == null) {
            workdayService = (WorkdayService) AppUtil.getBean("workdayService");
        }
        //工作日
        int leftWorkDay = workdayService.getWorkDayCount(beginTime, endTime);
        long minute = 0;    
        if(leftWorkDay==0){//开始时间与结束时间是同一天
            minute = getBeginMinute(beginTime)-getBeginMinute(endTime);
        }else if(leftWorkDay==1){//开始时间与结束时间是跨一天
            minute = getBeginMinute(beginTime)+(420-getBeginMinute(endTime));
        }else{//开始时间与结束时间是跨多天
            minute = getBeginMinute(beginTime)+(leftWorkDay-1)*420+(420-getBeginMinute(endTime));
        } 
        return minute;
    }

    /**
     * 描述:获取n个小时后的时间
     *
     * @author Madison You
     * @created 2020/9/14 10:20:00
     * @param laterHour 小时
     * @return
     */
    public static String getLaterHourDate(int laterHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + laterHour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(calendar.getTime());
    }

    /**
     * 描述 获取当天的工作分钟
     * @author Rider Chen
     * @created 2017年3月2日 上午11:04:14
     * @param beginTime
     */
    private static long getBeginMinute(String beginTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beginDate = null;
        long minute = 0;
        String pmBeginStr = "13:30";//下午开始时间
        int pmBegin = 1330;
        String pmEndStr = "17:00";//下午开始时间
        int pmEnd = 1700;
        try {
            beginDate = sdf.parse(beginTime);
            // 开始日期月
            int month = Integer.parseInt(dateToStr(beginDate, "MM"));
            //全年一样
            if(month<=12){
                pmBeginStr = "14:00";//下午开始时间
                pmBegin = 1400;
                pmEndStr = "17:30";//下午开始时间
                pmEnd = 1730;
            }
            // 开始日期小时
            String beginDay = dateToStr(beginDate, "yyyy-MM-dd");
            // 开始日期小时
            int beginHour = Integer.parseInt(dateToStr(beginDate, "HHmm"));
            
            if (beginHour >= 830 && beginHour <= 1200) {
                minute += getIntervalMinute(beginTime, beginDay + " 12:00");
                minute += getIntervalMinute(beginDay + " "+pmBeginStr, beginDay + " "+pmEndStr);
            }
            if (beginHour < 830 && beginHour <= 1200) {
                minute += getIntervalMinute(beginDay + " 08:30", beginDay + " 12:00");
                minute += getIntervalMinute(beginDay + " "+pmBeginStr, beginDay + " "+pmEndStr);
            }
            if (beginHour >= pmBegin && beginHour <= pmEnd) {
                minute += getIntervalMinute(beginTime, beginDay + " "+pmEndStr);
            }
            if (beginHour > 1200 && beginHour < pmBegin && beginHour <= pmEnd) {
                minute += getIntervalMinute(beginDay + " "+pmBeginStr, beginDay + " "+pmEndStr);
            }
            if (beginHour > pmEnd) {
                minute += 0;
            }
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e);
        }
        return minute;
    }
    /**
     * 
     * 描述 获取两个时间的间隔天数
     * @author Flex Hu
     * @created 2016年2月17日 下午4:47:22
     * @param beginTime
     * @param endTime
     * @return
     */
    public static long getIntervalDay(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = sdf.parse(beginTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        // 默认为毫秒，除以1000是为了转换成秒
        long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
        long day = interval / (24 * 3600);// 天
        return day;
    }
    
    /**
     * 
     * 描述 获取间隔时间
     * @author Flex Hu
     * @created 2016年3月8日 下午3:53:34
     * @param beginTime
     * @param endTime
     * @param timeFormat
     * @param type 1:返回间隔秒  2:返回间隔小时 3:返回间隔分钟 4:返回间隔天数
     * @return
     */
    public static long getIntervalTime(String beginTime,String endTime,String timeFormat,int type){
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = sdf.parse(beginTime);
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage());
        }
        // 默认为毫秒，除以1000是为了转换成秒
        long interval = (endDate.getTime() - beginDate.getTime()) / 1000;// 秒
        switch(type){
            case 1:
                return interval % 60;
            case 2:
                return interval % (24 * 3600) / 3600;
            case 3:
                return interval / 60;
            case 4:
                return interval / (24 * 3600);
            default:
                break;
        }
        return interval;
    }
    

    /**
     * 根据字符串时间类型获取年份
     * 
     * @param date
     * @return
     */
    public static int getYear(String date) {
        Date time = getDateOfStr(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取月份
     * 
     * @param date
     * @return
     */
    public static int getMonth(String date) {
        Date time = getDateOfStr(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据字符串时间获取日
     * 
     * @param date
     * @return
     */
    public static int getDay(String date) {
        Date time = getDateOfStr(date, "yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        return cal.get(Calendar.DATE);
    }

    /**
     * 当前季度的开始时间
     * 
     * @return
     */
    public static String getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        String str = "";
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 4);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return str;
    }

    /**
     * 当前季度的结束时间
     * 
     * @return
     */
    public static String getCurrentQuarterEndTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String str = "";
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return str;
    }

    /**
     * 当前年的开始时间
     * 
     * @return
     */
    public static String getCurrentYearStartTime() {
        Calendar c = Calendar.getInstance();
        String str = "";
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return str;
    }

    /**
     * 当前年的结束时间
     * 
     * @return
     */
    public static String getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        String str = "";
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            str = sdf.format(c.getTime());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return str;
    }

    /**
     * 获取某年第一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getCurrYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     * 
     * @param year
     *            年份
     * @return Date
     */
    public static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    /**
     * 方法getHours
     * 
     * @param minutes
     *            传入参数
     * @return 返回值String
     */
    public static String getHours(Long minutes) {
        Long hours = minutes / 60;
        Long minu = minutes % 60;
        if (minu != 0) {
            return hours + "小时" + minu + "分钟";
        } else {
            return hours + "小时";
        }
    }

    /***
     * 获取两个时间的间隔时间,以字符串返回
     * 
     * @author Flex Hu
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String getInternalTime(String beginTime, String endTime) {
        String timeConsuming = "";
        Long internal = null;
        internal = DateTimeUtil.getIntervalMinute(beginTime, endTime);
        if (internal >= 0 && internal < 60) {
            timeConsuming = internal + "分钟";
        } else if (internal >= 60 && internal < 1440) {
            // 求多少小时
            int hours = internal.intValue() / 60;
            // 求多少分钟
            int minutes = internal.intValue() % 60;
            timeConsuming = hours + "小时" + minutes + "分钟";
        } else if (internal >= 1440) {
            // 求出天数
            int days = internal.intValue() / 1440;
            // 求出小时数量
            int hours = (internal.intValue() - (days * 1440)) / 60;
            // 求出分钟数量
            int minutes = (internal.intValue() - (days * 1440)) % 60;
            timeConsuming = days + "天" + hours + "小时" + minutes + "分钟";
        }
        return timeConsuming;
    }
    /***
     * 获取两个时间的间隔工作时间,以字符串返回
     * 
     * @author Flex Hu
     * @param beginTime
     * @param endTime
     * @return
     */
    public static String getWorkTime(String beginTime, String endTime) {
        String timeConsuming = "";
        Long internal = null;
        internal = new DateTimeUtil().getWorkMinute(beginTime, endTime);
        if (internal >= 0 && internal < 60) {
            timeConsuming = internal + "分钟";
        } else if (internal >= 60) {
            // 求多少小时
            int hours = internal.intValue() / 60;
            // 求多少分钟
            int minutes = internal.intValue() % 60;
            timeConsuming = hours + "小时" + minutes + "分钟";
        }
       /* else if (internal >= 1440) {
            // 求出天数
            int days = internal.intValue() / 1440;
            // 求出小时数量
            int hours = (internal.intValue() - (days * 1440)) / 60;
            // 求出分钟数量
            int minutes = (internal.intValue() - (days * 1440)) % 60;
            timeConsuming = days + "天" + hours + "小时" + minutes + "分钟";
        }*/
        return timeConsuming;
    }
    /**
     * 根据日期，计算其所在年数 和 所在周数
     * 
     * @param date
     * @return 如：int[0]=2014 int[1]=17
     */
    public static int[] getWeekOfYear(Date date) {
        int[] result = new int[2];
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int weekOfyear = c.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        int intervalToSunday = dayOfWeek == 0 ? 0 : (7 - dayOfWeek);
        Date thizSunday = getNextDay(date, intervalToSunday);
        result[0] = getYear(dateToStr(thizSunday)); // 所在周的 周日年数
        result[1] = weekOfyear;
        return result;
    }

    /**
     * 根据日期所在年数 和 所在周数， 计算其所在周 的周一
     * 
     * @param year
     * @param weekOfYear
     * @return
     */
    public static Date getMondayByWeekOfYear(int year, int weekOfYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0) {
            dayofweek = 7;
        }
        calendar.add(Calendar.DATE, -dayofweek + 1);
        return calendar.getTime();
    }
    /**
     * 
     * 描述 获取中国日期显示方式
     * @author Flex Hu
     * @created 2015年5月21日 下午2:25:19
     * @param date
     * @return yyyy年mm月dd日
     */
    public static String getChinaDate(String date){
        int year = DateTimeUtil.getYear(date);
        int month = DateTimeUtil.getMonth(date);
        int day = DateTimeUtil.getDay(date);
        return year+"年"+month+"月"+day+"日";
    }
    
    /**
     * 
     * 描述 格式化日期字符串
     * @author Flex Hu
     * @created 2015年5月21日 下午2:32:47
     * @param sourceDate
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String formatDateStr(String sourceDate,String sourceFormat,String targetFormat){
        Date d = DateTimeUtil.getDateOfStr(sourceDate,sourceFormat);
        return DateTimeUtil.getStrOfDate(d, targetFormat);
    }

    /**
     * 描述:获取过去某一天的日期
     *
     * @author Madison You
     * @created 2020/10/23 10:21:00
     * @param
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取当前时间
     * @param pattern
     * @return
     */
    public static String getNowTime(String pattern){
        return localDateTimeToStr(LocalDateTime.now(),pattern);
    }

    /**
     * LocalDateTime转化为指定格式字符串
     * @param fromDateTime
     * @param dateTimeFotmat
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime fromDateTime, String dateTimeFotmat){
        if (StringUtils.isEmpty(dateTimeFotmat)) {
            dateTimeFotmat = PATTERN_YMD_HMS;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateTimeFotmat);
        return fromDateTime.format(df);
    }
    
    /**
     * 描述:获取过去七天的每天的日期
     *
     * @author Madison You
     * @created 2020/10/23 10:22:00
     * @param
     * @return
     */
    public static ArrayList<String> getPastWeekDate(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals; i >= 1; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 两个时间字符串想减返回时间差
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param type 返回类型 D-天,H-小时,M-分钟,S-秒,L-毫秒
     * @return
     */
    public static long diffTime(String beginTime,String endTime,String type){
        long diffDuration = 0L;
        if(StringUtil.isNotEmpty(beginTime)&&StringUtil.isNotEmpty(endTime)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YMD_HMS);
            LocalDateTime beginDateTime = LocalDateTime.parse(beginTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
            Duration duration = Duration.between(beginDateTime, endDateTime);

            switch (type){
                case "D":
                    diffDuration = duration.toDays();
                    break;
                case "H":
                    diffDuration = duration.toHours();
                    break;
                case "M":
                    diffDuration = duration.toMinutes();
                    break;
                case "S":
                    diffDuration = duration.getSeconds();
                    break;
                case "L":
                    diffDuration = duration.toMillis();
                default:
                    break;
            }
        }

        return diffDuration;
    }

    /**
     * 时间戳转日期
     *
     * @param timestamp
     * @return
     */
    public static String timestampToStrLocalDateTime(long timestamp) {
        return localDateTimeToStr(LocalDateTime.ofEpochSecond(timestamp/1000
                ,0, ZoneOffset.ofHours(8)),null);
    }

    /**
     * 获取当前系统时间戮
     *
     * @return
     */
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }
}
