package com.imcsk.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 推送相关参数配置
 * @Author csk
 * @Date 2022/8/31
 */
@Component
@ConfigurationProperties("wechat")
public class PushConfigBean {
    /**
     * 微信公众平台的appID
     */
    private static String appId;
    /**
     * 微信公众平台的appSecret
     */
    private static String secret;
    /**
     * 天气查询的城市ID
     */
    private static String district_id;
    /**
     * 应用AK
     */
    private static String mapAk;
    /**
     * 纪念日
     */
    private static String loveDate;
    /**
     * 生日
     */
    private static String birthday;
    /**
     * 关注公众号的用户ID
     */
    private static List<String> userId;
    /**
     * 模板ID
     */
    private static String templateId;

    /**
     * 天行数据apiKey
     */
    private static String flatterKey;

    /**
     * 是否使用农历计算生日
     */
    private static boolean useLunar;

    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        PushConfigBean.appId = appId;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        PushConfigBean.secret = secret;
    }

    public static String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        PushConfigBean.district_id = district_id;
    }

    public static String getMapAk() {
        return mapAk;
    }

    public void setMapAk(String mapAk) {
        PushConfigBean.mapAk = mapAk;
    }

    public static String getLoveDate() {
        return loveDate;
    }

    public void setLoveDate(String loveDate) {
        PushConfigBean.loveDate = loveDate;
    }

    public static String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        PushConfigBean.birthday = birthday;
    }

    public static List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        PushConfigBean.userId = userId;
    }

    public static String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        PushConfigBean.templateId = templateId;
    }

    public static String getFlatterKey() {
        return flatterKey;
    }

    public void setFlatterKey(String flatterKey) {
        PushConfigBean.flatterKey = flatterKey;
    }

    public static boolean isUseLunar() {
        return useLunar;
    }

    public void setUseLunar(boolean useLunar) {
        PushConfigBean.useLunar = useLunar;
    }
}
