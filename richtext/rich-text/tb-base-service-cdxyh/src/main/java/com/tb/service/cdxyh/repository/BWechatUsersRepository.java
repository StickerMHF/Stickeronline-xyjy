package com.tb.service.cdxyh.repository;

import com.google.inject.internal.cglib.proxy.$FixedValue;
import com.tb.service.cdxyh.entity.BWechatUsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BWechatUsersRepository extends JpaRepository<BWechatUsersEntity, String> {
    void deleteByOpenidEquals (String id);

    @Query(value = "SELECT a.name,a.id,a.avatar_url as avatarUrl,b.attention FROM w_wechat_users a left JOIN (SELECT member_id,count(*) as attention from b_wechat_users_attention where user_id=?1 GROUP BY member_id) b ON a.id=b.member_id limit ?2 offset ?3", nativeQuery = true)
    List<Map<String,Object>> findAllListByUserId(String userId, Integer pageSize, Integer offset);

    /**
     * 我的粉丝
     * @param userId
     * @param pageSize
     * @param offset
     * @return
     */
//    @Query(value = "SELECT b.* FROM b_wechat_users_attention a left JOIN w_wechat_users b ON a.user_id=b.id where a.member_id=?1 limit ?2 offset ?3", nativeQuery = true)
//    List<BWechatUsersEntity> queryFansListByUserId(String userId, Integer pageSize, Integer offset);
    @Query(value = "SELECT b.id,b.nick_name,b.avatar_url FROM b_wechat_users_attention a left JOIN w_wechat_users b ON a.user_id=b.id where a.member_id=?1 limit ?2 offset ?3", nativeQuery = true)
    List<BWechatUsersEntity> queryFansListByUserId(String userId, Integer pageSize, Integer offset);

    /**
     * 我关注的人
     * @param userId
     * @param pageSize
     * @param offset
     * @return
     */
    @Query(value = "SELECT b.* FROM b_wechat_users_attention a left JOIN w_wechat_users b ON a.member_id=b.id where a.user_id=?1 limit ?2 offset ?3", nativeQuery = true)
    List<BWechatUsersEntity> queryAttentionListByUserId(String userId, Integer pageSize, Integer offset);

    /**
     * 根据名字首字母进行分组
     * @return
     */
    @Query(value="select name_initial from w_wechat_users group by name_initial", nativeQuery=true)
    List<Map<String,Object>> groupByNameInitial();

    /**
     * 根据用户名字首字母获取用户列表
     * @return
     */
//    @Query(value="select * from w_wechat_users order by name_initial", nativeQuery=true)
//    List<BWechatUsersEntity> findAllOrderByNameDesc();

    @Query(value = "SELECT a.name,a.id,a.avatar_url,a.college,a.name_initial,b.attention FROM w_wechat_users a left JOIN (SELECT member_id,count(*) as attention from b_wechat_users_attention where user_id=?1 group by member_id) b ON a.id=b.member_id order by a.name_initial", nativeQuery = true)
    List<Map<String,Object>> findAllOrderByNameDesc(String userId);

    /**
     * 根据校友会ID获取校友用户
     * @param userId
     * @param pageSize
     * @param offset
     * @return
     */
    @Query(value = "select s.*,j.check_state,j.president from (SELECT a.name,a.id,a.avatar_url as avatarUrl,b.attention FROM w_wechat_users a left JOIN (SELECT member_id,count(*) as attention from b_wechat_users_attention where user_id=?1 GROUP BY member_id) b ON a.id=b.member_id) as s right JOIN b_alumnus_join as j on s.id=j.user_id where j.alumnus_id=?2 ORDER BY j.president desc limit ?3 offset ?4", nativeQuery = true)
    List<Map<String,Object>> queryListByAlumnusId(String userId,String alumnusId, Integer pageSize, Integer offset);


    @Query(value = "select a.id,a.sex,a.nick_name,a.avatar_url from w_wechat_users a where a.id=?1", nativeQuery = true)
    List<Map<String,Object>> getUserInfoByUserId(String openId);
}
