package com.imcsk.entity;

import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author csk
 * @Date 2022/9/11
 */
@Component
public class LunarCalendarFestivalBean {
    //生肖年
    private String animal;
    //干支年
    private String ganZhiYear;
    //阴历年
    private String lunarYear;
    //阴历月
    private String lunarMonth;
    //阴历日
    private String lunarDay;
    //阳历节日
    private String solarFestival;
    //阴历节日
    private String lunarFestival;
    //节气
    private String lunarTerm;

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getGanZhiYear() {
        return ganZhiYear;
    }

    public void setGanZhiYear(String ganZhiYear) {
        this.ganZhiYear = ganZhiYear;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getSolarFestival() {
        return solarFestival;
    }

    public void setSolarFestival(String solarFestival) {
        this.solarFestival = solarFestival;
    }

    public String getLunarFestival() {
        return lunarFestival;
    }

    public void setLunarFestival(String lunarFestival) {
        this.lunarFestival = lunarFestival;
    }

    public String getLunarTerm() {
        return lunarTerm;
    }

    public void setLunarTerm(String lunarTerm) {
        this.lunarTerm = lunarTerm;
    }
}
