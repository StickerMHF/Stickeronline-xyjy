package com.xby.lcdata.system.service.impl;

import com.xby.lcdata.system.repository.SAccessoryRepository;
import com.xby.lcdata.system.repository.SCatalogRepository;
import com.xby.lcdata.system.service.SysCatalogAsyncService;
import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.oConvertUtils;
import com.xby.lcdata.system.entity.SysAccessory;
import com.xby.lcdata.system.entity.SysCatalog;
import com.xby.lcdata.system.model.CatalogIdModel;
import com.xby.lcdata.system.model.SysCatalogTree;
import com.xby.lcdata.system.utils.FindsCatalogsChildrenUtil;
import com.xby.lcdata.system.utils.YouBianCodeUtil;
import io.netty.util.internal.StringUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.service.impl
 * @Description:
 * @Author: Sticker
 * @Date: 2019/7/31
 * @Version: 1.0
 * @LastModified:毛海锋
 */
@Component
@AsyncServiceHandler
@Transactional
public class SysCatalogAsyncServiceImpl implements SysCatalogAsyncService, BaseAsyncService {
    @Autowired
    private SCatalogRepository sysCatalogRepository;
    @Autowired
    private SAccessoryRepository sysAccessoryRepository;

    /**
     * 获取目录列表
     */
    @Override
    public void list(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SysCatalog> list = sysCatalogRepository.findAll();
        future.complete(new JsonArray(Json.encode(list)));
        handler.handle(future);
    }

    /**
     * 获取目录树
     */
    @Override
    public void queryTreeList(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SysCatalog> list = sysCatalogRepository.findAll();
        List<SysCatalogTree> treeList = new ArrayList<>();
        this.getTreeModelList(treeList, list, null);
        future.complete(new JsonArray(Json.encode(treeList)));
        handler.handle(future);
    }

    /**
     * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有目录的名称,方便用户的操作
     */
    @Override
    public void queryIdTree(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SysCatalog> list = sysCatalogRepository.findAll();
        List<SysCatalogTree> treeList = new ArrayList<>();
        // 调用wrapTreeDataToTreeList方法生成树状数据
        FindsCatalogsChildrenUtil.wrapTreeDataToTreeList(list);
//            this.getTreeModelList(treeList, list, null);
        List<CatalogIdModel> idLists = FindsCatalogsChildrenUtil.getCatalogIdModel(list);
        future.complete(new JsonArray(Json.encode(idLists)));
        handler.handle(future);
    }

    /**
     * 获取目录树，包含文件
     *
     * @param params
     * @param handler
     */
    public void listOfAccessory(JsonObject params, Handler<AsyncResult<JsonArray>> handler) {
        Future<JsonArray> future = Future.future();
        List<SysCatalog> catalogs = sysCatalogRepository.findAll();
        List<SysAccessory> accessorys = sysAccessoryRepository.findAll();
        List<SysCatalogTree> treeList = new ArrayList<>();
        this.getTreeModelList(treeList, catalogs, null);
        this.addAccessoryToTree(treeList, accessorys);
        future.complete(new JsonArray(Json.encode(treeList)));
        handler.handle(future);
    }

    @Override
    public void add(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysCatalog sysCatalog = new SysCatalog(params);
        sysCatalog.setCode(generateOrgCode(sysCatalog.getPid()));
        sysCatalogRepository.save(sysCatalog);
        future.complete(sysCatalog.toJson());
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        SysCatalog sysCatalog = new SysCatalog(params);
        sysCatalogRepository.save(sysCatalog);
        future.complete(sysCatalog.toJson());
        handler.handle(future);
    }

    @Override
    public void delete(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("id");
        sysCatalogRepository.deleteById(id);
        future.complete();
        handler.handle(future);
    }

