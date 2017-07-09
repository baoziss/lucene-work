package com.baizhi.test;

import com.baizhi.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public class TestLogin {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("client.xml");
        UserService userService = (UserService) context.getBean("userService");
        Map<String, String> result = userService.login("1", "zhangsan", "123456");
        System.out.println(result);
    }
}
