package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BAlumnusJoinEntity;
import com.tb.service.cdxyh.entity.BMessageEntity;
import com.tb.service.cdxyh.entity.BWechatUsersEntity;
import com.tb.service.cdxyh.repository.BAlumnusJoinRepository;
import com.tb.service.cdxyh.repository.BMessageRepository;
import com.tb.service.cdxyh.repository.BWechatUsersAttentionRepository;
import com.tb.service.cdxyh.repository.BWechatUsersRepository;
import com.tb.service.cdxyh.service.BWechatUsersAsyncService;
import com.tb.service.cdxyh.service.BWechatUsersAttentionAsyncService;
import com.tb.service.cdxyh.utils.Pinyin4jUtil;
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
import java.util.Map;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BWechatUsersAsyncServiceImpl implements BWechatUsersAsyncService, BaseAsyncService {
    @Autowired
    private BWechatUsersRepository bWechatUsersRepository;
    @Autowired
    private BWechatUsersAttentionRepository bWechatUsersAttentionRepository;
    @Autowired
    private BAlumnusJoinRepository bAlumnusJoinRepository;
    @Autowired
    private BMessageRepository bMessageRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        bWechatUsersEntity.setCreateTime(new Date());
        bWechatUsersEntity.setUpdateTime(new Date());
        bWechatUsersEntity.setAuditStatus("0");
        //获取名字首字母
        String nameInitial = Pinyin4jUtil.getFirstPinYinHeadChar(bWechatUsersEntity.getName());
        bWechatUsersEntity.setNameInitial(nameInitial);
        bWechatUsersRepository.save(bWechatUsersEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);

        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
        if (params.getString("type")!=null) {
            bWechatUsersEntity.setType(params.getString("type"));
            matcher.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        //创建实例
        Example<BWechatUsersEntity> ex = Example.of(bWechatUsersEntity, matcher);
        Page<BWechatUsersEntity> plist = bWechatUsersRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        bWechatUsersEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BWechatUsersEntity> sr = bWechatUsersRepository.findById(bWechatUsersEntity.getOpenid());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bWechatUsersEntity.setUpdateTime(new Date());
            bWechatUsersRepository.save(bWechatUsersEntity);
            //TODO 返回false说明什么？
            future.complete("修改成功!");
        }
        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String[] ids = params.getString("id").split(",");
        for (int i = 0; i < ids.length; i++) {
            bWechatUsersRepository.deleteById(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }


    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        Optional<BWechatUsersEntity> res = bWechatUsersRepository.findById(bWechatUsersEntity.getOpenid());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.fail("用户未认证");
        }
        handler.handle(future);
    }

    @Override
    public void queryAllByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String userId=params.getString("userId");
        if(userId!=null){
        Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
        List<Map<String,Object>> res = bWechatUsersRepository.findAllListByUserId(userId,pageVo.getPageSize(),offset);
        Long zoom=bWechatUsersRepository.count();
        JsonArray array=new JsonArray(res);
        future.complete(new JsonObject().put("content",array).put("total",zoom));
        }else{
            future.complete(new JsonObject().put("content",new JsonArray()).put("total",0));
        }
        handler.handle(future);
    }

    @Override
    public void queryFansListByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String userId=params.getString("userId");
        if(userId!=null){
            Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
            List<BWechatUsersEntity> res = bWechatUsersRepository.queryFansListByUserId(userId,pageVo.getPageSize(),offset);
//        Long zoom=bWechatUsersRepository.count();
            JsonArray array=new JsonArray();
            res.forEach(item->{
                array.add(item.toJson());
            });
            future.complete(new JsonObject().put("content",array).put("total",0));
        }else{
            future.complete(new JsonObject().put("content",new JsonArray()).put("total",0));
        }

        handler.handle(future);
    }

    @Override
    public void queryAttentionListByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String userId=params.getString("userId");
        if(userId!=null){
            Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
            List<BWechatUsersEntity> res = bWechatUsersRepository.queryAttentionListByUserId(userId,pageVo.getPageSize(),offset);
//        Long zoom=bWechatUsersRepository.count();
            JsonArray array=new JsonArray();
            res.forEach(item->{
                if(item!=null){
                    array.add(item.toJson());
                }

            });
            future.complete(new JsonObject().put("content",array).put("total",0));
        }else{
            future.complete(new JsonObject().put("content",new JsonArray()).put("total",0));
        }

        handler.handle(future);
    }

    /**
     * 按首字母分组排序返回结果
     * @param params
     * @param handler
     */
    @Override
    public void getUserListByInitialGroup(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        JsonObject resObj = new JsonObject();
        String userId = params.getString("userId");
        List<Map<String, Object>> list = bWechatUsersRepository.findAllOrderByNameDesc(userId);
        for (int i = 0; i < list.size(); i++) {
            JsonObject jsonObject = new JsonObject(list.get(i));
            String nameInitial = jsonObject.getString("name_initial");
            JsonObject obj = resObj.getJsonObject(nameInitial);
            if (obj !=null && !obj.isEmpty()){
                obj.getJsonArray("list").add(jsonObject);
            } else {
                resObj.put(nameInitial,new JsonObject().put("list", new JsonArray().add(jsonObject)));
            }
        }
        future.complete(resObj);
        handler.handle(future);
    }

    @Override
    public void queryListByAlumnusId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);
        String userId=params.getString("userId");
        String alumnusId=params.getString("alumnusId");
        if(userId!=null&&alumnusId!=null){
            Integer offset=(pageVo.getPageNo()-1)*pageVo.getPageSize();
            List<Map<String,Object>> res = bWechatUsersRepository.queryListByAlumnusId(userId,alumnusId,pageVo.getPageSize(),offset);
            Long zoom=bWechatUsersRepository.count();
            JsonArray array=new JsonArray(res);
            JsonArray result=new JsonArray();
            array.forEach(r->{
                JsonObject s=(JsonObject)r;
                JsonObject json=new JsonObject(s.toString());
                if(json.getInteger("check_state")!=2){
                    json.put("president",0);
                }
                result.add(json);
            });
            future.complete(new JsonObject().put("content",array).put("total",zoom));
        }else{
            future.complete(new JsonObject().put("content",new JsonArray()).put("total",0));
        }
        handler.handle(future);
    }

    @Override
    public void getUserDetailsByUserId(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String userId = params.getString("userId");
        JsonObject resObj = new JsonObject();
        List<Map<String,Object>> list = bWechatUsersRepository.getUserInfoByUserId(userId);
        if (list.size()>0){
            Map<String,Object> user = list.get(0);
            resObj.put("userInfo", user);

            //统计我的关注数量
            Integer followNum = bWechatUsersAttentionRepository.countAllByUserId(userId);
            resObj.put("followNum", followNum);
            //统计我的粉丝数量
            Integer fansNum = bWechatUsersAttentionRepository.countAllByMemberId(userId);
            resObj.put("fansNum",fansNum);
            //统计加入组织数量
            Integer alumnusNum = bAlumnusJoinRepository.countAllByUserId(userId);
            resObj.put("alumnusNum", alumnusNum);
        }
        future.complete(resObj);
        handler.handle(future);
    }
    @Override
    public void checkById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BWechatUsersEntity bWechatUsersEntity = new BWechatUsersEntity(params);

        Optional<BWechatUsersEntity> sr =bWechatUsersRepository.findById(bWechatUsersEntity.getOpenid());
        if (!sr.isPresent()) {
            future.fail("未找到对应实体");
        } else {
            BWechatUsersEntity result=sr.get();
            result.setAuditStatus(bWechatUsersEntity.getAuditStatus());
//            result.setPresident(Integer.parseInt(params.getString("president","0")));
            //添加通知消息
            BMessageEntity bMessageEntity=new BMessageEntity();
            bMessageEntity.setStatus(0);
            bMessageEntity.setType(1);
            bMessageEntity.setCreateTime(new Date());
            bMessageEntity.setUpdateTime(new Date());
            bMessageEntity.setRecoreId(result.getOpenid());
            bMessageEntity.setUserId(result.getOpenid());
            if(result.getAuditStatus().equals("1")){
                bMessageEntity.setContent("校友认证审核成功");
            }else{
                bMessageEntity.setContent("校友认证失败");
            }
            bMessageRepository.save(bMessageEntity);
            bWechatUsersRepository.save(result);
            //TODO 返回false说明什么？
            future.complete(new JsonObject(Json.encode(result)));
        }

        handler.handle(future);
    }
    public static void main(String[] args) {
        JsonObject resObj = new JsonObject();
        System.out.println(resObj.getJsonObject("*"));
    }



}
