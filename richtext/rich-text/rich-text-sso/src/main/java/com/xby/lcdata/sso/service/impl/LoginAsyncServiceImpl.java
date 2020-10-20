package com.xby.lcdata.sso.service.impl;

import com.sticker.online.core.anno.AsyncServiceHandler;
import com.sticker.online.core.model.BaseAsyncService;
import com.sticker.online.core.utils.*;
import com.xby.lcdata.sso.entity.SysLoginLog;
import com.xby.lcdata.sso.entity.SysUser;
import com.xby.lcdata.sso.entity.SysUsersRoles;
import com.xby.lcdata.sso.entity.WWechatUser;
import com.xby.lcdata.sso.repository.SysLoginLogRepository;
import com.xby.lcdata.sso.repository.SsoSysUserRepository;
import com.xby.lcdata.sso.repository.SysUsersRolesRepository;
import com.xby.lcdata.sso.repository.WWechatUserRepository;
import com.xby.lcdata.sso.service.LoginAsyncService;
import com.xby.lcdata.sso.util.CloudMessage;
import com.xby.lcdata.sso.util.RedisUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName: LoginAsyncServiceImpl
 * @Description:
 * @Author: wangf
 * @Date: 2019/11/19 0019 9:54
 * @Version: 1.0
 **/
@Component
@AsyncServiceHandler
public class LoginAsyncServiceImpl implements LoginAsyncService, BaseAsyncService {

    @Autowired
    private SsoSysUserRepository sysUserRepository;
    @Autowired
    private SysLoginLogRepository sysLoginLogRepository;
    @Autowired
    private SysUsersRolesRepository sysUsersRolesRepository;
    @Autowired
    private WWechatUserRepository wWechatUserRepository;

    private final String PREFIX_USER_INFO = "PREFIX_USER_INFO_";

    /**
     * 登录
     *
     * @param params
     * @param handler
     */
    @Override
    public void login(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String username = params.getString("username");
        String password = params.getString("password");
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setIp(params.getString("ip"));
        sysLoginLog.setLoginTime(TimeUtil.getNow());
        String oldToken = String.valueOf(RedisUtil.get(CommonConstant.PREFIX_USER_TOKEN + username));
        if (oConvertUtils.isNotEmpty(oldToken)) {
            RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + oldToken);
            String userId = JwtUtil.getUserInfo(oldToken).getString("userid");
            RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + username);
            RedisUtil.del(PREFIX_USER_INFO + userId);
        }
        SysUser user = sysUserRepository.getSUserByUsernameEquals(username);
        if (oConvertUtils.isNotEmpty(user)) {
            //密码验证
            String userpassword = PasswordUtil.encrypt(username, password, user.getSalt());
            String syspassword = user.getPassword();
            if (!syspassword.equals(userpassword)) {
                future.fail("用户名或密码错误!");
                sysLoginLog.setLoginResult("登录失败!用户名或密码错误!");
                sysLoginLogRepository.save(sysLoginLog);
            } else {
                JsonObject resultJson = new JsonObject();
                //生成用户数据请求token
                String token = JwtUtil.sign(username, syspassword, user.getId());
                user.setPassword(null);
                user.setSalt(null);
                resultJson.put("token", token);

//                    //生成用于刷新Token有效期的Token
//                    String refreshToken = JwtUtil.sign(username, syspassword, user.getId());
//                    resultJson.put("refreshToken", refreshToken);
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
//                    long tokenPeriodTime = calendar.getTimeInMillis();
//                    resultJson.put("tokenPeriodTime", tokenPeriodTime);

                resultJson.put("userInfo", user.toJson());
                user.setPassword(null);
                user.setSalt(null);
                saveToken(token);//保存Token
                RedisUtil.set(CommonConstant.PREFIX_USER_TOKEN + username, token);
                RedisUtil.set(PREFIX_USER_INFO + user.getId(), user.toJson().toString());
                RedisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + username, JwtUtil.EXPIRE_TIME / 250);
                RedisUtil.expire(PREFIX_USER_INFO + user.getId(), JwtUtil.EXPIRE_TIME / 250);
                //有效时间为5天
//                    RedisUtil.set(PREFIX_REFRESH + refreshToken, refreshToken);
//                    RedisUtil.expire(PREFIX_REFRESH + refreshToken, JwtUtil.EXPIRE_TIME - 380);
                sysLoginLog.setLoginResult("登录成功!");
                sysLoginLogRepository.save(sysLoginLog);
                System.out.println("************************************登录成功!");
                future.complete(resultJson);
            }
        } else {
            future.fail("用户不存在!");
        }

        handler.handle(future);
    }

    /**
     * 用户注册
     *
     * @param params
     * @param handler
     */
    @Override
    public void register(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        try {
            SysUser sysUser = new SysUser();
            String salt = oConvertUtils.randomGen(8);
            String username = params.getString("username");
            String password = params.getString("password");
            String roleId = params.getString("roleId");
            sysUser.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(username, password, salt);
            sysUser.setUsername(username);
            sysUser.setRealname(username);
            sysUser.setStatus(1);
            sysUser.setDelFlag(0);
            sysUser.setCreateTime(TimeUtil.getNow());
            sysUser.setPassword(passwordEncode);
            sysUser.setPhone(params.getString("phone"));
            SysUser registerUser = sysUserRepository.save(sysUser);
            SysUsersRoles sysUsersRoles = new SysUsersRoles();
            sysUsersRoles.setRoleId(roleId);
            sysUsersRoles.setUserId(registerUser.getId());
            sysUsersRolesRepository.save(sysUsersRoles);
            future.complete("注册成功!");
        } catch (Exception e) {
            e.printStackTrace();
            future.fail("注册失败!" + e.getCause().getMessage());
        }
        handler.handle(future);
    }

    /**
     * 退出
     *
     * @param params
     * @param handler
     */
    @Override
    public void logout(JsonObject params, Handler<AsyncResult<String>> handler) {
        Future<String> future = Future.future();
        String token = params.getString("token");
        RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);

