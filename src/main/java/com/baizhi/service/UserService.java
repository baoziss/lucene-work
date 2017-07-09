package com.baizhi.service;


import com.baizhi.entity.Server;
import com.baizhi.entity.Token;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/1.
 */
@Service
public interface UserService {
    //用户登录
    Map<String,Object> login(String appID, String name, String pwd);

    //业务票据授权
    Server getServer(String appID, String token);

    //令牌校验
    boolean isToken(String token);

    //用户退出
    String logout(String token);
}
