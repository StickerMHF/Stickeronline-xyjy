package com.tb.service.cdxyh.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.TimeUtil;
import com.tb.base.common.vo.PageVo;
import com.tb.service.cdxyh.entity.BTeachersEntity;
import com.tb.service.cdxyh.repository.BTeachersRepository;
import com.tb.service.cdxyh.service.BTeachersAsyncService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@AsyncServiceHandler
public class BTeachersAsyncServiceImpl implements BTeachersAsyncService, BaseAsyncService {
    @Autowired
    private BTeachersRepository bTeachersRepository;
    @Override
    public void add(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        bTeachersEntity.setCreateTime(new Date());
        bTeachersEntity.setUpdateTime(new Date());
        bTeachersRepository.save(bTeachersEntity);
        future.complete("添加成功!");
        handler.handle(future);
    }

    @Override
    public void queryPageList(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        PageVo pageVo = new PageVo(params);
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        String sorts=params.getString("sort","createTime");
        Sort sort = new Sort(Sort.Direction.DESC, sorts);
        Pageable pageable = PageRequest.of(pageVo.getPageNo() - 1, pageVo.getPageSize(), sort);
        ExampleMatcher matcher = ExampleMatcher.matching(); //构建对象
//        matcher.withMatcher("userId", ExampleMatcher.GenericPropertyMatchers.contains());
        //创建实例
        Example<BTeachersEntity> ex = Example.of(bTeachersEntity, matcher);
        Page<BTeachersEntity> plist = bTeachersRepository.findAll(ex,pageable);
        future.complete(new JsonObject(Json.encode(plist)));
        handler.handle(future);
    }

    @Override
    public void edit(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        bTeachersEntity.setCreateTime(TimeUtil.convertStringToDate(params.getString("createTime")));
        Optional<BTeachersEntity> sr = bTeachersRepository.findById(bTeachersEntity.getId());
        if (sr == null) {
            future.fail("未找到对应实体");
        } else {
            bTeachersEntity.setUpdateTime(new Date());
            bTeachersRepository.save(bTeachersEntity);
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
            bTeachersRepository.deleteByIdEquals(ids[i]);
        }
        future.complete("删除成功!");
        handler.handle(future);
    }

    @Override
    public void queryById(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
        Optional<BTeachersEntity> res = bTeachersRepository.findById(bTeachersEntity.getId());
        if (res.isPresent()) {
            future.complete(new JsonObject(Json.encode(res.get())));
        }else{
            future.complete(new JsonObject());
        }
        handler.handle(future);
    }

    @Override
    public void jobs(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        BTeachersEntity bTeachersEntity = new BTeachersEntity(params);
//        Optional<BTeachersEntity> res = bTeachersRepository.findById(bTeachersEntity.getId());
        String filePath = "D:\\js.chd.edu.cn.csv";
        List<BTeachersEntity> list=this.readTxtFile(filePath);
        if(list!=null){
            bTeachersRepository.saveAll(list);
            future.complete(new JsonObject().put("count",list.size()));
        }else{
            future.fail("解析html出错");
        }

        handler.handle(future);
    }

