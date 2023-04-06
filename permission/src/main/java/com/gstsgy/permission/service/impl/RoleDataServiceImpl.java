package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.service.RoleDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.RoleDataDO;
import com.gstsgy.permission.bean.db.UserRoleDO;
import com.gstsgy.permission.mapper.RoleDataMapper;
import com.gstsgy.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName RoleMenuServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/23 上午10:42
 **/
@Service
@Transactional
public class RoleDataServiceImpl implements RoleDataService {
    @Autowired
    private RoleDataMapper roleDataMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseBean getAllWarehouseByRole(Long roleId) {
        return ResponseBean.getSuccess(roleDataMapper.selectWarehouseIdByRole(roleId));
    }

    @Override
    public ResponseBean saveData(Long roleId, List<String> warehouses) {


        QueryWrapper<RoleDataDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleDataDO::getRoleId, roleId);
        // 删除旧的
        List<RoleDataDO> listOld = roleDataMapper.selectList(queryWrapper);
        listOld.forEach(item -> {
            if (!warehouses.contains(item.getWarehouseId())) {

                roleDataMapper.deleteById(item.getId());
            }
        });
        List<String> listOldMenu = listOld.stream().map(RoleDataDO::getWarehouseId).collect(Collectors.toList());

        // 新增新的
        warehouses.forEach(item -> {
            if (!listOldMenu.contains(item)) {

                RoleDataDO roleMenuDO = new RoleDataDO();
                roleMenuDO.setRoleId(roleId);
                roleMenuDO.setWarehouseId(item);
                roleDataMapper.insert(roleMenuDO);
            }
        });
        return ResponseBean.getSuccess(true);
    }

    @Override
    public List<String> getAllWare(Long userId) {
        if (userId == 1) {

            return roleDataMapper.selectWarehouses();
        }

        // 1  通过用户 查询角色
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId, userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        return list.stream().flatMap(item -> roleDataMapper.
                selectWarehouseIdByRole(item.getRoleId()).stream()).filter(Objects::nonNull).distinct().collect(Collectors.toList());

    }
}
