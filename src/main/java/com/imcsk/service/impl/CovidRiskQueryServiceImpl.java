package com.imcsk.service.impl;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.imcsk.entity.CovidRiskBean;
import com.imcsk.entity.ResultBean;
import com.imcsk.service.ICovidRiskQueryService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description COVID-19风险地区查询
 * @Author csk
 * @Date 2022/9/13
 */
@Service
public class CovidRiskQueryServiceImpl implements ICovidRiskQueryService {
//    final String URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpid}&corpsecret={corpsecret}";
    final String URL = "http://bmfw.www.gov.cn/bjww/interface/interfaceJson";
//    final String URL = "http://103.66.32.242:8005/zwfwMovePortal/interface/interfaceJson";
    final String timestamp = getTimestampStr();

//    final String timestamp = "1665800536";
    public static String x_wif_nonce = "QkjjtiLM2dCratiA";
    public static String x_wif_paasid = "smt-application";

    public static String appId = "NcApplication";
    public static String paasHeader = "zdww";
    public static String nonceHeader = "123456789abcdefg";
    public static String key = "3C502C97ABDA40D0A60FBEE50FAAD1DA";

    public static String signature = "fTN2pfuisxTavbTuYVSsNJHetwq5bJvCQkjjtiLM2dCratiA";
    @Override
    public ResultBean getCovidRiskArea() {
        ResultBean resultBean = new ResultBean();


        Map<String,String> headers = new HashMap<>();
        headers.put("x-wif-nonce", x_wif_nonce);
        headers.put("x-wif-signature", get_x_wif_signature());
        headers.put("x-wif-timestamp", timestamp);
        headers.put("x-wif-paasid", x_wif_paasid);
        headers.put("Accept", "*/*");

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("appId",appId);
        data.put("paasHeader",paasHeader);
        data.put("timestampHeader",timestamp);
        data.put("nonceHeader",nonceHeader);
        data.put("signatureHeader",crypo_sha256());
        data.put("key",key);

        String result = HttpUtil.createPost(URL)
                .headerMap(headers,false)
                .body(JSONUtil.toJsonStr(data)).execute().body();

        JSONObject jsonObject = JSONObject.parseObject(result);
//        JSONObject hcount = jsonObject.getJSONObject("data");
//        String str = hcount.getString("hcount");
        // 接口返回码
        String code = jsonObject.getString("code");
        if (!"0".equals(code)) {
            String err = jsonObject.getString("msg");
            resultBean.setCode(code);
            resultBean.setMessage("获取COVID-19风险数据错误:" + err);
            return resultBean;
        }

        // 高风险地区个数
        String hcount = jsonObject.getJSONObject("data").getString("hcount");
        // 中风险地区个数
        String mcount = jsonObject.getJSONObject("data").getString("mcount");
        // 低风险地区个数
        String lcount = jsonObject.getJSONObject("data").getString("lcount");
        // 数据更新时间
        String end_update_time = jsonObject.getJSONObject("data").getString("end_cupdate_time");

        CovidRiskBean covidBean = new CovidRiskBean();
        covidBean.setHcount(hcount);
        covidBean.setMcount(mcount);
        covidBean.setLcount(lcount);
        covidBean.setEnd_update_time(end_update_time);

        resultBean.setCode(code);
        resultBean.setData(covidBean);

        return resultBean;
    }

    private String getTimestampStr(){
        return String.valueOf(System.currentTimeMillis()/1000L);
    }

    private String get_x_wif_signature(){
        MessageDigest messageDigest;
        String str = timestamp + signature + timestamp;
        String encodeStr = getSha256Str(str);

        return encodeStr;
    }

    private String crypo_sha256(){
        MessageDigest messageDigest;

        String e = timestamp;
        String a = "23y0ufFl5YxIyGrI8hWRUZmKkvtSjLQA";
        String i = "123456789abcdefg";
        String str = e + a + i + e;

        String encodeStr = getSha256Str(str);

        return encodeStr;
    }

    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * sha256加密
     *
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String getSha256Str(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
}
