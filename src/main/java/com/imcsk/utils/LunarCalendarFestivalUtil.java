package com.imcsk.utils;

import com.imcsk.entity.LunarCalendarBean;
import com.imcsk.entity.LunarCalendarFestivalBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 获取输入公历日期的生肖、天干地支、农历年、农历月、农历日、公历节日、农历节日、24节气等数据
 * @Author csk
 * @Date 2022/9/11
 */
public class LunarCalendarFestivalUtil {

    /**
     *
     *      xxxx	xxxx	xxxx	xxxx	xxxx
     * 位数	20-17	16-12	12-9	8-5	    4-1
     * 1-4 位: 表示当年有无闰年，有的话，为闰月的月份，没有的话，为0。
     * 5-16 位：为除了闰月外的正常月份是大月还是小月，1为30天，0为29天。
     * 注意：从 1 月到 12 月对应的是第 16 位到第 5 位（而不是从第 5 位 到第 16 位）。
     * 17-20 位： 1 和 0 分别表示闰月是 大月 还是 小月，大月 30 天，小月 29 天（仅当存在闰月的情况下有意义）。
     *
     * eg:
     * 1982年 => 0x0a974
     * 二进制：0000 1010 1001 0111 0100
     * 1-4 位：0100 => 表示1982年的4月为闰月，即有第二个4月。
     * 16-5 位：1001 0101 1011 => 表示1982年从1月到13月的天数依次为：30、29、30、29、 29(闰四小月)、 30、29、29、30、 29、30、30、30。
     * 20-17 位：0000 => 闰月，0 为闰小月，得出4月是闰小月
     *
     */
    final static long[] lunarInfo = new long[]{
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
            0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
            0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
            0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
            0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
            0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
            0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
            0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
            0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
            0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
            0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    };
    //阳历天数
    final static int[] solarMonths = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    //生肖
    final static String[] animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    //天干
    final static String[] tGan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    //地支
    final static String[] dZhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    //二十四节气
    final static String[] solarTerms = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏",
            "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    //二十四节气日期偏移度
    private static final double D = 0.2422;
    //特殊年份节气日期偏移
    private final static Map<Integer, Integer[]> INCREASE_OFFSET_MAP = new HashMap<Integer, Integer[]>();//+1偏移
    private final static Map<Integer, Integer[]> DECREASE_OFFSET_MAP = new HashMap<Integer, Integer[]>();//-1偏移

    static {
        INCREASE_OFFSET_MAP.put(0, new Integer[]{1982});//小寒
        DECREASE_OFFSET_MAP.put(0, new Integer[]{2019});//小寒
        INCREASE_OFFSET_MAP.put(1, new Integer[]{2082});//大寒
        DECREASE_OFFSET_MAP.put(3, new Integer[]{2026});//雨水
        INCREASE_OFFSET_MAP.put(5, new Integer[]{2084});//春分
        INCREASE_OFFSET_MAP.put(9, new Integer[]{2008});//小满
        INCREASE_OFFSET_MAP.put(10, new Integer[]{1902});//芒种
        INCREASE_OFFSET_MAP.put(11, new Integer[]{1928});//夏至
        INCREASE_OFFSET_MAP.put(12, new Integer[]{1925, 2016});//小暑
        INCREASE_OFFSET_MAP.put(13, new Integer[]{1922});//大暑
        INCREASE_OFFSET_MAP.put(14, new Integer[]{2002});//立秋
        INCREASE_OFFSET_MAP.put(16, new Integer[]{1927});//白露
        INCREASE_OFFSET_MAP.put(17, new Integer[]{1942});//秋分
        INCREASE_OFFSET_MAP.put(19, new Integer[]{2089});//霜降
        INCREASE_OFFSET_MAP.put(20, new Integer[]{2089});//立冬
        INCREASE_OFFSET_MAP.put(21, new Integer[]{1978});//小雪
        INCREASE_OFFSET_MAP.put(22, new Integer[]{1954});//大雪
        DECREASE_OFFSET_MAP.put(23, new Integer[]{1918, 2021});//冬至
    }

