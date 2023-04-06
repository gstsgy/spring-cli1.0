package com.gstsgy.permission.service.impl;

import com.gstsgy.permission.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.bean.db.RoleDO;
import com.gstsgy.permission.bean.db.UserRoleDO;
import com.gstsgy.permission.mapper.OperatorMapper;
import com.gstsgy.permission.mapper.RoleMapper;
import com.gstsgy.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * @ClassName UserRoleServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/20 上午10:49
 **/
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public ResponseBean queryRoleByUser(Long userId) {
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId,userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        return ResponseBean.getSuccess(list);
    }

    @Override
    public ResponseBean queryUserByRole(Long roleId) {
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getRoleId,roleId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);

        return ResponseBean.getSuccess(list);
    }

    @Override
    public ResponseBean addRoleByUser(List<RoleDO> roles, Long userId) {
        // 删除原来的
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getUserId,userId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);
        list.forEach(item->{

            userRoleMapper.deleteById(item.getId());
        });
        // 新增
        roles.forEach(item->{
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(userId);
            userRoleDO.setRoleId(item.getId());
            userRoleMapper.insert(userRoleDO);
        });
        return ResponseBean.getSuccess(true);
    }

    @Override
    public ResponseBean addUserByRole(List<OperatorDO> users, Long roleId) {
        // 删除原来的
        QueryWrapper<UserRoleDO> query = new QueryWrapper<>();
        query.lambda().eq(UserRoleDO::getRoleId,roleId);
        List<UserRoleDO> list = userRoleMapper.selectList(query);
        list.forEach(item->{

            userRoleMapper.deleteById(item.getId());
        });
        // 新增
        users.forEach(item->{
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(item.getId());
            userRoleDO.setRoleId(roleId);
            userRoleMapper.insert(userRoleDO);
        });
        return ResponseBean.getSuccess(true);
    }

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public ResponseBean queryRoles() {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        List<RoleDO> list = roleMapper.selectList(query);
        list.sort(Comparator.comparing(RoleDO::getName));

        return ResponseBean.getSuccess(list);
    }

    @Override
    public ResponseBean queryUsers() {
        QueryWrapper<OperatorDO> query = new QueryWrapper<>();
        query.lambda().ne(OperatorDO::getId,1);
        List<OperatorDO> list = operatorMapper.selectList(query);
        list.sort(Comparator.comparing(OperatorDO::getNickName));

        return ResponseBean.getSuccess(list);
    }
}
