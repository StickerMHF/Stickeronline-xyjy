package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SLoginLogRepository;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.tools.common.utils.StringUtils;
import com.tb.base.common.vo.PageVo;
import com.xby.lcdata.system.entity.SysLoginLog;
import com.xby.lcdata.system.service.SysLoginLogService;
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
 * @ClassName: SLoginLogServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2019/8/26 0026 10:12
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class SysLoginLogServiceImpl implements SysLoginLogService, BaseAsyncService {

    @Autowired
    private SLoginLogRepository sLoginLogRepository;

    /**
     * 获取登录日志列表
     *
     * @param params
     * @param handler
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        String begin = params.getString("loginTime_begin");
        String end = params.getString("loginTime_end");
        String userName = params.getString("username");
        Sort sort = new Sort(Sort.Direction.DESC, "loginTime");
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        Page<SysLoginLog> sOperationLogPage = sLoginLogRepository.findAll(new Specification<SysLoginLog>() {
            @Override
            public Predicate toPredicate(Root<SysLoginLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Date> loginTimeField = root.get("loginTime");
                Path<String> usernameField = root.get("username");
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(userName)) {
                    list.add(criteriaBuilder.like(usernameField, "%" + userName + "%"));
                }
                if (!StringUtils.isEmpty(begin) && !StringUtils.isEmpty(end)) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        list.add(criteriaBuilder.between(loginTimeField, simpleDateFormat.parse(begin), simpleDateFormat.parse(end)));
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
     * 删除登录日志
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
            sLoginLogRepository.deleteByIdEquals(id[i]);
        }
        future.complete("删除登录日志成功!");
        handler.handle(future);
    }

}
