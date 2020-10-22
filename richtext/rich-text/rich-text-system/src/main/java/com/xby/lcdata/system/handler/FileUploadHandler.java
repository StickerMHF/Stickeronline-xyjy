package com.xby.lcdata.system.handler;

import com.sticker.online.core.anno.RouteHandler;
import com.sticker.online.core.anno.RouteMapping;
import com.sticker.online.core.anno.RouteMethod;
import com.sticker.online.core.model.ReplyObj;
import com.sticker.online.core.utils.AsyncServiceUtil;
import com.sticker.online.core.utils.HttpUtil;
import com.xby.lcdata.system.service.FileUploadAsyncService;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import static java.net.HttpURLConnection.HTTP_OK;

@RouteHandler("file")
public class FileUploadHandler {

    private FileUploadAsyncService fileStoreAsyncService =
            AsyncServiceUtil.getAsyncServiceInstance(FileUploadAsyncService.class);

    @RouteMapping(value = "/upload", method = RouteMethod.POST, order = 1)
    public Handler<RoutingContext> fileUpload() {
        return ctx -> {
            JsonArray files = new JsonArray();
            ctx.fileUploads().forEach(res -> {
                JsonObject file = new JsonObject();
                file.put("charSet", res.charSet());
                file.put("contentTransferEncoding", res.contentTransferEncoding());
                file.put("contentType", res.contentType());
                file.put("fileName", res.fileName());
                file.put("name", res.name());
                file.put("size", res.size());
                file.put("uploadedFileName", res.uploadedFileName());
                files.add(file);
            });
            fileStoreAsyncService.saveFile(files, res -> {
                if (res.succeeded()) {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setMsg("success").setCode(200).setSuccess(true).setResult(res.result()));
                } else {
                    HttpUtil.fireJsonResponse(ctx.response(), HTTP_OK,
                            ReplyObj.build().setMsg(res.cause().getMessage()).setCode(500).setSuccess(false));
                }
            });
        };
    }

}
