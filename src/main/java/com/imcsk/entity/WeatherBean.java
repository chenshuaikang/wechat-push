package com.imcsk.entity;

/**
 * @Description 天气信息实体类
 * @Author csk
 * @Date 2022/8/31
 */
public class WeatherBean {
    private String text_day;
    private String text_night;
    //  最高气温
    private String high;
    //  最低气温
    private String low;
    //  风力"3-4级"
    private String wc_day;
    //  风向"东北风"
    private String wd_day;
    private String wc_night;
    private String wd_night;
    private String date;
    private String week;
    // 当前天气
    private String text_now;
    // 当前温度
    private String temp;
    // 当前城市
    private String city;
    // 具体城市(到县级)
    private String cityName;
}
