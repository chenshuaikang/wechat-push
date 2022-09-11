package com.imcsk.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.PushConfigBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.IFlatterService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 获取彩虹屁相关数据
 * @Author csk
 * @Date 2022/9/1
 */
@Service
public class FlatterServiceImpl implements IFlatterService {

    @Override
    public ResultBean getFlatter() {
        final String URL = "http://api.tianapi.com/caihongpi/index?key=" + PushConfigBean.getFlatterKey();

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
            resultBean.setMessage("天行数据接口调用报错:" + msg);
            return resultBean;
        }

        // 组装返回数据
        /* 原始数据如下
            {
                "code": "200",
                "message": null,
                "data": "\n春水初生，春林初盛，春风十里，不如你。"
            }
         */
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String data = "\n" + newslist.getJSONObject(0).getString("content");
        resultBean.setCode(code);
        resultBean.setData(data);

        return resultBean;
    }
}
