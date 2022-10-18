package com.imcsk.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.*;
import com.imcsk.service.*;
import com.imcsk.utils.LunarCalendarFestivalUtil;
import com.imcsk.utils.MemoryDayUtil;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 企业微信消息推送
 * @Author csk
 * @Date 2022/10/15
 */
@Service
public class WxCpSendMsgImpl implements IWxCpSendMsgSerivce {
    @Autowired
    private IFlatterService iFlatterService;

    @Autowired
    private IWeatherService iWeatherService;

    @Autowired
    private IGetBiYingImageService iGetBiYingImageService;

    @Autowired
    private IGetAccessTokenService iGetAccessTokenService;

    @Autowired
    private ICovidDataQueryService iCovidDataQueryService;

    @Override
    public ResultBean sendWxCpMsg() {
        /**
         * 注意使用时access_token目前没有做缓存，存在过期可能（有效期三个小时），
         * 过期时要用getAccessToken方法进行获取新的access_token
         */
        final String URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" +
                "uGNz_FMTOG93a7o8b0WNLf2PuTyMNjrC_ArOEJ6TNxwCfKRTjgHWdMZVGBNoEn7lDcSSDRI8BWjoC1gm367D7iYNga8OEMNW3SU2ihdkBAwreZ0a2ly2bs5PPlyQ5k9rKSCLU_Ajnl2h9V7bQ2xOc1ZR-jxmCgPxzu51SNfMkxkcdWtAbCpDwx74Txaie_lpYv5dJP-a6zkMqHNOua27Sg";

        ResultBean resultBean = new ResultBean();

        // 请求头
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String,Object> data = new HashMap<String,Object>();

        data.put("touser",PushConfigBean.getToUser());
        data.put("msgtype","news");
        data.put("agentid",PushConfigBean.getAgentId());

        Map<String,Object> contentMap =new HashMap<>();

        JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();
        // 获取当前年月日
        LocalDate localDate = LocalDate.now();

        json.put("title",localDate+"  "+getWeekName(getWeek(localDate))+"温馨推送");
        json.put("description",buildMsg());
        json.put("url","");
        json.put("picurl",getImageUrl());
        jsonArray.add(json);

        contentMap.put("articles", jsonArray);
//        contentMap.put("content","您的会议室已经预定，稍后会同步到邮箱" +
//                "                                >**事项详情** \n" +
//                "                                >事　项：<font color=\\\"info\\\">开会</font> \n" +
//                "                                >组织者：@miglioguan \n" +
//                "                                >参与者：@miglioguan、@kunliu、@jamdeezhou、@kanexiong、@kisonwang \n" +
//                "                                > \n" +
//                "                                >会议室：<font color=\\\"info\\\">广州TIT 1楼 301</font> \n" +
//                "                                >日　期：<font color=\\\"warning\\\">2018年5月18日</font> \n" +
//                "                                >时　间：<font color=\\\"comment\\\">上午9:00-11:00</font> \n" +
//                "                                > \n" +
//                "                                >请准时参加会议。 \n" +
//                "                                > \n" +
//                "                                >如需修改会议信息，请点击：[修改会议信息](https://work.weixin.qq.com)");

        data.put("news", contentMap);
//        data.put("markdown",contentMap);
        System.out.println(JSONUtil.toJsonStr(data));

        String result = HttpUtil.createPost(URL)
                .headerMap(headers,false)
                .body(JSONUtil.toJsonStr(data)).execute().body();

        JSONObject jsonObject = JSONObject.parseObject(result);

        System.out.println(jsonObject);

        // 检查是否调通接口
        if (jsonObject == null) {
            // 接口地址有误或者接口没调通
            resultBean.setCode("500");
            resultBean.setMessage("无法获取企业微信推送相关数据，请稍后重试！");
            return resultBean;
        }

        // 获取接口状态
        String code = jsonObject.getString("errcode");
        if (!"0".equals(code)) {
            String err = jsonObject.getString("errmsg");
            resultBean.setCode(code);
            resultBean.setMessage("企业微信推送相关数据报错:" + err);
            return resultBean;
        }

        resultBean.setCode(code);
        resultBean.setMessage(jsonObject.getString("errmsg"));

        return resultBean;
    }

