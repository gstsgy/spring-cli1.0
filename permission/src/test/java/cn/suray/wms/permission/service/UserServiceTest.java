package com.gstsgy.permission.service;

import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.service.impl.UserServiceImpl;

public class UserServiceTest {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        OperatorDO operatorDO = new OperatorDO();
        operatorDO.setCode("root");
        operatorDO.setPasswd("suray2020");
        System.out.println(userService.encrypt(operatorDO));
        // root 密码 77A9E9F8CECA87252EA9D0C3F6D3AE70
    }
}
