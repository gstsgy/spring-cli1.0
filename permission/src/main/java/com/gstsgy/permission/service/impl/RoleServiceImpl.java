package com.gstsgy.permission.service.impl;

import com.gstsgy.base.model.LangCode;
import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.RoleDO;
import com.gstsgy.permission.bean.db.UserRoleDO;
import com.gstsgy.permission.mapper.RoleMapper;
import com.gstsgy.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 下午3:27
 **/
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseBean addRole(RoleDO roleDO) {
        if (roleDO == null || roleDO.getName() == null || roleDO.getName().equals("")) {
            return ResponseBean.getParamEmptyException(LangCode.of("permission.role_name"));
        }
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        query.lambda().eq(RoleDO::getName, roleDO.getName());
        long count = roleMapper.selectCount(query);
        if (count > 0) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("core.name_unique"),LangCode.of("permission.role"));
        }

        return ResponseBean.getSuccess(roleMapper.insert(roleDO) > 0);
    }

    @Override
    public ResponseBean deleteRoles(List<RoleDO> roleDOs) {

        roleDOs.forEach(item -> {

            RoleDO roleDO1 = roleMapper.selectById(item.getId());

            roleMapper.deleteById(roleDO1.getId());
            // 删除用户角色
            QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper();
            queryWrapper.lambda().eq(UserRoleDO::getRoleId, item.getId());
            List<UserRoleDO> tmps = userRoleMapper.selectList(queryWrapper);
            tmps.forEach(it -> {
                userRoleMapper.deleteById(it.getId());
            });

        });
        return ResponseBean.getSuccess(true);
    }

    @Override
    public ResponseBean queryRoles(String name, Integer pageSize, Integer pageNum) {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        if (name != null && !"".equals(name)) {
            query.lambda().like(RoleDO::getName, name);
        }
        IPage<RoleDO> page = new Page<>(pageNum, pageSize);
        page =roleMapper.selectPage(page, query);

        return ResponseBean.getSuccess(page);
    }

    @Override
    public ResponseBean updateRole(RoleDO roleDO) {

        RoleDO roleDO1 = roleMapper.selectById(roleDO.getId());
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        query.lambda().eq(RoleDO::getName, roleDO.getName());
        long count = roleMapper.selectCount(query);
        if (count > 0&&!roleDO1.getName().equals(roleDO.getName())) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("core.name_unique"),LangCode.of("permission.role"));
        }

        roleDO1.setName(roleDO.getName());
        roleDO1.setDesc(roleDO.getDesc());
        return ResponseBean.getSuccess(roleMapper.updateById(roleDO1) > 0);
    }

    @Override
    public ResponseBean queryRoleEnum() {
        QueryWrapper<RoleDO> query = new QueryWrapper<>();
        List<RoleDO> list = roleMapper.selectList(query);
        List<WebEnumVO> resultData = list.stream().map(it->new WebEnumVO(it.getId(),it.getName())).collect(Collectors.toList());
        return ResponseBean.getSuccess(resultData);
    }
}
