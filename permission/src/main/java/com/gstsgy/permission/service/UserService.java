package com.gstsgy.permission.service;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:29
 **/
public interface UserService {
    ResponseBean addUser(OperatorDO operatorDO);
    //@CacheEvict(value = {"user"})
    ResponseBean deleteUser(List<OperatorDO> operatorDOs);

    ResponseBean queryUser(OperatorDO operatorDO, Integer pageNum, Integer pageSize);
    //@CacheEvict(value = {"user"},  key = "#operatorDO.id")
    ResponseBean updateUser(OperatorDO operatorDO);

    ResponseBean getUserPosition();

    /*ResponseBean sendMail(String code);*/
    //@CacheEvict(value = {"user"},  key = "#user.id")
    ResponseBean updateUserpw(OperatorDO user, String oldPwd) throws Exception;

    OperatorDO queryOne(String code);

    OperatorDO queryOneByEmail(String email);

    //@Cacheable(value = {"user"}, /*keyGenerator = "myKeyGenerator",*/key = "#p0",condition="#p0!=null")
    OperatorDO queryOne(Long id);

    ResponseBean queryUserEnum(Long deptId);

    //@CacheEvict(value = {"user"},  key = "#user.id")
    ResponseBean restpassword(OperatorDO user);

    String encrypt(OperatorDO user);
}
