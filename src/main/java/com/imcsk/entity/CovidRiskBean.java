package com.imcsk.entity;

import org.springframework.stereotype.Component;

/**
 * @Description COVID-19
 * @Author csk
 * @Date 2022/10/15
 */
@Component
public class CovidRiskBean {
    // 高风险地区个数
    private String hcount;
    // 中风险地区个数
    private String mcount;
    // 低风险地区个数
    private String lcount;
    // 数据最后更新时间
    private String end_update_time;

    public String getHcount() {
        return hcount;
    }

    public void setHcount(String hcount) {
        this.hcount = hcount;
    }

    public String getMcount() {
        return mcount;
    }

    public void setMcount(String mcount) {
        this.mcount = mcount;
    }

    public String getLcount() {
        return lcount;
    }

    public void setLcount(String lcount) {
        this.lcount = lcount;
    }

    public String getEnd_update_time() {
        return end_update_time;
    }

    public void setEnd_update_time(String end_update_time) {
        this.end_update_time = end_update_time;
    }
}
