package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusActivityEntity;
import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import com.tb.service.cdxyh.entity.BMessageEntity;
import com.tb.service.cdxyh.entity.BNewsEntity;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.repository.BMessageRepository;
import com.tb.service.cdxyh.service.BAlumnusJoinService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@AsyncServiceHandler
public class BAlumnusJoinServiceImpl implements BAlumnusJoinService, BaseAsyncService {
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Autowired
    private BMessageRepository bMessageRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        bAlumnusJoinEntity.setCreateTime(new Date());
        bAlumnusJoinEntity.setCreateBy(bAlumnusJoinEntity.getUserName());
        bAlumnusJoinEntity.setCheckState(0);
        bAlumnusJoinEntity.setPresident(0);
        BAlumnusJoinEntity save = bAlumnusJoinRepository.save(bAlumnusJoinEntity);
        future.complete(new JsonObject(Json.encode(save)));
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if (oConvertUtils.isNotEmpty(params.getString("president"))) {
            bAlumnusJoinEntity.setCheckState(Integer.parseInt(params.getString("president")));
            exampleMatcher.withMatcher("president", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if (oConvertUtils.isNotEmpty(params.getString("checkState"))) {
            bAlumnusJoinEntity.setCheckState(Integer.parseInt(params.getString("checkState")));
            exampleMatcher.withMatcher("checkState", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        Example<BAlumnusJoinEntity> ex = Example.of(bAlumnusJoinEntity, exampleMatcher);
        Page<BAlumnusJoinEntity> plist = bAlumnusJoinRepository.findAll(ex,pageable);

        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);

        Optional<BAlumnusJoinEntity> sr =bAlumnusJoinRepository.findById(bAlumnusJoinEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bAlumnusJoinEntity.setUpdateTime(new Date());
            bAlumnusJoinRepository.save(bAlumnusJoinEntity);
            //TODO 返回false说明什么？
            future.complete(new JsonObject(Json.encode(bAlumnusJoinEntity)));
        }

        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        //查询数据条目
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
        bAlumnusJoinRepository.deleteAll(list);
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryall(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
//        Future<JsonArray> future = Future.future();
//        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        BAlumnusJoinEntity bAlumnusEntity = new BAlumnusJoinEntity();
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
//        //创建实例
//        Example<BAlumnusJoinEntity> ex = Example.of(bAlumnusEntity, matcher);
//        List<BAlumnusJoinEntity> newsList = bAlumnusJoinRepository.findAll(ex);
//        if (newsList == null || newsList.size() <= 0) {
//            future.complete(new JsonArray());
//        } else {
//            future.complete(new JsonArray(Json.encode(newsList)));
//        }
//        handler.handle(future);
    }

    @Override
    public void checkById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);

        Optional<BAlumnusJoinEntity> sr =bAlumnusJoinRepository.findById(bAlumnusJoinEntity.getId());
        if (sr.isPresent()) {
            BAlumnusJoinEntity result=sr.get();
            result.setCheckState(Integer.parseInt(params.getString("checkState","0")));
            if(result.getCheckState().equals(2)){
                result.setPresident(Integer.parseInt(params.getString("president","2")));
            }
            bAlumnusJoinRepository.save(result);

            //添加通知消息
            BMessageEntity bMessageEntity=new BMessageEntity();
            bMessageEntity.setStatus(0);
            bMessageEntity.setType(2);
            bMessageEntity.setCreateTime(new Date());
            bMessageEntity.setUpdateTime(new Date());
            bMessageEntity.setRecoreId(result.getId());
            bMessageEntity.setUserId(result.getUserId());
            if(result.getCheckState().equals(2)){
                bMessageEntity.setContent("申请会长（副会长）通过");
            }else{
                bMessageEntity.setContent("申请会长（副会长）失败");
            }
            bMessageRepository.save(bMessageEntity);
            //TODO 返回false说明什么？
            future.complete(new JsonObject(Json.encode(result)));

        } else {
            future.fail("未找到对应实体");
        }

        handler.handle(future);
    }
    /**
     * 根据用户查询申请会长状态
     *
     * @param params
     * @param handler
     */
    @Override
    public void queryPresidentByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
        if (list.size()==0) {
            future.fail("未找到对应实体");
        } else {
            BAlumnusJoinEntity result=list.get(0);
            future.complete(new JsonObject(Json.encode(result)));
        }
        handler.handle(future);
    }
    /**
     * 申请会长
     *
     * @param params
     * @param handler
     */
    @Override
    public void applyByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
        String alumnusId = bAlumnusJoinEntity.getAlumnusId();
        String userId = bAlumnusJoinEntity.getUserId();
        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByAlumnusIdAndUserId(alumnusId, userId);
if(list.size()>0){
    BAlumnusJoinEntity result=list.get(0);
    result.setCheckState(Integer.parseInt(params.getString("checkState","0")));
    bAlumnusJoinRepository.save(result);
    //TODO 返回false说明什么？
    future.complete(new JsonObject(Json.encode(result)));
}else{
    future.fail("未加入本会");
}


        handler.handle(future);
    }

//    @Override
//    public void queryApplyListByuserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
//        Future<JsonObject> future = Future.future();
//        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);
//        String userId = bAlumnusJoinEntity.getUserId();
//
//        PageVo pageVo = new PageVo(params);
//        Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
//
//        List<BAlumnusJoinEntity> list = bAlumnusJoinRepository.findByUserIdAndCheckStateNot(userId,0);
//
//        JsonObject result=new JsonObject();
//        result.put("content",new JsonArray(Json.encode(list)));
////        result.put("total",count);
//        future.complete(result);
//        handler.handle(future);
//    }
    @Override
    public void queryApplyListByuserId( JsonObject params,  Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BAlumnusJoinEntity bAlumnusJoinEntity = new BAlumnusJoinEntity(params);

        PageVo pageVo = new PageVo(params);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        bAlumnusJoinEntity.setCheckState(0);
        Page<BAlumnusJoinEntity> page = bAlumnusJoinRepository.findAll(new Specification<BAlumnusJoinEntity>() {
            @Override
            public Predicate toPredicate(Root<BAlumnusJoinEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(bAlumnusJoinEntity.getUserId())){
                    //相当于 like
                    list.add(criteriaBuilder.equal(root.get("userId").as(String.class),bAlumnusJoinEntity.getUserId()));
                }
                if (bAlumnusJoinEntity.getCheckState()!=null){
                    //相当于 like
                    list.add(criteriaBuilder.notEqual(root.get("checkState").as(Integer.class),bAlumnusJoinEntity.getCheckState()));
                }
                //重点
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageable);
        JsonObject res=new JsonObject(Json.encode(page));
        future.complete(res);
        handler.handle(future);
    }


}
