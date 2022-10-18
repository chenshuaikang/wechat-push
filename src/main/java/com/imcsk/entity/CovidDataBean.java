package com.imcsk.entity;

/**
 * @Description 疫情数据
 * @Author csk
 * @Date 2022/10/15
 */
public class CovidDataBean {
    private String id;
    // 累计确诊
    private String sure_cnt;
    // 累计治愈
    private String cure_cnt;
    // 累计死亡
    private String die_cnt;
    // 现有确诊
    private String present;
    // 新增确诊
    private String sure_new_cnt;
    // 新增本土
    private String sure_new_loc;
    // 新增本土无症状
    private String sure_new_hid;
    // 更新时间
    private String updatetime;
    // 数据统计截止时间
    private String time;

    private String sure_nzd_cnt;
    private String sure_new_zero_days;
    private String local_incr_zero_days;
    private String incr_yst;
    private String continent;
    private String population;
    private String hid_cnt;
    private String incomplete;
    private String unqiue;
    private String name;
    private String today_local_sure_new_cnt;
    private String today_local_hidden_new_cnt;

    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;

    private String like_cnt;

    private String hcount;
    private String mcount;

    public String getSure_cnt() {
        return sure_cnt;
    }

    public void setSure_cnt(String sure_cnt) {
        this.sure_cnt = sure_cnt;
    }

    public String getCure_cnt() {
        return cure_cnt;
    }

    public void setCure_cnt(String cure_cnt) {
        this.cure_cnt = cure_cnt;
    }

    public String getDie_cnt() {
        return die_cnt;
    }

    public void setDie_cnt(String die_cnt) {
        this.die_cnt = die_cnt;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getSure_new_cnt() {
        return sure_new_cnt;
    }

    public void setSure_new_cnt(String sure_new_cnt) {
        this.sure_new_cnt = sure_new_cnt;
    }

    public String getSure_new_loc() {
        return sure_new_loc;
    }

    public void setSure_new_loc(String sure_new_loc) {
        this.sure_new_loc = sure_new_loc;
    }

    public String getSure_new_hid() {
        return sure_new_hid;
    }

    public void setSure_new_hid(String sure_new_hid) {
        this.sure_new_hid = sure_new_hid;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSure_nzd_cnt() {
        return sure_nzd_cnt;
    }

    public void setSure_nzd_cnt(String sure_nzd_cnt) {
        this.sure_nzd_cnt = sure_nzd_cnt;
    }

    public String getSure_new_zero_days() {
        return sure_new_zero_days;
    }

    public void setSure_new_zero_days(String sure_new_zero_days) {
        this.sure_new_zero_days = sure_new_zero_days;
    }

    public String getLocal_incr_zero_days() {
        return local_incr_zero_days;
    }

    public void setLocal_incr_zero_days(String local_incr_zero_days) {
        this.local_incr_zero_days = local_incr_zero_days;
    }

    public String getIncr_yst() {
        return incr_yst;
    }

    public void setIncr_yst(String incr_yst) {
        this.incr_yst = incr_yst;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getHid_cnt() {
        return hid_cnt;
    }

    public void setHid_cnt(String hid_cnt) {
        this.hid_cnt = hid_cnt;
    }

    public String getIncomplete() {
        return incomplete;
    }

    public void setIncomplete(String incomplete) {
        this.incomplete = incomplete;
    }

    public String getUnqiue() {
        return unqiue;
    }

    public void setUnqiue(String unqiue) {
        this.unqiue = unqiue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToday_local_sure_new_cnt() {
        return today_local_sure_new_cnt;
    }

    public void setToday_local_sure_new_cnt(String today_local_sure_new_cnt) {
        this.today_local_sure_new_cnt = today_local_sure_new_cnt;
    }

    public String getToday_local_hidden_new_cnt() {
        return today_local_hidden_new_cnt;
    }

    public void setToday_local_hidden_new_cnt(String today_local_hidden_new_cnt) {
        this.today_local_hidden_new_cnt = today_local_hidden_new_cnt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(String like_cnt) {
        this.like_cnt = like_cnt;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