    public String buildMsg(){
        StringBuilder sb = new StringBuilder();
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
        ResultBean weatherResult = iWeatherService.getWeather();

        if(!"0".equals(weatherResult.getCode())){
            // 获取天气数据失败
            sb.append("<br/>");
            sb.append(weatherResult.getMessage());
        }else {
            WeatherBean weatherBean = (WeatherBean) weatherResult.getData();
            // 获取农历日期
            LunarCalendarFestivalUtil festival = new LunarCalendarFestivalUtil();
            LunarCalendarFestivalBean festivalBean = new LunarCalendarFestivalBean();
            festival.initLunarCalendarInfo(weatherBean.getDate(),festivalBean);

            sb.append("📅今天是农历" + festivalBean.getLunarYear() + "年 " + festivalBean.getLunarMonth() + "月" + festivalBean.getLunarDay()+"\n\r");
            sb.append("❤今天是我们恋爱的第"+loveDay+"天,距离宝宝的生日还有"+birthday+"天\n\r");
            sb.append(""+weatherBean.getCityName()+"的天气："+weatherBean.getText_now()+" \n最低气温: "+weatherBean.getLow()+"度 \n最高气温: "+weatherBean.getHigh()+"度 \n风力: "+weatherBean.getWc_day()+" \n风向: "+weatherBean.getWd_day()+"\n\r");
        }

        ResultBean covidResult = iCovidDataQueryService.getCovidData();
        if (!"1".equals(covidResult.getCode())) {
            // 获取COVID-19数据失败
            sb.append(covidResult.getMessage());
        }else {
            CovidDataBean covidDataBean = (CovidDataBean) covidResult.getData();
            sb.append(covidDataBean.getCity()+"的疫情报告：\n");
//            sb.append("🆕新增确诊："covidDataBean);
            sb.append("🆕新增本土："+covidDataBean.getSure_new_loc()+"例\n");
            sb.append("🆕新增本土无症状："+covidDataBean.getSure_new_hid()+"例\n");
            sb.append("\uD83D\uDE37 现有确诊："+covidDataBean.getPresent()+"例\n");
            sb.append("\uD83D\uDE37 累计确诊："+covidDataBean.getSure_cnt()+"例\n");
            sb.append("⛔️高风险地区："+covidDataBean.getHcount()+"个\n");
            sb.append("⚠️中风险地区："+covidDataBean.getMcount()+"个\n");
            sb.append(covidDataBean.getTime());
            sb.append("\n\r");
        }


        // 获取天行API数据
        ResultBean flatterResult = iFlatterService.getFlatter();
        if (!"200".equals(flatterResult.getCode())){
            // 获取数据失败
            sb.append(flatterResult.getMessage());
        }else {
            sb.append(flatterResult.getData());
        }
        return sb.toString();
    }

    /**
     * 获得指定日期的星期
     * @param localDate
     * @return int
     */
    public static int getWeek(LocalDate localDate){
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 获得星期名称
     * @param value 星期数值
     * @return String
     */
    public static String getWeekName(int value){
        String week="";
        switch (value){
            case 1:
                week="星期一";
                break;
            case 2:
                week="星期二";
                break;
            case 3:
                week="星期三";
                break;
            case 4:
                week="星期四";
                break;
            case 5:
                week="星期五";
                break;
            case 6:
                week="星期六";
                break;
            case 7:
                week="星期日";
                break;
            default:
                break;
        }
        return week;
    }

    /**
     * @Description: 获取必应图片链接
     * @Return: 图片链接
     * @Author csk
     * @Date: 2022/10/17
     */
    public String getImageUrl(){
        ResultBean bean = iGetBiYingImageService.getImage();
        BiYingImageBean imgBean = (BiYingImageBean) bean.getData();
        return imgBean.getImgUrl();
    }

    public String getAccessToken(){
        ResultBean bean = iGetAccessTokenService.getAccessToken();

        return bean.getData().toString();
    }
}
