package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SysDictItemRepository;
import com.xby.lcdata.system.repository.SysDictRepository;
import com.xby.lcdata.system.service.SysDictAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.core.annotation.LogAnnotation;
import com.xby.lcdata.system.entity.SysDict;
import com.xby.lcdata.system.entity.SysDictItem;
import com.xby.lcdata.system.model.DictModel;
import com.xby.lcdata.system.model.DuplicateCheckVo;
import com.xby.lcdata.system.model.SysDictTree;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/8/2
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler

public class SysDictAsyncServiceImpl implements SysDictAsyncService, BaseAsyncService {
    @Autowired
    private SysDictRepository sysDictRepository;

    @Autowired
    private SysDictItemRepository sysDictItemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 获取字典列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        ExampleMatcher matcher =
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //构建对象

        SysDict sysDict = new SysDict();
        String dictName = params.getString("dictName", "");
        String dictCode = params.getString("dictCode", "");


        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        if (dictName != null && dictName != "") {
            sysDict.setDictName(dictName);
            matcher.withMatcher("dictName", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        if (dictCode != null && dictCode != "") {
            sysDict.setDictCode(dictCode);
            matcher.withMatcher("dictCode", ExampleMatcher.GenericPropertyMatchers.startsWith());
        }
        //创建实例
        Example<SysDict> ex = Example.of(sysDict, matcher);
        //分页信息
        PageVo pageVo = new PageVo(params);

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort); //页码：前端从1开始，jpa从0开始，做个转换
        Page<SysDict> sysDicts = sysDictRepository.findAll(ex, pageable);
        future.complete(new JsonObject(Json.encode(sysDicts)));
        handler.handle(future);
    }

    /**
     * @功能：获取树形字典数据
     */
    @Override
    public void treeList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysDict sysDict = new SysDict(params);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象

