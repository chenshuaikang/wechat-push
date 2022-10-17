package com.imcsk.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.PushConfigBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.entity.WeatherBean;
import com.imcsk.service.IWeatherService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 获取天气相关信息实现类
 * @Author csk
 * @Date 2022/9/1
 * 百度接口文档地址：https://lbs.baidu.com/index.php?title=webapi/weather
 */
@Service
public class WeatherServiceImpl implements IWeatherService {
    @Override
    public ResultBean getWeather() {
        ResultBean resultBean = new ResultBean();

        Map<String, String> map = new HashMap<>();

        final String URL = "https://api.map.baidu.com/weather/v1/?district_id={districtId}&data_type=all&ak={ak}";

        map.put("districtId", PushConfigBean.getDistrict_id());
        map.put("ak", PushConfigBean.getMapAk());

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(URL, String.class, map);
        JSONObject jsonObject = JSONObject.parseObject(result);

        // 检查是否调通接口
        if (jsonObject == null) {
            // 接口地址有误或者接口没调通
            resultBean.setCode("500");
            resultBean.setMessage("无法获取相关数据，请稍后重试！建议检查百度天气数据接口服务是否正常");
            return resultBean;
        }

        // 获取接口状态
        String status = jsonObject.getString("status");
        if (!"0".equals(status)) {
            String err = jsonObject.getString("message");
            resultBean.setCode(status);
            resultBean.setMessage("百度天气数据接口调用报错:" + err);
            return resultBean;
        }

        JSONArray forecasts = jsonObject.getJSONObject("result").getJSONArray("forecasts");
        System.out.println(forecasts);
        List<WeatherBean> weathers = forecasts.toJavaList(WeatherBean.class);
        WeatherBean weather = weathers.get(0);
        JSONObject now = jsonObject.getJSONObject("result").getJSONObject("now");
        JSONObject location = jsonObject.getJSONObject("result").getJSONObject("location");

        // 当前天气
        weather.setText_now(now.getString("text"));
        // 当前温度
        weather.setTemp(now.getString("temp"));
        // 当前城市
        weather.setCity(location.getString("city"));
        // 当前城市（具体到县）
        weather.setCityName(location.getString("name"));

        resultBean.setCode(status);
        resultBean.setData(weather);

        return resultBean;
    }
}
