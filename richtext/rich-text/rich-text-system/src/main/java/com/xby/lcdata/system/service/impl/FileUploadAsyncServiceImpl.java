package com.xby.lcdata.system.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.UUIDUtil;
import com.tb.base.common.constants.Constants;
import com.xby.lcdata.system.service.FileUploadAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@AsyncServiceHandler
public class FileUploadAsyncServiceImpl implements FileUploadAsyncService, BaseAsyncService {


    private final String ACCESS_KEY = "-1Fx0Sqcw9lmRxVfjsdASS3_ltYi4AQgHPsEbUQB";
    private final String SECRET_KEY = "2A3s04dg6f2EsujxeKGSo-svoNTvznyFzO32lXD1";
    private final String BUCKET = "cdxyh";
    private final String ACCESS_DOMAIN = "http://cdxyh.stickeronline.cn/";

    /**
     * OSS保存文件
     *
     * @param files
     * @param resultHandler
     */
    @Override
    public void saveFile(JsonArray files, Handler<AsyncResult<JsonArray>> resultHandler) {
        Future<JsonArray> future = Future.future();
//        Configuration cfg = new Configuration(Region.region0());
        Configuration cfg = new Configuration(Zone.zone2());
        Configuration.defaultApiHost = ACCESS_DOMAIN;
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);
        if (files.size() > 0) {
            JsonArray urls = new JsonArray();
            files.forEach(item -> {
                JsonObject json = (JsonObject) item;
                String uploadedFileName = json.getString("uploadedFileName");
                try {
                    Response response = uploadManager.put(uploadedFileName, null, upToken);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    System.out.println(putRet.key);
                    System.out.println(putRet.hash);
                    String url = ACCESS_DOMAIN + putRet.key;
                    JsonObject fileJson = json;
                    fileJson.put("url", url);
                    new File(uploadedFileName).delete();
                    urls.add(fileJson);
                } catch (QiniuException e) {
                    System.out.println(e.response.error);
                }
            });
            future.complete(urls);
        } else {
            future.fail("文件不存在!");
        }
        resultHandler.handle(future);
    }

}