        sysDict.setDelFlag(1);
        matcher.withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.contains());
        matcher.withMatcher("dictName", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<SysDict> ex = Example.of(sysDict, matcher);
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<SysDict> sysDicts = sysDictRepository.findAll(ex, sort);
        List<SysDictTree> treeList = new ArrayList<>();
        for (SysDict node : sysDicts) {
            treeList.add(new SysDictTree(node));
        }
        future.complete(new JsonObject(Json.encode(treeList)));
        handler.handle(future);
    }

    /**
     * @功能：新增
     */
    @Override
    @LogAnnotation(operationType = "新增字典")
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            SysDict sysDict = new SysDict(params);

            sysDict.setCreateTime(new Date());
            sysDictRepository.save(sysDict);
            future.complete(new JsonObject(Json.encode(sysDict)));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：编辑
     */
    @Override
    @LogAnnotation(operationType = "编辑字典")
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            sysDictRepository.modifyDict(params.getString("dictName"), params.getString("dictCode"),
                    params.getString("description"), JwtUtil.getUsername(params.getString("token")),
                    TimeUtil.getDateNow(), params.getString("id"));
            future.complete(new JsonObject(Json.encode(sysDictRepository.findById(params.getString("id")))));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：删除
     */
    @Override
    @LogAnnotation(operationType = "删除字典")
    public void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            String id = params.getString("id", "");
            SysDict sysDictmodel = new SysDict(params);
            Optional<SysDict> sysDict = sysDictRepository.findById(id);
            if (sysDict.equals(null)) {
                future.fail("未找到对应实体");
            } else {
                //update-begin--Author:huangzhilin  Date:20140417 for：[bugfree号]数据字典增加级联删除功能--------------------
                sysDictRepository.delete(sysDict.get());
                //update-begin--Author:huangzhilin  Date:20140417 for：[bugfree号]数据字典增加级联删除功能--------------------
                future.complete(new JsonObject(Json.encode(sysDict)));
            }
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：批量删除
     */
    @Override
    @LogAnnotation(operationType = "批量删除字典")
    public void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            String ids = params.getString("ids", "");
            if (ids == null || "".equals(ids.trim())) {
                future.fail("参数不识别！");
            } else {
                String[] id = ids.split(",");
                for (int i = 0; i < id.length; i++) {
                    SysDict sysDict = sysDictRepository.findById(id[i]).get();
                    sysDict.setDelFlag(2);
                    sysDictRepository.save(sysDict);
                }
                future.complete(new JsonObject().put("ids", ids));
            }
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * @功能：导出excel
     */
    @Override
    public void exportXls(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        future.complete();
        handler.handle(future);
    }

    /**
     * @功能：通过excel导入数据
     */
    @Override
    public void importExcel(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        future.complete();
        handler.handle(future);
    }

    /**
     * 获取字典数据
     */
    @Override
    public void getDictItems(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String dictCode = params.getString("dictCode");
        //String dictName = params.getString("dictName");
        //log.info(" dictCode : " + dictCode);
        List<SysDictItem> ls = null;
        try {
            if (dictCode.indexOf(",") != -1) {
                //关联表字典（举例：sys_user,realname,id）
                String[] param = dictCode.split(",");
                if (param.length != 3) {
                    future.fail("字典Code格式不正确！");
                }
                ls = this.queryTableDictItemsByCode(param[0], param[1], param[2]);
            } else {
                //字典表
                ls = sysDictItemRepository.queryDictItemsByCode(dictCode);

            }

            future.complete(new JsonArray(Json.encode(ls)));
        } catch (Exception e) {
            future.fail(e);
        }

        handler.handle(future);
    }

    /**
     * 将字典转化为模型
     *
     * @param lists
     * @param handler
     */
    private void entityToModel(List<SysDictItem> lists, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        try {
            List<DictModel> result = new ArrayList<DictModel>();
            lists.forEach(item -> {
                DictModel dict = new DictModel(item);
                result.add(dict);
            });
            future.complete(new JsonArray(Json.encode(result)));
        } catch (Exception e) {
            future.fail(e);
        }

        handler.handle(future);
    }

    /**
     * 获取任意表字典数据，未测试
     *
     * @param table
     * @param text
     * @param code
     * @return
     */
    private List<SysDictItem> queryTableDictItemsByCode(String table, String text, String code) {
        String sql = " select " + text + " as 'text'," + code + " as 'value' from " + table + " ";
        Query formDesignQueryCount = entityManager.createNativeQuery(sql, DictModel.class);
        List<SysDictItem> result = formDesignQueryCount.getResultList();
        return result;
    }

    /**
     * 获取字典数据
     *
     * @param params
     * @return
     */
    @Override
    public void getDictItemsByKey(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String dictCode = params.getString("dictCode");
        String key = params.getString("key");
        String text = null;
        try {
            text = sysDictRepository.queryDictTextByKey(dictCode, key);
            future.complete(new JsonArray(Json.encode(text)));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    /**
     * 校验数据是否在系统中是否存在
     *
     * @return
     */
    @Override
    public void doDuplicateCheck(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        DuplicateCheckVo duplicateCheckVo = new DuplicateCheckVo(params);
        Long num = null;

        if (StringUtils.isNotBlank(duplicateCheckVo.getDataId())) {
            // [2].编辑页面校验
            num = this.duplicateCheckCountSql(duplicateCheckVo);
        } else {
            // [1].添加页面校验
            num = this.duplicateCheckCountSqlNoDataId(duplicateCheckVo);
        }

        if (num == null || num == 0) {
            // 该值可用
            future.complete(new JsonObject().put("msg", "该值可用！"));
        } else {
            // 该值不可用
            future.fail("该值不可用，系统中已存在！");
        }

        handler.handle(future);
    }

    /**
     * 编辑页面校验
     */
    private Long duplicateCheckCountSql(DuplicateCheckVo duplicateCheckVo) {
        StringBuffer applyRecordCountSql =
                new StringBuffer("SELECT COUNT(*) FROM " + duplicateCheckVo.getTableName() + " WHERE " + duplicateCheckVo.getFieldName() + " = '" + duplicateCheckVo.getFieldVal() + "' and id != '" + duplicateCheckVo.getDataId() + "' ");
        Query queryCount = entityManager.createNativeQuery(applyRecordCountSql.toString());
        long resultCount = ((BigInteger) queryCount.getSingleResult()).longValue();
        return resultCount;
    }

    /**
     * 添加页面校验
     */
    private Long duplicateCheckCountSqlNoDataId(DuplicateCheckVo duplicateCheckVo) {
        StringBuffer applyRecordCountSql =
                new StringBuffer("SELECT COUNT(*) FROM " + duplicateCheckVo.getTableName() + " WHERE " + duplicateCheckVo.getFieldName() + " = '" + duplicateCheckVo.getFieldVal() + "' ");
        Query queryCount = entityManager.createNativeQuery(applyRecordCountSql.toString());
        long resultCount = ((BigInteger) queryCount.getSingleResult()).longValue();
        return resultCount;
    }

    /**
     * 根据字典名称与字典编号查询字典列表
     */
    public void queryByNameCode(String dictName, String dictCode, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        try {
            StringBuffer sql = new StringBuffer(String.format("select * from s_dict where del_flag = 1 "));
            if (dictName != null) {
                sql.append(String.format(" and dict_name like '%s' ", "%" + dictName + "%"));
            }
            if (dictCode != null) {
                sql.append(String.format(" and dict_code = '%s' ", dictCode));
            }
            Query queryList = entityManager.createNativeQuery(sql.toString(), SysDictItem.class);
            future.complete(new JsonObject(Json.encode(queryList.getResultList())));
        } catch (Exception e) {
            future.fail(e);
        }
        handler.handle(future);
    }

    @Override
    public void getDataBySql(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        String sql = params.getString("sql");
        Query query = entityManager.createNativeQuery(sql);
        if (query.getResultList().size() > 0) {
            Query queryList = entityManager.createNativeQuery(sql);
            queryList.unwrap(org.hibernate.SQLQuery.class)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            future.complete(new JsonArray(Json.encode(query.getResultList())));
        } else {
            future.complete(new JsonArray());
        }
        handler.handle(future);
    }
}