    //定义一个二维数组，第一维数组存储的是20世纪的节气C值，第二维数组存储的是21世纪的节气C值,0到23个，依次代表立春、雨水...大寒节气的C值
    private static final double[][] CENTURY_ARRAY = {
            {6.11, 20.84, 4.6295, 19.4599, 6.3826, 21.4155, 5.59, 20.888, 6.318, 21.86, 6.5, 22.2, 7.928, 23.65, 8.35, 23.95, 8.44, 23.822, 9.098, 24.218, 8.218, 23.08, 7.9, 22.6},
            {5.4055, 20.12, 3.87, 18.73, 5.63, 20.646, 4.81, 20.1, 5.52, 21.04, 5.678, 21.37, 7.108, 22.83, 7.5, 23.13, 7.646, 23.042, 8.318, 23.438, 7.438, 22.36, 7.18, 21.94}
    };
    //农历月份
    final static String lunarNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    //农历年
    final static String[] lunarYears = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    final static String[] chineseTen = new String[]{"初", "十", "廿", "卅"};
    //农历节日
    final static String[] lunarHoliday = new String[]{"0101 春节", "0115 元宵节", "0202 龙头节", "0505 端午节", "0707 七夕节", "0715 中元节",
            "0815 中秋节", "0909 重阳节", "1001 寒衣节", "1015 下元节", "1208 腊八节", "1223 小年"};
    //公立节日
    final static String[] solarHoliday = new String[]{"0101 元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0315 消费者权益日",
            "0401 愚人节", "0422 地球日", "0423 读书日", "0501 劳动节", "0504 青年节", "0512 护士节", "0518 博物馆日", "0519 旅游日", "0601 儿童节",
            "0701 建党节", "0801 建军节", "0910 教师节", "1001 国庆节", "1024 联合国日", "1204 宪法日", "1224 平安夜", "1225 圣诞节"};
    //格式化日期
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    static SimpleDateFormat solarDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 返回农历y年的总天数
     *
     * @param y
     * @return
     */
    private int lunarYearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            sum += ((lunarInfo[y - 1900] & i) != 0 ? 1 : 0);
        }
        return (sum + leapDays(y));
    }

    /**
     * 返回农历y年闰月的天数
     */
    private int leapDays(int y) {
        if (leapMonth(y) != 0) {
            return ((lunarInfo[y - 1900] & 0x10000) != 0 ? 30 : 29);
        } else
            return 0;
    }

    /**
     * 判断y年的农历中那个月是闰月,不是闰月返回0
     *
     * @param y
     * @return
     */
    private int leapMonth(int y) {
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 返回农历y年m月的总天数
     *
     * @param y
     * @param m
     * @return
     */
    private int monthDays(int y, int m) {
        return ((lunarInfo[y - 1900] & (0x10000 >> m)) != 0 ? 30 : 29);
    }

    /**
     * 获取阴历年
     *
     * @param year
     * @return
     */
    private String getLunarYearString(String year) {
        int y1 = Integer.parseInt(year.charAt(0) + "");
        int y2 = Integer.parseInt(year.charAt(1) + "");
        int y3 = Integer.parseInt(year.charAt(2) + "");
        int y4 = Integer.parseInt(year.charAt(3) + "");
        return lunarYears[y1] + lunarYears[y2] + lunarYears[y3] + lunarYears[y4];
    }

    /**
     * 获取阴历日
     */
    private String getLunarDayString(int day) {
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + lunarNumber[n];
    }

    /**
     * 特例,特殊的年分的节气偏移量,由于公式并不完善，所以算出的个别节气的第几天数并不准确，在此返回其偏移量
     *
     * @param year 年份
     * @param n    节气编号
     * @return 返回其偏移量
     */
    private int specialYearOffset(int year, int n) {
        int offset = 0;
        offset += getOffset(DECREASE_OFFSET_MAP, year, n, -1);
        offset += getOffset(INCREASE_OFFSET_MAP, year, n, 1);
        return offset;
    }

    /**
     * 节气偏移量计算
     *
     * @param map
     * @param year
     * @param n
     * @param offset
     * @return
     */
    private int getOffset(Map<Integer, Integer[]> map, int year, int n, int offset) {
        int off = 0;
        Integer[] years = map.get(n);
        if (null != years) {
            for (int i : years) {
                if (i == year) {
                    off = offset;
                    break;
                }
            }
        }
        return off;
    }

    /**
     * 获取某年的第n个节气为几日(从0小寒起算)
     *
     * @param year
     * @param n
     * @return
     */
    private int sTerm(int year, int n) {
        double centuryValue = 0;//节气的世纪值，每个节气的每个世纪值都不同
        int centuryIndex = -1;
        if (year >= 1901 && year <= 2000) {//20世纪
            centuryIndex = 0;
        } else if (year >= 2001 && year <= 2100) {//21世纪
            centuryIndex = 1;
        } else {
            throw new RuntimeException("不支持此年份：" + year + "，目前只支持1901年到2100年的时间范围");
        }
        centuryValue = CENTURY_ARRAY[centuryIndex][n];
        int dateNum = 0;

        int y = year % 100;//步骤1:取年分的后两位数
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年
            if (n == 0 || n == 1 || n == 2 || n == 3) {
                //注意：凡闰年3月1日前闰年数要减一，即：L=[(Y-1)/4],因为小寒、大寒、立春、雨水这两个节气都小于3月1日,所以 y = y-1
                y = y - 1;//步骤2
            }
        }
        dateNum = (int) (y * D + centuryValue) - (int) (y / 4);//步骤3，使用公式[Y*D+C]-L计算
        dateNum += specialYearOffset(year, n);//步骤4，加上特殊的年分的节气偏移量
        return dateNum;
    }

    /**
     * 母亲节和父亲节
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String getMotherOrFatherDay(int year, int month, int day) {
        if (month != 5 && month != 6) return null;
        if ((month == 5 && (day < 8 || day > 14)) || (month == 6 && (day < 15 || day > 21))) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        weekDate = (weekDate == 1) ? 7 : weekDate - 1;
        switch (month) {
            case 5:
                if (day == 15 - weekDate) {
                    return "母亲节";
                }
                break;
            case 6:
                if (day == 22 - weekDate) {
                    return "父亲节";
                }
                break;
        }
        return null;
    }

    /**
     * 感恩节
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String thanksgiving(int year, int month, int day) {
        if (month != 11) return null;
        if ((month == 11 && (day < 19 || day > 28))) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        weekDate = (weekDate == 1) ? 7 : weekDate - 1;
        switch (month) {
            case 11:
                if (day == 29 - weekDate + 4) {
                    return "感恩节";
                }
                break;
        }
        return null;
    }

    /**
     * 获取复活节
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    private String getEasterDay(int year, int month, int day) {
        int n = year - 1900;
        int a = n % 19;
        int q = n / 4;
        int b = (7 * a + 1) / 19;
        int m = (11 * a + 4 - b) % 29;
        int w = (n + q + 31 - m) % 7;
        int answer = 25 - m - w;
        String easterDay = "";
        if (answer > 0) {
            easterDay = year + "-" + 4 + "-" + answer;
        } else {
            easterDay = year + "-" + 3 + "-" + (31 + answer);
        }
        String searchDay = year + "-" + month + "-" + day;
        if (searchDay.equals(easterDay)) {
            return "复活节";
        }
        return null;
    }

    /**
     * 输入公历日期初始化当前日期的生肖、天干地支、农历年、农历月、农历日、公历节日、农历节日、24节气
     * 输入日期的格式为(YYYY-MM-DD)
     *
     * @param currentDate
     */
    public void initLunarCalendarInfo(String currentDate,LunarCalendarFestivalBean festivalBean) {
        String[] splitDate = currentDate.split("-");
        //设置生肖
        int year = Integer.parseInt(splitDate[0]);
        festivalBean.setAnimal( animals[(year - 4) % 12]);
        //设置天干地支
        int num = year - 1900 + 36;
        festivalBean.setGanZhiYear((tGan[num % 10] + dZhi[num % 12]));

        //基准日期
        Date baseDate = null;
        //当前日期
        Date nowaday = null;
        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
            nowaday = solarDateFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 获取当前日期与1900年1月31日相差的天数
        int offset = (int) ((nowaday.getTime() - baseDate.getTime()) / 86400000L);

        //用offset减去每农历年的天数，计算当天是农历第几天 iYear最终结果是农历的年份
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 10000 && offset > 0; iYear++) {
            daysOfYear = lunarYearDays(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        festivalBean.setLunarYear(getLunarYearString(iYear + ""));
        int leapMonth = leapMonth(iYear); // 闰哪个月,1-12
        boolean leap = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(iYear);
            } else
                daysOfMonth = monthDays(iYear, iMonth);

            offset -= daysOfMonth;
            // 解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
        }
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
            }
        }
        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        // 设置对应的阴历月份
        festivalBean.setLunarMonth(lunarNumber[iMonth - 1]);
        if ("一".equals(festivalBean.getLunarMonth())) {
            festivalBean.setLunarMonth("正");
        }
        if ("十二".equals(festivalBean.getLunarMonth())) {
            festivalBean.setLunarMonth("腊");
        }
        if (leap) {
            festivalBean.setLunarMonth("闰" + festivalBean.getLunarMonth());
        }

        //设置阴历日
        int iDay = offset + 1;
        festivalBean.setLunarDay(getLunarDayString(iDay));

        //设置节气
        int month = Integer.parseInt(splitDate[1]);
        int day = Integer.parseInt(splitDate[2]);
        if (day == sTerm(year, (month - 1) * 2)) {
            festivalBean.setLunarTerm(solarTerms[(month - 1) * 2]);
        } else if (day == sTerm(year, (month - 1) * 2 + 1)) {
            festivalBean.setLunarTerm(solarTerms[(month - 1) * 2 + 1]);
        } else {
            festivalBean.setLunarTerm("");
        }

        //设置阳历节日
        String solarFestival = "";
        for (int i = 0; i < solarHoliday.length; i++) {
            // 返回公历节假日名称
            String sd = solarHoliday[i].split(" ")[0]; // 节假日的日期
            String sdv = solarHoliday[i].split(" ")[1]; // 节假日的名称
            String smonth_v = splitDate[1];
            String sday_v = splitDate[2];
            String smd = smonth_v + sday_v;
            if (sd.trim().equals(smd.trim())) {
                solarFestival = sdv;
                break;
            }
        }
        //判断节日是否是父亲节或母亲节
        String motherOrFatherDay = getMotherOrFatherDay(year, month, day);
        if (motherOrFatherDay != null) {
            solarFestival = motherOrFatherDay;
        }
        //判断节日是否是复活节
        String easterDay = getEasterDay(year, month, day);
        if (easterDay != null) {
            solarFestival = easterDay;
        }
        //判断节日是否是感恩节
        String thanksgiving = thanksgiving(year, month, day);
        if (thanksgiving != null) {
            solarFestival = thanksgiving;
        }
        festivalBean.setSolarFestival(solarFestival);

        //设置阴历节日
        String lunarFestival = "";
        for (int i = 0; i < lunarHoliday.length; i++) {
            //阴历闰月节日
            if (leap) {
                break;
            }
            // 返回农历节假日名称
            String ld = lunarHoliday[i].split(" ")[0]; // 节假日的日期
            String ldv = lunarHoliday[i].split(" ")[1]; // 节假日的名称
            String lmonth_v = iMonth + "";
            String lday_v = iDay + "";
            String lmd = "";
            if (iMonth < 10) {
                lmonth_v = "0" + iMonth;
            }
            if (iDay < 10) {
                lday_v = "0" + iDay;
            }
            lmd = lmonth_v + lday_v;
            if ("12".equals(lmonth_v)) { // 除夕夜需要特殊处理
                if ((daysOfMonth == 29 && iDay == 29) || (daysOfMonth == 30 && iDay == 30)) {
                    lunarFestival = "除夕";
                    break;
                }
            }
            if (ld.trim().equals(lmd.trim())) {
                lunarFestival = ldv;
                break;
            }
        }
        if ("清明".equals(festivalBean.getLunarTerm())) {
            lunarFestival = "清明节";
        }
        festivalBean.setLunarFestival(lunarFestival);
    }
}
