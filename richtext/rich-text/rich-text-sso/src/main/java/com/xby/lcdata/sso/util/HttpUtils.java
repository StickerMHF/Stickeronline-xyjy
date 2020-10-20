package com.xby.lcdata.sso.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @ClassName: HttpUtils
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/12 0012 14:38
 * @Version: 1.0
 **/
public class HttpUtils {

    /**
     * 发送HttpGet请求
     *
     * @param params
     * @param url
     * @return
     */
    public static JSONObject sendGet(Map<String, String> params, String url) {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = null;
        CloseableHttpResponse response = null;
        String data = "";
        try {
            uriBuilder = new URIBuilder(url);
            if (null != params) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            response = client.execute(new HttpGet(uriBuilder.build()));
            data = EntityUtils.toString(response.getEntity());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject result = JSONObject.parseObject(data);
        return result;
    }

    public static JSONObject sendPost() {
        String data = "";
        JSONObject result = JSONObject.parseObject(data);
        return result;
    }

}
