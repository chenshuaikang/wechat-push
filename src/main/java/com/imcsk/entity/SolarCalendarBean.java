package com.imcsk.entity;

import org.springframework.stereotype.Component;

/**
 * @Description 阳历实体类
 * @Author csk
 * @Date 2022/9/11
 */
@Component
public class SolarCalendarBean {
    public int solarYear;
    public int solarMonth;
    public int solarDay;

    public int getSolarYear() {
        return solarYear;
    }

    public void setSolarYear(int solarYear) {
        this.solarYear = solarYear;
    }

    public int getSolarMonth() {
        return solarMonth;
    }

    public void setSolarMonth(int solarMonth) {
        this.solarMonth = solarMonth;
    }

    public int getSolarDay() {
        return solarDay;
    }

    public void setSolarDay(int solarDay) {
        this.solarDay = solarDay;
    }

    /**
     * 转成String类型的日期
     *
     * @return "yyyy-MM-dd"
     */
    public String getStringDate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.solarYear);
        stringBuilder.append("-");
        if (this.solarMonth < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(this.solarMonth);
        stringBuilder.append("-");
        if (this.solarDay < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(this.solarDay);
        return stringBuilder.toString();
    }

    public void parseDate(String birthday) {
        String[] split = birthday.split("-");
        this.solarYear = Integer.parseInt(split[0]);
        this.solarMonth = Integer.parseInt(split[1]);
        this.solarDay = Integer.parseInt(split[2]);
    }

    @Override
    public String toString() {
        return solarYear + "年" + solarMonth + "月" + solarDay + "日";
    }
}
