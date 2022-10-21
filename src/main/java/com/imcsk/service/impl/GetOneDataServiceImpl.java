package com.imcsk.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.*;
import com.imcsk.service.IGetOneDataService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Description 获取one一个数据
 * @Author csk
 * @Date 2022/10/18
 */
@Service
public class GetOneDataServiceImpl implements IGetOneDataService {
    @Override
    public ResultBean getOneData() {
        final String URL = "http://api.tianapi.com/one/index?key=" + PushConfigBean.getFlatterKey()+"&rand=1";

        ResultBean resultBean = new ResultBean();

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(URL, String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);

        // 检查是否调通接口
        if (jsonObject == null) {
            // 接口地址有误或者接口没调通
            resultBean.setCode("500");
            resultBean.setMessage("无法获取相关数据，请稍后重试！建议检查天行数据接口服务是否正常");
            return resultBean;
        }

        // 检查接口状态码是否为200
        String code = jsonObject.getString("code");
        if (!"200".equals(code)) {
            String msg = jsonObject.getString("msg");
            resultBean.setCode(code);
            resultBean.setMessage("天行one数据接口调用报错:" + msg);
            return resultBean;
        }

        // 组装返回数据
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String arrObj = jsonObject.getString("newslist");

        List<OneDataBean> oneDataList = newslist.toJavaList(OneDataBean.class);
        System.out.println(arrObj);
//        List<WeatherBean> weathers = forecasts.toJavaList(WeatherBean.class);
        OneDataBean oneDataBean = oneDataList.get(0);

        resultBean.setCode(code);
        resultBean.setData(oneDataBean);

        return resultBean;
    }

}
