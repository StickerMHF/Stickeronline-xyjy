package com.xby.lcdata.system.utils;


import com.xby.lcdata.system.entity.SysCatalog;
import com.xby.lcdata.system.model.CatalogIdModel;
import com.xby.lcdata.system.model.SysCatalogTreeModel;
import com.sticker.online.core.utils.oConvertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.ygj.cyryks.internet.main.sys.utils
 * @Description:对应目录表,处理并查找树级数据
 * @Author: Sticker
 * @Date: 2019/8/1
 * @Version: 1.0
 * @LastModified:毛海锋
 */
public class FindsCatalogsChildrenUtil {


    private static List<CatalogIdModel> idList = new ArrayList<>(4);


    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysDepart类型的list集合转换成SysDepartTreeModel类型的集合
     */
    public static List<SysCatalogTreeModel> wrapTreeDataToTreeList(List<SysCatalog> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        idList.clear();
        List<SysCatalogTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCatalog depart = recordList.get(i);
            records.add(new SysCatalogTreeModel(depart));
        }
        List<SysCatalogTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    public static List<CatalogIdModel> wrapCatalogIdModel() {
        return idList;
    }
    public static List<CatalogIdModel> getCatalogIdModel(List<SysCatalog> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        idList.clear();
        List<SysCatalogTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCatalog depart = recordList.get(i);
            records.add(new SysCatalogTreeModel(depart));
        }
        List<SysCatalogTreeModel> tree = findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<SysCatalogTreeModel> findChildren(List<SysCatalogTreeModel> recordList,
                                                         List<CatalogIdModel> idList) {

        List<SysCatalogTreeModel> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCatalogTreeModel branch = recordList.get(i);
            if (oConvertUtils.isEmpty(branch.getPid())||branch.getPid().equals("0")) {
                treeList.add(branch);
                CatalogIdModel departIdModel = new CatalogIdModel().convert(branch);
                idList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,recordList,idList);
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<SysCatalogTreeModel> treeList,List<SysCatalogTreeModel> recordList,List<CatalogIdModel> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysCatalogTreeModel model = treeList.get(i);
            CatalogIdModel idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                SysCatalogTreeModel m = recordList.get(i1);
                if (m.getPid()!=null && m.getPid().equals(model.getId())) {
                    model.getChildren().add(m);
                    CatalogIdModel dim = new CatalogIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }


    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<SysCatalogTreeModel> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysCatalogTreeModel model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(1);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(0);
            }
        }
    }
}
