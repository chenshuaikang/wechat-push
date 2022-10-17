package com.imcsk.controller;

import com.imcsk.entity.ResultBean;
import com.imcsk.service.*;
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

    @Autowired
    private IGetAccessTokenService iGetAccessTokenService;

    @Autowired
    private IWxCpSendMsgSerivce iWxCpSendMsgSerivce;

    @Autowired
    private IGetBiYingImageService iGetBiYingImageService;

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

    @RequestMapping("getAccessToken")
    public ResultBean getAccessToken(){
        return iGetAccessTokenService.getAccessToken();
    }

    @RequestMapping("sendMsgTest")
    public ResultBean sendMsgTest(){
        return iWxCpSendMsgSerivce.sendWxCpMsg();
    }

    @RequestMapping("getBiYingImageTest")
    public ResultBean getBiYingImage(){
        return iGetBiYingImageService.getImage();
    }
}
