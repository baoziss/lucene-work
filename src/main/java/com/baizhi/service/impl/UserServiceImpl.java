package com.baizhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.entity.Server;
import com.baizhi.entity.Token;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.AESUtil;
import com.hyd.ssdb.SsdbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/1.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private SsdbClient ssdbClient;

    /**
     * 用户登录
     * @param appID
     * @param name
     * @param pwd
     * @return
     */
    public Map<String, Object> login(String appID, String name, String pwd) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Query query = new Query();
        //添加查询条件
        Criteria nameCriteria = new Criteria("name");
        nameCriteria.is(name);
        query.addCriteria(nameCriteria);
        List<User> users = mongoTemplate.find(query, User.class);
        try {
            if (users.size() == 0) {
                throw new RuntimeException("用户不存在！");
            } else {
                for (User user : users) {
                    if (pwd.equals(user.getPwd())) {
                        String s = null;
                        Token token = new Token();
                        token.setName(name);
                        token.setPwd(pwd);
                        try {
                            token.setIp(InetAddress.getLocalHost().getHostAddress());
                            token.setCreateTime(new Date());
                            String s1 = JSONObject.toJSONString(token);
                            //加密
                            s = AESUtil.aesEncrypt(s1, "123456");
                            //存储登录信息
                            ssdbClient.set(s,"1");
                            //返回登录提示以及业务票据
                            map.put("登录成功！",s);
                        } catch (Exception e) {
                            throw new RuntimeException(e.getMessage());
                        }
                        map.put(s, s);
                    } else {
                        throw new RuntimeException("密码错误！");
                    }
                }
            }
        } catch (Exception e) {
            map.put(e.getMessage(), null);
        }finally {
            ssdbClient.close();
        }
        return map;
    }

    /**
     * 获取业务票据
     * @param appID
     * @param token
     * @return
     */
    public Server getServer(String appID, String token) {
        //判断ssdb中是否存在用户令牌
        //存在则返回用户业务票据，否则返回为空
        if(isToken(token)){
            Query query = new Query();
            Criteria appCriteria = new Criteria("appID");
            appCriteria.is(appID);
            try {
                String s = AESUtil.aesDecrypt(token, "123456");
                Token token1 = JSONObject.parseObject(s, Token.class);
                appCriteria.and("name").is(token1.getName());

                List<Server> services = mongoTemplate.find(query, Server.class);
                for (Server service : services) {
                    return service;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    /**
     * 令牌校验
     * @param token
     * @return
     */
    public boolean isToken(String token) {
        boolean exists = ssdbClient.exists(token);
        ssdbClient.close();
        return exists;
    }

    /**
     * 用户退出
     * @param token
     */
    public String logout(String token) {
        try {
            ssdbClient.del(token);
        } catch (Exception e) {
            return "操作失败！";
        }finally {
            ssdbClient.close();
        }
        return "操作成功！";
    }
}
