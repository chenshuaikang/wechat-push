package com.imcsk.service.impl;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.PushConfigBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.IGetAccessTokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 企业微信获取AccessToken（调用接口凭证）
 * @Author csk
 * @Date 2022/10/15
 */
@Service
public class GetAccessTokenServiceImpl implements IGetAccessTokenService {

    public static final String REDIS_WX_TOKEN = "ACCESS_TOKEN";

    @Override
    public ResultBean getAccessToken() {
        ResultBean resultBean = new ResultBean();
        Map<String, String> map = new HashMap<>();
        Jedis jedis = RedisDS.create().getJedis();
        String access_token = "";

        // 优先从Redis中获取缓存数据
        access_token = jedis.get(REDIS_WX_TOKEN+PushConfigBean.getCorpId());

        if (!access_token.isEmpty()){
            resultBean.setCode("200");
            resultBean.setMessage("sucess");
            resultBean.setData(access_token);
            return resultBean;
        }

        final String URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

        map.put("corpid", PushConfigBean.getCorpId());
        map.put("corpsecret", PushConfigBean.getCorpSecret());

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.getForObject(URL, String.class, map);
        JSONObject jsonObject = JSONObject.parseObject(result);

        // 检查是否调通接口
        if (jsonObject == null) {
            // 接口地址有误或者接口没调通
            resultBean.setCode("500");
            resultBean.setMessage("无法获取相关数据，请稍后重试！");
            return resultBean;
        }

        // 获取接口状态
        String errcode = jsonObject.getString("errcode");
        if (!"0".equals(errcode)) {
            String err = jsonObject.getString("errmsg");
            resultBean.setCode(errcode);
            resultBean.setMessage("企业微信获取AccessToken接口调用报错:" + err);
            return resultBean;
        }

        access_token = jsonObject.getString("access_token");

        System.out.println(access_token);

        resultBean.setCode(jsonObject.getString("errcode"));
        resultBean.setMessage(jsonObject.getString("errmsg"));
        resultBean.setData(access_token);

        String expTime = jsonObject.getString("expires_in");

        jedis.set(REDIS_WX_TOKEN+PushConfigBean.getCorpId(),access_token,new SetParams().px(Long.parseLong(expTime)));

        return resultBean;
    }
}
