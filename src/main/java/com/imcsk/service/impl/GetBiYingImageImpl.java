package com.imcsk.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.BiYingImageBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.IGetBiYingImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 获取必应每日壁纸数据
 * @Author csk
 * @Date 2022/10/16
 */
@Service
public class GetBiYingImageImpl implements IGetBiYingImageService {
    @Override
    public ResultBean getImage() {
        ResultBean resultBean = new ResultBean();

        Map<String, String> map = new HashMap<>();
        final String BASEURL = "https://cn.bing.com";
        final String URL = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";

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

        JSONArray images = jsonObject.getJSONArray("images");
        String arrObj = jsonObject.getString("images");

        System.out.println(arrObj);
//        List<BiYingImageBean> imageBean = images.toJavaList(BiYingImageBean.class);
        List<BiYingImageBean> imageBean = JSON.parseArray(arrObj,BiYingImageBean.class);
        BiYingImageBean image = imageBean.get(0);

        System.out.println(BASEURL + image.getUrl());
        image.setImgUrl(BASEURL + image.getUrl());

        resultBean.setCode("0");
        resultBean.setData(image);
        return resultBean;
    }
}
