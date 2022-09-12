package com.imcsk.service.impl;

import com.imcsk.entity.LunarCalendarFestivalBean;
import com.imcsk.entity.PushConfigBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.entity.WeatherBean;
import com.imcsk.service.IFlatterService;
import com.imcsk.service.IPushService;
import com.imcsk.service.IWeatherService;
import com.imcsk.utils.LunarCalendarFestivalUtil;
import com.imcsk.utils.MemoryDayUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description 推送消息业务实现类
 * @Author csk
 * @Date 2022/9/1
 */
@Service
public class PushServiceImpl implements IPushService {

    private static WxMpTemplateMsgService wxService = null;

    @Autowired
    private IFlatterService iFlatterService;

    @Autowired
    private IWeatherService iWeatherService;

    /***
     * @Description: 消息推送
     * @Author csk
     * @Date: 2022/9/11
     */
    @Override
    public String push() {
        // 构建模板消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().templateId(PushConfigBean.getTemplateId()).build();

        // 计算纪念日天数
        long loveDay = MemoryDayUtil.calculationLianAi(PushConfigBean.getLoveDate());
        // 计算生日天数
        long birthday = 0;
        if(PushConfigBean.isUseLunar()){
            // 使用农历生日
            birthday = MemoryDayUtil.calculationBirthdayByLunar(PushConfigBean.getBirthday());
        }else {
            // 使用阳历生日
            birthday = MemoryDayUtil.calculationBirthday(PushConfigBean.getBirthday());
        }
        // 向模板消息添加纪念日
        templateMessage.addData(new WxMpTemplateData("loveDays",loveDay + "", "#FF1493"));
        // 向模板消息添加生日
        templateMessage.addData(new WxMpTemplateData("birthdays",birthday + "","#FFA500"));

        // 获取天气数据
        ResultBean weatherResult = iWeatherService.getWeather();
        StringBuilder messageAll = new StringBuilder();
        if(!"0".equals(weatherResult.getCode())){
            // 获取天气数据失败
            messageAll.append("<br/>");
            messageAll.append(weatherResult.getMessage());
            templateMessage.addData(new WxMpTemplateData("weather", "***", "#00FFFF"));
        }else {
            WeatherBean weatherBean = (WeatherBean) weatherResult.getData();
            // 获取农历日期
            LunarCalendarFestivalUtil festival = new LunarCalendarFestivalUtil();
            LunarCalendarFestivalBean festivalBean = new LunarCalendarFestivalBean();
            festival.initLunarCalendarInfo(weatherBean.getDate(),festivalBean);

            templateMessage.addData(new WxMpTemplateData("date", weatherBean.getDate() + "  " + weatherBean.getWeek(), "#00BFFF"));
            templateMessage.addData(new WxMpTemplateData("lunar", "农历" + festivalBean.getLunarYear() + "年 " + festivalBean.getLunarMonth() + "月" + festivalBean.getLunarDay(), "#00BFFF"));
            templateMessage.addData(new WxMpTemplateData("festival", festivalBean.getLunarTerm() + " " + festivalBean.getSolarFestival() + " " + festivalBean.getLunarFestival(), "#00BFFF"));
            templateMessage.addData(new WxMpTemplateData("weather", weatherBean.getText_now(), "#00FFFF"));
            templateMessage.addData(new WxMpTemplateData("low", weatherBean.getLow() + "", "#173177"));
            templateMessage.addData(new WxMpTemplateData("temp", weatherBean.getTemp() + "", "#EE212D"));
            templateMessage.addData(new WxMpTemplateData("wc_day", weatherBean.getWc_day() + "", "#EE212D"));
            templateMessage.addData(new WxMpTemplateData("wd_day", weatherBean.getWd_day() + "", "#EE212D"));
            templateMessage.addData(new WxMpTemplateData("high", weatherBean.getHigh() + "", "#FF6347"));
            templateMessage.addData(new WxMpTemplateData("city", weatherBean.getCityName() + "", "#173177"));
        }

        // 获取天行API数据
        ResultBean flatterResult = iFlatterService.getFlatter();
        if (!"200".equals(flatterResult.getCode())){
            // 获取数据失败
            messageAll.append("<br/>");
            messageAll.append(flatterResult.getMessage());
        }else {
            templateMessage.addData(new WxMpTemplateData("rainbow", (String) flatterResult.getData(), "#FF69B4"));
        }

        // 备注
        String remark = "❤";
        if (loveDay % 365 == 0) {
            remark = "\n今天是恋爱" + (loveDay / 365) + "周年纪念日!";
        }
        if (birthday == 0) {
            remark = "\n今天是生日,生日快乐呀!";
        }
        if (loveDay % 365 == 0 && birthday == 0) {
            remark = "\n今天是生日,也是恋爱" + (loveDay / 365) + "周年纪念日!";
        }
        templateMessage.addData(new WxMpTemplateData("remark", remark, "#FF1493"));

        WxMpTemplateMsgService service = getService();
        int success = 0;
        int error = 0;
        for (String userId: PushConfigBean.getUserId()) {
            templateMessage.setToUser(userId);
            try {
                service.sendTemplateMsg(templateMessage);
                success += 1;
            }catch (WxErrorException e){
                error += 1;
                messageAll.append(success).append("个成功!");
                messageAll.append(error).append("个失败!");
                messageAll.append("<br/>");
                messageAll.append(e.getMessage());
                return "推送结果:" + messageAll;
            }
        }
        return "成功推送给" + success + "个用户!" + messageAll;
    }

    @Override
    public WxMpTemplateMsgService getService() {
        if(wxService != null){
            return wxService;
        }
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(PushConfigBean.getAppId());
        wxStorage.setSecret(PushConfigBean.getSecret());

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        wxService = wxMpService.getTemplateMsgService();

        return wxService;
    }
}
