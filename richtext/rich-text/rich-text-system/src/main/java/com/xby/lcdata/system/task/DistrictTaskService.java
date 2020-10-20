package com.xby.lcdata.system.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xby.lcdata.system.entity.SDistrictEntity;
import com.xby.lcdata.system.repository.DistrictRepository;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DistrictTaskService {

    @Autowired
    private DistrictRepository districtRepository;

//    @Scheduled(initialDelay = 5000, fixedDelay = 1000 * 60 * 60 * 240)
//    public void start() {
//        System.out.println("*****************************************开始获取行政区域数据*****************************************");
//        getLbsAmap();
//        System.out.println("*****************************************行政区域数据录入完成*****************************************");
//    }

    /**
     * 调用高德地图行政区域查询接口获取数据
     */
    public void getLbsAmap() {
        String uri = "https://restapi.amap.com/v3/config/district?keywords=中国&subdistrict=3&key" +
                "=3e83f3dd8a644858895faa73b3ad0336";
//        获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        创建一个Get请求
        HttpGet httpGet = new HttpGet(uri);
//        获取Response返回结果
        CloseableHttpResponse response = null;
        JSONArray jsonArray = null;
        try {
            response = httpClient.execute(httpGet);
//            将返回结果转换为字符串
            HttpEntity httpEntity = response.getEntity();
//            转换为JSON对象
            JSONObject result = JSON.parseObject(EntityUtils.toString(httpEntity));
//            获取省级行政区的JSON数组
            jsonArray = result.getJSONArray("districts");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                关闭HttpResponse
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        districtRepository.deleteAll();
        getDsitrict(jsonArray, "0");
    }

    /**
     * 递归遍历数据添加到数据库
     *
     * @param districts
     * @param superiorCode
     */
    public void getDsitrict(JSONArray districts, String superiorCode) {
        for (int i = 0; i < districts.size(); i++) {
            JSONObject item = districts.getJSONObject(i);
            saveDistrict(item.getString("adcode"), item.getString("name"), item.getString("level"),
                    item.getString("citycode"), superiorCode);
            if (item.getJSONArray("districts").size() > 0) {
                getDsitrict(item.getJSONArray("districts"), item.getString("adcode"));
            }
        }
    }

    public void saveDistrict(String adCode, String name, String level, String cityCode, String superiorCode) {
        SDistrictEntity sDistrictEntity = new SDistrictEntity();
        sDistrictEntity.setAdCode(adCode);
        sDistrictEntity.setName(name);
        sDistrictEntity.setLevel(level);
        sDistrictEntity.setCityCode(cityCode.equals("[]") ? "0" : cityCode);
        sDistrictEntity.setSuperiorCode(superiorCode);
        System.out.println("编码:" + adCode + "====名称:" + name + "====行政级别:" + level + "====城市编码:" + cityCode +
                "====上级行政区域代码:" + superiorCode);
        districtRepository.save(sDistrictEntity);
    }

}
