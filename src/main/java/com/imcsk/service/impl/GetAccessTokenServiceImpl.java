package com.imcsk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.PushConfigBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.IGetAccessTokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 企业微信获取AccessToken（调用接口凭证）
 * @Author csk
 * @Date 2022/10/15
 */
@Service
public class GetAccessTokenServiceImpl implements IGetAccessTokenService {

    @Override
    public ResultBean getAccessToken() {
        ResultBean resultBean = new ResultBean();
        Map<String, String> map = new HashMap<>();

        final String URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={ID}&corpsecret={SECRET}";

        map.put("ID", PushConfigBean.getCorpId());
        map.put("SECRET", PushConfigBean.getCorpSecret());

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
        String errcode = jsonObject.getString("errcode");
        if (!"0".equals(errcode)) {
            String err = jsonObject.getString("errmsg");
            resultBean.setCode(errcode);
            resultBean.setMessage("百度天气数据接口调用报错:" + err);
            return resultBean;
        }

        String access_token = jsonObject.getString("access_token");

        System.out.println(access_token);

        resultBean.setCode(jsonObject.getString("errcode"));
        resultBean.setMessage(jsonObject.getString("errmsg"));
        resultBean.setData(access_token);

        return resultBean;
    }
}