    @Override
    public void deleteBatch(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String id = params.getString("ids");
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            sysCatalogRepository.deleteById(ids[i]);
        }
        future.complete(new JsonObject().put("data", id).put("msg", "删除成功!"));
        handler.handle(future);
    }

    /**
     * saveSCatalogData 的调用方法,生成目录编码
     */
    private String generateOrgCode(String parentId) {
        //update-begin--Author:Steve  Date:20190201 for：组织机构添加数据代码调整
        ExampleMatcher matcher1 = ExampleMatcher.matching(); //构建对象
        ExampleMatcher matcher2 = ExampleMatcher.matching(); //构建对象
        String[] strArray = new String[1];
        // 创建一个List集合,存储查询返回的所有SysDepart对象
        List<SysCatalog> departList = new ArrayList<>();
        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";
        // 如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (StringUtil.isNullOrEmpty(parentId)) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            matcher1.withMatcher("pid", ExampleMatcher.GenericPropertyMatchers.contains());
            Sort sort = new Sort(Sort.Direction.DESC, "code");
            SysCatalog sysCatalog = new SysCatalog();
            sysCatalog.setPid("");
            //创建实例
            Example<SysCatalog> ex = Example.of(sysCatalog, matcher1);
            departList = sysCatalogRepository.findAll(ex, sort);
            if (departList == null || departList.size() == 0) {
                return YouBianCodeUtil.getNextYouBianCode(null);
            } else {
                SysCatalog depart = departList.get(0);
                oldOrgCode = depart.getCode();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else { // 反之则查询出所有同级的目录,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            matcher2.withMatcher("pid", ExampleMatcher.GenericPropertyMatchers.contains());
            Sort sort = new Sort(Sort.Direction.DESC, "code");
            SysCatalog sysDepart = new SysCatalog();
            sysDepart.setPid(parentId);
            //创建实例
            Example<SysCatalog> ex = Example.of(sysDepart, matcher2);
            // 查询出同级目录的集合
            List<SysCatalog> parentList = sysCatalogRepository.findAll(ex, sort);
            // 查询出父级目录
            SysCatalog depart = sysCatalogRepository.findById(parentId).get();
            // 获取父级目录的Code
            String parentCode = depart.getCode();
            // 处理同级目录为null的情况
            if (parentList == null || parentList.size() == 0) {
                // 直接生成当前的目录编码并返回
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
            } else { //处理有同级目录的情况
                // 获取同级目录的编码,利用工具类
                String subCode = parentList.get(0).getCode();
                // 返回生成的当前目录编码
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
            }
        }
        return newOrgCode;
    }

    /**
     * 将list转为树结构
     *
     * @param treeList
     * @param metaList
     * @param temp
     */
    private void getTreeModelList(List<SysCatalogTree> treeList, List<SysCatalog> metaList, SysCatalogTree temp) {
        for (SysCatalog catalog : metaList) {
            String tempPid = catalog.getPid();
            SysCatalogTree tree = new SysCatalogTree(catalog);
            if (temp == null && (oConvertUtils.isEmpty(tempPid) || tempPid.equals("0"))) {
                treeList.add(tree);
                getTreeModelList(treeList, metaList, tree);
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getId())) {
                temp.getChildren().add(tree);
                getTreeModelList(treeList, metaList, tree);
            }
        }
    }

    /**
     * 将资料列表加到目录树中
     *
     * @param treeList
     * @param accessorys
     */
    private void addAccessoryToTree(List<SysCatalogTree> treeList, List<SysAccessory> accessorys) {
        for (SysAccessory sysAccessory : accessorys) {
            String tempPid = sysAccessory.getPid();
            SysCatalogTree tree = new SysCatalogTree(sysAccessory);
            for (SysCatalogTree catalogtree : treeList) {
                if (catalogtree.getLeaf() == 0) {
                    if (catalogtree.getId().equals(tempPid)) {
                        if (!isequals(catalogtree.getChildren(), tree)) {
                            catalogtree.getChildren().add(tree);
                        }
                    }
                    this.addAccessoryToTree(catalogtree.getChildren(), accessorys);
                }
            }
        }
    }

    /**
     * 判断目录中是否有文件
     *
     * @param treeList
     * @param tree
     * @return
     */
    private boolean isequals(List<SysCatalogTree> treeList, SysCatalogTree tree) {
        boolean result = false;
        for (SysCatalogTree catalogtree : treeList) {
            if (catalogtree.getLeaf() == 1 && catalogtree.getId().equals(tree.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }

}