    public static void main(String[] args) {
        BTeachersAsyncServiceImpl test =new BTeachersAsyncServiceImpl();
        String filePath = "D:\\js.chd.edu.cn.csv";
//      "res/";
        List<BTeachersEntity> ss=test.readTxtFile(filePath);
        System.out.println(ss);
    }
    public List<BTeachersEntity> readTxtFile(String filePath){
        try {
            List<BTeachersEntity> list=new ArrayList<>();
            String encoding="UTF-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    BTeachersEntity bTeachersEntity=new BTeachersEntity();
//                    list.add(lineTxt);
                    String name=lineTxt.split(",")[0];
                    String url=lineTxt.split(",")[1];
                    String rank=lineTxt.split(",")[2];
                    bTeachersEntity.setName(name);
                    bTeachersEntity.setRank(rank);
                    System.out.println(name+":"+rank+":"+url);
                    bTeachersEntity.setCreateBy("管理员");
                    bTeachersEntity.setCreateTime(new Date());
                    bTeachersEntity.setUpdateBy("管理员");
                    bTeachersEntity.setUpdateTime(new Date());

                    getHtml(bTeachersEntity,url);
                    list.add(bTeachersEntity);
                }
                read.close();
                return list;
//                System.out.println(list);
            }else{
                System.out.println("找不到指定的文件");
                return null;
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
            return null;
        }

    }
    private void getHtml(BTeachersEntity bTeachersEntity,String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36").get();
//            System.out.println(doc);
            Elements listDiv = doc.getElementsByAttributeValue("class", "mod modbox clearfix");
            for (Element text : listDiv) {
                //照片
                Element zp=text.getElementById("wp_news_w11");
                Elements img=zp.getElementsByTag("img");
                String pthto=img.get(0).attr("src");
                bTeachersEntity.setPhotos("http://js.chd.edu.cn"+pthto);

                Elements fenleis = text.getElementsByAttributeValue("class", "post post1 post-31 mbox");
                for (Element fl : fenleis){
                    Elements h3 = fl.getElementsByTag("h3");
                    if(h3.size()>0){
                        Elements span=h3.get(0).getElementsByTag("span");
                        String title=span.get(0).text();
                        switch (title){
                            case "个人资料":
                                grzl(bTeachersEntity,fl);
                                break;
                            case "个人简介":
                                grjj(bTeachersEntity,fl);
                                break;
                            case "社会职务":
                                shzw(bTeachersEntity,fl);
                                break;
                            case "研究领域":
                                yjly(bTeachersEntity,fl);
                                break;
                            case "科研项目":
                                kyxm(bTeachersEntity,fl);
                                break;
                            case "论文":
                                lunwen(bTeachersEntity,fl);
                                break;
                            case "科技成果":
                                kjcg(bTeachersEntity,fl);
                                break;
                            case "荣誉奖励":
                                ryjl(bTeachersEntity,fl);
                                break;
                            case "工作经历":
                                gzjl(bTeachersEntity,fl);
                                break;
                            default:
                                break;
                        }
//                        System.out.println(title);
                    }


                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    /**
     * 工作经历
     * @param bTeachersEntity
     * @param element
     */
    private  void gzjl(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setGzjl(ul.html());
    }
    /**
     * 荣誉奖励
     * @param bTeachersEntity
     * @param element
     */
    private  void ryjl(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setRyjl(ul.html());
    }
    /**
     * 科技成果
     * @param bTeachersEntity
     * @param element
     */
    private  void kjcg(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setKjcg(ul.html());
    }
    /**
     * 论文
     * @param bTeachersEntity
     * @param element
     */
    private  void lunwen(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setLw(ul.html());
    }
    /**
     * 科研项目
     * @param bTeachersEntity
     * @param element
     */
    private  void kyxm(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setKyxm(ul.html());
    }
    /**
     * 研究领域
     * @param bTeachersEntity
     * @param element
     */
    private  void yjly(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setYjly(ul.html());
    }
    /**
     * 社会职务
     * @param bTeachersEntity
     * @param element
     */
    private  void shzw(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setShzw(ul.html());
    }
    /**
     * 个人简介
     * @param bTeachersEntity
     * @param element
     */
    private  void grjj(BTeachersEntity bTeachersEntity,Element element){
        Element ul = element.getElementsByAttributeValue("class", "con").get(0);
        bTeachersEntity.setGrjj(ul.html());
    }

    /**
     * 个人资料
     * @param bTeachersEntity
     * @param element
     */
    private void grzl(BTeachersEntity bTeachersEntity,Element element){
        Elements ul = element.getElementsByAttributeValue("class", "news_list news_text clearfix");
        Elements li = ul.get(0).getElementsByTag("li");
//        if(bTeachersEntity.getName().equals("瞿伟")){
//            System.out.println(bTeachersEntity.getName());
//        }
        for (Element item : li){
            Elements title=item.getElementsByAttributeValue("class", "title");
            if(title.size()==0)break;
            String name=item.getElementsByAttributeValue("class", "title").get(0).text();
            String value=item.getElementsByAttributeValue("class", "txt").get(0).text();
//            System.out.println(name+":"+value);
            switch (name){
                case "学院：":
                    bTeachersEntity.setCollege(value);
                    break;
                case "性别：":
                    bTeachersEntity.setSex(value);
                    break;
                case "联系电话：":
                    bTeachersEntity.setContact(value);
                    break;
                case "电子邮箱：":
                    bTeachersEntity.setEmail(value);
                    break;
                case "学历：":
                    bTeachersEntity.setEducation(value);
                    break;
                case "毕业院校：":
                    bTeachersEntity.setByyx(value);
                    break;
                case "办公地址：":
                    bTeachersEntity.setBgdd(value);
                    break;
                default:
                    break;
            }
        }

    }
}
