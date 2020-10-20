package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SOperationLogRepository;
import com.xby.lcdata.system.service.SysOperationLogService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.tools.common.utils.StringUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.entity.SysOperationLog;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: SOperationLogServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:16
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class SysOperationLogServiceImpl implements SysOperationLogService, BaseAsyncService {

    @Autowired
    private SOperationLogRepository sOperationLogRepository;

    /**
     * 获取操作日志
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String begin = params.getString("oparationTime_begin");
        String end = params.getString("oparationTime_end");
        String userName = params.getString("username");
        Sort sort = new Sort(Sort.Direction.DESC, "oparationTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysOperationLog> sOperationLogPage = sOperationLogRepository.findAll(new Specification<SysOperationLog>() {
            @Override
            public Predicate toPredicate(Root<SysOperationLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> oparationTimeField = root.get("oparationTime");
                Path<String> operatorField = root.get("operator");
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(userName)) {
                    list.add(criteriaBuilder.like(operatorField, "%" + userName + "%"));
                }
                if (!StringUtils.isEmpty(begin) && !StringUtils.isEmpty(end)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        list.add(criteriaBuilder.between(oparationTimeField, simpleDateFormat.parse(begin), simpleDateFormat.parse(end)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Predicate[] predicates = new Predicate[list.size()];
                criteriaQuery.where(list.toArray(predicates));
                return null;
            }
        }, pageable);
        future.complete(new JsonObject(Json.encode(sOperationLogPage)));
        handler.handle(future);
    }

    /**
     * 删除操作日志
     *
     * @param params
     * @param handler
     */
    @Override
    public void deleteBatch(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String ids = params.getString("ids");
        String[] id = ids.split(",");
        for (int i = 0; i < id.length; i++) {
            sOperationLogRepository.deleteByIdEquals(id[i]);
        }
        future.complete("删除操作日志成功!");
        handler.handle(future);
    }

}
