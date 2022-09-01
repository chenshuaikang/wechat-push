package com.imcsk.service.impl;

import com.imcsk.service.IPushService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import org.springframework.stereotype.Service;

/**
 * @Description 推送消息业务实现类
 * @Author csk
 * @Date 2022/9/1
 */
@Service
public class PushServiceImpl implements IPushService {
    @Override
    public String push() {
        return null;
    }

    @Override
    public WxMpTemplateMsgService gerService() {
        return null;
    }
}
