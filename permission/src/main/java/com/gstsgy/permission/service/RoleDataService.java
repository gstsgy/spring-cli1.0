package com.gstsgy.permission.service;

import com.gstsgy.base.model.ResponseBean;

import java.util.List;

/**
 * @ClassName RoleMenuService
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:41
 **/
public interface RoleDataService {


    ResponseBean getAllWarehouseByRole(Long roleId);

    ResponseBean saveData(Long roleId, List<String> warehouses);

    List<String> getAllWare(Long userId);

}
