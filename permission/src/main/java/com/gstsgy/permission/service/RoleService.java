package com.gstsgy.permission.service;

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.RoleDO;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午3:21
 **/
public interface RoleService {
    ResponseBean addRole(RoleDO roleDO);

    ResponseBean deleteRoles(List<RoleDO> roleDOs);

    ResponseBean queryRoles(String name,Integer pageSize,Integer pageNum);

    ResponseBean updateRole(RoleDO roleDO);

    ResponseBean queryRoleEnum();
}
