package com.imcsk.controller;

import com.imcsk.entity.ResultBean;
import com.imcsk.service.IFlatterService;
import com.imcsk.service.IPushService;
import com.imcsk.service.IWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 消息推送Controller
 * @Author csk
 * @Date 2022/8/31
 */
@RestController
public class PushController {

    @Autowired
    private IFlatterService iFlatterService;

    @Autowired
    private IWeatherService iWeatherService;

    @Autowired
    private IPushService iPushService;

    @RequestMapping("getFlatterTest")
    public ResultBean getFlatterTest(){
        return iFlatterService.getFlatter();
    }

    @RequestMapping("getWeatherTest")
    public ResultBean getWeatherTest(){
        return iWeatherService.getWeather();
    }

    @RequestMapping("pushTest")
    public String push(){
        return iPushService.push();
    }
}
