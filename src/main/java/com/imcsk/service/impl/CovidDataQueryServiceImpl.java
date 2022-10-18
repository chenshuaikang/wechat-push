package com.imcsk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.CovidDataBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.ICovidDataQueryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 获取疫情相关数据
 * @Author csk
 * @Date 2022/10/15
 */
@Service
public class CovidDataQueryServiceImpl implements ICovidDataQueryService {
    @Override
    public ResultBean getCovidData() {
        final String URL = "https://covid.myquark.cn/quark/covid/data/index_data?format=json&method=Huoshenshan.ncov2022&city=淮安";
        ResultBean resultBean = new ResultBean();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(URL, String.class);

        JSONObject jsonObject = JSONObject.parseObject(result);

        System.out.println(jsonObject);

        // 检查是否调通接口
        if (jsonObject == null) {
            // 接口地址有误或者接口没调通
            resultBean.setCode("500");
            resultBean.setMessage("无法获取COVID-19相关数据，请稍后重试");
            return resultBean;
        }

        // 获取接口状态
        String code = jsonObject.getString("code");
        if (!"1".equals(code)) {
            String err = jsonObject.getString("msg");
            resultBean.setCode(code);
            resultBean.setMessage("获取COVID-19相关数据报错:" + err);
            return resultBean;
        }
        JSONObject data = jsonObject.getJSONObject("data").getJSONObject("cityData");
        System.out.println(data);
        CovidDataBean covidDataBean = JSONObject.toJavaObject(data,CovidDataBean.class);
        // 中风险地区个数
        covidDataBean.setMcount(data.getJSONObject("danger").getString("1"));
        // 高风险地区个数
        covidDataBean.setHcount(data.getJSONObject("danger").getString("2"));
        // 数据统计截止时间
        covidDataBean.setTime(jsonObject.getJSONObject("data").getString("time"));

        resultBean.setCode(code);
        resultBean.setMessage(jsonObject.getString("msg"));
        resultBean.setData(covidDataBean);

        return resultBean;
    }

}
