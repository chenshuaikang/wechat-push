package com.imcsk.service;

import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;

public interface IPushService {

    String push();

    WxMpTemplateMsgService getService();

}
