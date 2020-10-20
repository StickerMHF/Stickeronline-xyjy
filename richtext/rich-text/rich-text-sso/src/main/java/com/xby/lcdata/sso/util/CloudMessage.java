package com.xby.lcdata.sso.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 发送短信验证码配置
 */
public class CloudMessage {

    //发送验证码的请求路径URL
    private static final String SERVER_URL = "https://api.netease.im/sms/sendcode.action";
    //应用的Appkey
    private static final String APP_KEY = "a5cd35ba61b83e84424ec0494e9ccc57";
    //应用的密钥appSecret
    private static final String APP_SECRET = "a08077f921c2";
    //随机数
    private static String NONCE;
    //短信模板ID
    private static String TEMPLATEID = "";
    //手机号
    private static String MOBILE;
    //验证码长度，默认为4
    private static final String CODELEN = "6";

    public static String sendCode(String phone, String temp, String messageType) throws Exception {
        TEMPLATEID = temp;
        NONCE = (new Random().nextInt(999999 - 100000) + 100000) + "";
        MOBILE = phone;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(SERVER_URL);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        /*
         * 计算CheckSum
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        //  判断是否为通知类短信
        if (messageType.equals("inform")) {
            /**
             * 发送通知类短信,且参数名称为:mobiles,手机号码为JsonArray类型,例如:['13888888888','13666666666']
             */
            nvps.add(new BasicNameValuePair("mobiles", MOBILE));
        }
        //  判断是否为验证码类短信
        if (messageType.equals("verificationCode")) {
            nvps.add(new BasicNameValuePair("mobile", MOBILE));
        }
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }
}
