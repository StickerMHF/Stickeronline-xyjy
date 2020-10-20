package com.xby.lcdata.system.core.aop;

import com.xby.lcdata.system.core.annotation.ChangeRecordAnnotation;
import com.xby.lcdata.system.core.enums.OperationType;
import com.xby.lcdata.system.entity.SChangeRecord;
import com.xby.lcdata.system.repository.SChangeRecordRepository;
import com.xby.lcdata.system.service.impl.DataChangeRecordServiceImpl;
import com.sticker.online.core.utils.JwtUtil;
import com.sticker.online.core.utils.TimeUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ChangeRecordAopAspect
 * @Description:
 * @Author: wangf
 * @Date: 2020/2/25 0025 17:12
 * @Version: 1.0
 **/
@Aspect
@Component
@Order(3)
public class ChangeRecordAopAspect {

    @Autowired
    private DataChangeRecordServiceImpl dataChangeRecordService;
    @Autowired
    private SChangeRecordRepository sChangeRecordRepository;

    /**
     * 删除操作
     *
     * @param joinPoint
     */
//    @Before("execution(public * com.xby.lcdata.*.repository.delete*(..))")
    public void doBefore(JoinPoint joinPoint) {
        // 获取方法密钥
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        ChangeRecordAnnotation changeRecordAnnotation = method.getAnnotation(ChangeRecordAnnotation.class);
        // 获取操作类型
        OperationType operationType = changeRecordAnnotation.operationType();
        // 获取操作实体
        Class entity = changeRecordAnnotation.entity();
        Table table = (Table) entity.getAnnotation(Table.class);
        // 获取操作表名
        String tableName = table.name();
        String schema = table.schema();
        // 获取所有属性、方法
        Field[] fields = entity.getDeclaredFields();
        // 筛选带有Column注解的属性
        List<Field> fieldArrayList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Column columns = fields[i].getAnnotation(Column.class);
            if (columns != null) {
                fieldArrayList.add(fields[i]);
            }
        }
        if (operationType.equals(OperationType.DELETE)) {
            Object[] paramsValue = joinPoint.getArgs();
            String[] paramsName = methodSignature.getParameterNames();
            String username = "";
            String[] id = null;
            for (int i = 0; i < paramsName.length; i++) {
                if (paramsName[i].equals("params") && paramsValue[i] != null) {
                    JsonObject params = new JsonObject(Json.encode(paramsValue[i]));
                    if (paramsName[i].equals("token")) {
                        username = JwtUtil.getUsername(String.valueOf(paramsValue[i]));
                    }
                    if (params.getString("id") != null) {
                        id = params.getString("id").split(",");
                    }
                } else if (paramsName[i].equals("id")) {
                    for (int k = 0; k < fieldArrayList.size(); k++) {
                        if (paramsName[i].equals("id") && fieldArrayList.get(k).getName().equals(paramsName[i])) {
                            id = String.valueOf(paramsValue[i]).split(",");
                        }
                    }
                }else if (paramsName[i].equals("id")) {
                    for (int k = 0; k < fieldArrayList.size(); k++) {
                        if (paramsName[i].equals("id") && fieldArrayList.get(k).getName().equals(paramsName[i])) {
                            id = String.valueOf(paramsValue[i]).split(",");
                        }
                    }
                }
            }
            for (int i = 0; i < id.length; i++) {
                StringBuffer sql = new StringBuffer(String.format("select * from %s ", tableName));
                sql.append(String.format(" where id ='%s'", id[i]));
                saveLog(tableName, username, "", sql.toString(), entity, operationType.name());
            }
        }
    }

    /**
     * 添加操作
     *
     * @param joinPoint
     */
//    @After(value = "execution(public * com.xby.lcdata.*.service..add*(..))")
    public void doAfter(JoinPoint joinPoint) {
        // 获取方法密钥
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        ChangeRecordAnnotation changeRecordAnnotation = method.getAnnotation(ChangeRecordAnnotation.class);
        // 获取操作类型
        OperationType operationType = changeRecordAnnotation.operationType();
        // 获取操作实体
        Class entity = changeRecordAnnotation.entity();
        Table table = (Table) entity.getAnnotation(Table.class);
        // 获取操作表名
        String tableName = table.name();
        if (operationType.equals(OperationType.ADD)) {
            Object[] paramsValue = joinPoint.getArgs();
            String[] paramsName = methodSignature.getParameterNames();
            String username = "";
            String newData = "";
            for (int i = 0; i < paramsName.length; i++) {
                if (paramsName[i].equals("params")) {
                    JsonObject params = new JsonObject(Json.encode(paramsValue[i]));
                    username = JwtUtil.getUsername(params.getString("token"));
                    params.remove("token");
                    newData = params.toString();
                }
            }
            saveLog(tableName, username, newData, "", entity, operationType.name());
        }
    }

    /**
     * 修改操作
     *
     * @param proceedingJoinPoint
     */
//    @Around(value = "execution(public * com.xby.lcdata.*.service..update*(..))")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        Object proceed = null;
        // 获取方法密钥
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        ChangeRecordAnnotation changeRecordAnnotation = method.getAnnotation(ChangeRecordAnnotation.class);
        // 获取操作类型
        OperationType operationType = changeRecordAnnotation.operationType();
        // 获取操作实体
        Class entity = changeRecordAnnotation.entity();
        Table table = (Table) entity.getAnnotation(Table.class);
        // 获取操作表名
        String tableName = table.name();
        String schema = table.schema();
        // 获取所有属性、方法
        Field[] fields = entity.getDeclaredFields();
        // 筛选带有Column注解的属性
        List<Field> fieldArrayList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Column columns = fields[i].getAnnotation(Column.class);
            if (columns != null) {
                fieldArrayList.add(fields[i]);
            }
        }
        if (operationType.equals(OperationType.UPDATE)) {
            Object[] paramsValue = proceedingJoinPoint.getArgs();
            String[] paramsName = methodSignature.getParameterNames();
            StringBuffer sql = new StringBuffer(String.format("select * from %s ", tableName));
            String username = "";
            String newData = "";
            for (int i = 0; i < paramsName.length; i++) {
                if (paramsName[i].equals("params")) {
                    JsonObject params = new JsonObject(Json.encode(paramsValue[i]));
                    username = JwtUtil.getUsername(params.getString("token"));
                    params.remove("token");
                    sql.append(String.format(" where id='%s' ", params.getString("id")));
                    newData = params.toString();
                }
            }
            saveLog(tableName, username, newData, sql.toString(), entity, operationType.name());
            try {
                proceed = proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return proceed;
    }

    /**
     * 保存数据变更记录
     *
     * @param tableName
     * @param username
     * @param sql
     * @param entity
     * @param type
     */
    public void saveLog(String tableName, String username, String newData, String sql, Class entity, String type) {
        System.out.println("获取原数据SQL:" + sql);
        SChangeRecord sChangeRecord = new SChangeRecord();
        sChangeRecord.setTableName(tableName);
        sChangeRecord.setOperator(username);
        sChangeRecord.setNewData(newData);
        List<Map<String, Object>> list = dataChangeRecordService.getList(sql, entity);
        if (list.size() > 0) {
//            JsonArray oldData = new JsonArray(Json.encode(list));
            JsonObject oldData = new JsonObject(Json.encode(list.get(0)));
            sChangeRecord.setOldData(oldData.toString());
            sChangeRecord.setType(type);
            sChangeRecord.setCreateTime(TimeUtil.getNow());
            sChangeRecordRepository.save(sChangeRecord);
        }
    }

}