//        String refreshToken = params.getString("refreshToken");
//        RedisUtil.del(PREFIX_REFRESH + refreshToken);

        String username = JwtUtil.getUserNameByToken(token);
        String userId = JwtUtil.getUserInfo(token).getString("userid");
        RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + username);
        RedisUtil.del(PREFIX_USER_INFO + userId);
        future.complete("已退出登录");
        handler.handle(future);
    }

    /**
     * 验证Token是否有效
     *
     * @param params
     * @param handler
     */
    @Override
    public void verifyToken(JsonObject params, Handler<AsyncResult<Boolean>> handler) {
        Future<Boolean> future = Future.future();
        String token = params.getString("token");
        String userToken = String.valueOf(RedisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(userToken)) {
            future.complete(true);
        } else {
            future.complete(false);
        }
        handler.handle(future);
    }

    /**
     * 刷新Token
     *
     * @param params
     * @param handler
     */
    @Override
    public void refreshToken(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
//        String refreshToken = params.getString("refreshToken");
//        String userRefreshToken = String.valueOf(RedisUtil.get(PREFIX_REFRESH + refreshToken));
//        if (oConvertUtils.isNotEmpty(userRefreshToken)) {
//            JsonObject userInfo = JwtUtil.getUserInfo(userRefreshToken);
//            String username = userInfo.getString("username");
//            String syspassword = userInfo.getString("password");
//            String userId = userInfo.getString("userid");
//            String token = JwtUtil.sign(username, syspassword, userId);
//            saveToken(token);
//            future.complete(new JsonObject().put("success", true).put("token", token));
//        } else {
//            future.complete(new JsonObject().put("success", false).put("token", "登录已失效!"));
//        }
        handler.handle(future);
    }

    /**
     * 根据Token从Redis中获取用户信息
     *
     * @param params
     * @param handler
     */
    @Override
    public void getUserByToken(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String token = params.getString("token");
        String userToken = String.valueOf(RedisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (oConvertUtils.isNotEmpty(userToken)) {
            String userId = JwtUtil.getUserInfo(token).getString("userid");
            String userInfo = String.valueOf(RedisUtil.get(PREFIX_USER_INFO + userId));
            future.complete(new JsonObject(userInfo));
        } else {
            future.fail("Token已失效!请重新登录!");
        }
        handler.handle(future);
    }

    /**
     * 获取短信验证码
     *
     * @param params
     * @param handler
     */
    @Override
    public void getVerificationCode(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        String phone = params.getString("phone");
        try {
            //  指定手机号码、模板id、短信类型（verificationCode：验证码，inform：通知类）
            JsonObject msgInfo = new JsonObject(CloudMessage.sendCode(phone, "14798522", "verificationCode"));
            String verificationCode = msgInfo.getString("obj");
            JsonObject resultJson = new JsonObject();
            if (verificationCode != null) {
                resultJson.put("verificationCode", verificationCode);
                resultJson.put("explain", "有效期为10分钟");
            } else {
                resultJson.put("msg", "验证码发送失败!" + msgInfo.getString("msg"));
            }
            future.complete(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
            future.fail("验证码获取失败!" + e.getMessage());
        }
        handler.handle(future);
    }

    public void saveToken(String token) {
        RedisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        //有效时间为2小时
        RedisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 250);
    }


    @Override
    public void loginByWechat(JsonObject params, Handler<AsyncResult<JsonObject>> handler) {
        Future<JsonObject> future = Future.future();
        WWechatUser wWechatUser=wWechatUserRepository.findByOpenId(params.getString("openId"));

        if(wWechatUser!=null){
            if(oConvertUtils.isNotEmpty(wWechatUser.getUserId())){
                Optional<SysUser> sysUser=sysUserRepository.findById(wWechatUser.getUserId());
                if(sysUser.get().getStatus()==1){
                    String username = sysUser.get().getUsername();
                    String password = sysUser.get().getPassword();
                    SysLoginLog sysLoginLog = new SysLoginLog();
                    sysLoginLog.setUsername(username);
                    sysLoginLog.setIp(params.getString("ip"));
                    sysLoginLog.setLoginTime(TimeUtil.getNow());
                    String oldToken = String.valueOf(RedisUtil.get(CommonConstant.PREFIX_USER_TOKEN + username));
                    if (oConvertUtils.isNotEmpty(oldToken)) {
                        RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + oldToken);
                        String userId = JwtUtil.getUserInfo(oldToken).getString("userid");
                        RedisUtil.del(CommonConstant.PREFIX_USER_TOKEN + username);
                        RedisUtil.del(PREFIX_USER_INFO + userId);
                    }
                    //密码验证
                    JsonObject resultJson = new JsonObject();
                    //生成用户数据请求token
                    String token = JwtUtil.sign(username, password, sysUser.get().getId());
                    sysUser.get().setPassword(null);
                    sysUser.get().setSalt(null);
                    resultJson.put("token", token);
                    resultJson.put("userInfo", sysUser.get().toJson());
                    sysUser.get().setPassword(null);
                    sysUser.get().setSalt(null);
                    saveToken(token);//保存Token
                    RedisUtil.set(CommonConstant.PREFIX_USER_TOKEN + username, token);
                    RedisUtil.set(PREFIX_USER_INFO + sysUser.get().getId(), sysUser.get().toJson().toString());
                    RedisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + username, JwtUtil.EXPIRE_TIME / 250);
                    RedisUtil.expire(PREFIX_USER_INFO + sysUser.get().getId(), JwtUtil.EXPIRE_TIME / 250);
                    sysLoginLog.setLoginResult("登录成功!");
                    sysLoginLogRepository.save(sysLoginLog);
                    System.out.println("************************************登录成功!");
                    future.complete(resultJson);

                }else{
                    future.fail("该账户已被冻结，请联系管理员处理！");
                }
            }else{
                future.fail("请联系管理员绑定账户！");
            }
        }else{
            WWechatUser wWechatUserIn=new WWechatUser(params);
            wWechatUserRepository.save(wWechatUserIn);
            future.fail("请联系管理员绑定账户！");
        }

        handler.handle(future);

    }

}
