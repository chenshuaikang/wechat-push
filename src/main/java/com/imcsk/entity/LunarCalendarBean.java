package com.imcsk.entity;

import org.springframework.stereotype.Component;

/**
 * @Description 阴历实体类
 * @Author csk
 * @Date 2022/9/11
 */
@Component
public class LunarCalendarBean {
    // 年
    public int lunarYear;
    // 月
    public int lunarMonth;
    // 日
    public int lunarDay;
    public boolean isLeap;

    public int getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(int lunarYear) {
        this.lunarYear = lunarYear;
    }

    public int getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(int lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public int getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(int lunarDay) {
        this.lunarDay = lunarDay;
    }

    public boolean isLeap() {
        return isLeap;
    }

    public void setLeap(boolean leap) {
        isLeap = leap;
    }

    @Override
    public String toString() {
        return lunarYear + "年" + lunarMonth + "月初" + lunarDay;
    }
}
