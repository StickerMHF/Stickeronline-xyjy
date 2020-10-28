package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BMomentsCommentEntity;
import com.tb.service.cdxyh.entity.BMomentsEntity;
import com.tb.service.cdxyh.entity.BMomentsLikeEntity;
import com.tb.service.cdxyh.repository.BMomentsCommentRepository;
import com.tb.service.cdxyh.repository.BMomentsLikeRepository;
import com.tb.service.cdxyh.repository.BMomentsRepository;
import com.tb.service.cdxyh.service.BMomentsService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BMomentsServiceImpl implements BMomentsService, BaseAsyncService {
    @Autowired
    private BMomentsRepository bMomentsRepository;
    @Autowired
    private BMomentsCommentRepository bMomentsCommentRepository;
    @Autowired
    private BMomentsLikeRepository bMomentsLikeRepository;

    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BMomentsEntity bMomentsEntity = new BMomentsEntity(params);
        bMomentsEntity.setCreateTime(new Date());
        BMomentsEntity save = bMomentsRepository.save(bMomentsEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BMomentsEntity bMomentsEntity = new BMomentsEntity();
        String likeCount = params.getString("order");
        Sort sort;
        if (likeCount != null&&!likeCount.isEmpty()){
            sort =new Sort(Sort.Direction.DESC, likeCount);
        } else {
            sort = new Sort(Sort.Direction.DESC, "createTime");
        }
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        //创建实例
        Example<BMomentsEntity> ex = Example.of(bMomentsEntity);
        Page<BMomentsEntity> plist = bMomentsRepository.findAll(ex,pageable);
        JsonObject resObj = new JsonObject(Json.encode(plist));
        JsonArray contents = resObj.getJsonArray("content");
        for (int i = 0; i < contents.size(); i++) {
            JsonObject content = contents.getJsonObject(i);
            String commentId = content.getString("id");
            //统计浏览量
            Integer viewCount = content.getInteger("viewCount");
            content.put("viewCount",viewCount+1);
            bMomentsRepository.save(new BMomentsEntity(content));
            //获取评论信息
            List<BMomentsCommentEntity> commentList = bMomentsCommentRepository.queryByCommentId(commentId);
            resObj.getJsonArray("content").getJsonObject(i).put("commentList", new JsonArray(Json.encode(commentList)));
            //获取当前用户点赞状态
            List<BMomentsLikeEntity> likeList = bMomentsLikeRepository.findAllByUserIdAndMomentId(params.getString("userId"),commentId);
            if(likeList.size()>0){
                resObj.getJsonArray("content").getJsonObject(i).put("status", likeList.get(0).getStatus());
            }else {
                resObj.getJsonArray("content").getJsonObject(i).put("status", "unlike");
            }
        }
        future.complete(resObj);
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {

    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
//        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        BMomentsEntity bMomentsEntity = new BMomentsEntity(params);
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BMomentsEntity> ex = Example.of(bMomentsEntity);
        List<BMomentsEntity> newsList = bMomentsRepository.findAll(ex);
        JsonArray resList = new JsonArray(Json.encode(newsList));
       for (int i = 0; i < resList.size(); i++) {
            String commentId = resList.getJsonObject(i).getString("id");
            List<BMomentsCommentEntity> commentList = bMomentsCommentRepository.queryByCommentId(commentId);
            System.out.println(i);
           resList.getJsonObject(i).put("commentList", commentList);
        }
       future.complete(resList);
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler){
        Future<JsonObject> future = Future.future();
        ExampleMatcher matcher = ExampleMatcher.matching();
        BMomentsEntity bAlummunsMemberEntity = new BMomentsEntity(params);
        Optional<BMomentsEntity> res = bMomentsRepository.findById(bAlummunsMemberEntity.getId());
        if (res.isPresent()){
            future.complete(new JsonObject(Json.encode(res.get())));
        } else {
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    @Override
    public void likeClick(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String type = params.getString("type");  //like unlike
        String id = params.getString("id");
        Optional<BMomentsEntity> res =  bMomentsRepository.findById(id);
        if (res.isPresent()){
            Integer likeCount = res.get().getLikeCount();
            BMomentsEntity bMomentsEntity = new BMomentsEntity();
            if (type.equals("like")){
                bMomentsEntity.setLikeCount(likeCount+1);
            } else if (type.equals("unlike") && likeCount<0){
                bMomentsEntity.setLikeCount(likeCount-1);
            }
            BMomentsEntity update = bMomentsRepository.save(bMomentsEntity);
            future.complete(new JsonObject(Json.encode(res.get())));
        } else {
            future.complete(new JsonObject());
        }

        handler.handle(future);
    }
}
