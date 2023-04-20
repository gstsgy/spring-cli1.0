package com.gstsgy.permission.service.impl;

import com.gstsgy.base.model.LangCode;
import com.gstsgy.base.utils.DateUtil;
import com.gstsgy.base.utils.Encrypt;
import com.gstsgy.permission.bean.view.WebEnumVO;
import com.gstsgy.permission.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.bean.db.UserRoleDO;
import com.gstsgy.permission.bean.view.OperatorVO;
import com.gstsgy.permission.mapper.OperatorMapper;
import com.gstsgy.permission.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:36
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private OperatorMapper operatorMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

//    @OperatorDefaultPassWd
//    private String defaultPassWord;

    @Override
    public ResponseBean addUser(OperatorDO operatorDO) {

        if (operatorDO.getDeptId() == null) {
            return ResponseBean.getParamEmptyException(LangCode.of("permission.dept"));
        }


        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getCode, operatorDO.getCode());

        OperatorDO operator = operatorMapper.selectOne(queryWrapper);
        if (operator != null) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("core.unique"), LangCode.of("permission.user_code"));
        }
        if (StringUtils.hasLength(operatorDO.getEmail())) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OperatorDO::getEmail, operatorDO.getEmail());

            operator = operatorMapper.selectOne(queryWrapper);
            if (operator != null) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("core.no_unique"), LangCode.of("permission.email"));
            }
        }
        operatorDO.setPasswdUpdateYmd(DateUtil.getDate());
        operatorDO.setPasswd("defaultPassWord");
        operatorDO.setPasswd(encrypt(operatorDO));
        return ResponseBean.getSuccess(operatorMapper.insert(operatorDO) > 0);
    }

    @Override
    public ResponseBean deleteUser(List<OperatorDO> operatorDOs) {

        operatorDOs.forEach(item -> {
            if (item.getId() != 1) {
                OperatorDO operatorDO = operatorMapper.selectById(item.getId());
                operatorMapper.deleteById(operatorDO.getId());
                // 删除用户角色
                QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(UserRoleDO::getUserId, operatorDO.getId());
                List<UserRoleDO> tmps = userRoleMapper.selectList(queryWrapper);
                tmps.forEach(it -> userRoleMapper.deleteById(it.getId()));
            }
        });
        return ResponseBean.getSuccess(true);
    }

    @Override
    public ResponseBean queryUser(OperatorDO operatorDO, Integer pageNum, Integer pageSize) {
        Page<OperatorVO> page = new Page<>(pageNum, pageSize);
        operatorMapper.selectAllUser(page, operatorDO);
        return ResponseBean.getSuccess(page);
    }

    @Override
    public ResponseBean updateUser(OperatorDO newOperatorDO) {

        if (newOperatorDO.getId() == 1) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("permission.admin_disable_update"));
        }
        OperatorDO oldOperatorDO = operatorMapper.selectById(newOperatorDO.getId());
        if (oldOperatorDO == null) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("core.empty"), LangCode.of("permission.user"));
        }
        // 原先没有邮箱  更新后有邮箱  则判断 邮箱是否重复
        if (StringUtils.hasLength(newOperatorDO.getEmail()) && !StringUtils.hasLength(oldOperatorDO.getEmail())) {
            QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OperatorDO::getEmail, newOperatorDO.getEmail());

            OperatorDO operator = operatorMapper.selectOne(queryWrapper);
            if (operator != null) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("core.no_unique"), LangCode.of("permission.email"));
            }
        }
        // 原先有邮箱  更新后有邮箱  原先的邮箱不等于现在的 则判断 邮箱是否重复
        if (StringUtils.hasLength(newOperatorDO.getEmail()) &&
                StringUtils.hasLength(oldOperatorDO.getEmail()) &&
                !newOperatorDO.getEmail().equals(oldOperatorDO.getEmail())
        ) {
            QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OperatorDO::getEmail, newOperatorDO.getEmail());

            OperatorDO operator = operatorMapper.selectOne(queryWrapper);
            if (operator != null) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("core.no_unique"), LangCode.of("permission.email"));
            }
        }

        newOperatorDO.setPasswd(oldOperatorDO.getPasswd());
        return ResponseBean.getSuccess(operatorMapper.updateById(newOperatorDO) > 0);
    }

    @Override
    public ResponseBean getUserPosition() {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct position");
        return ResponseBean.getSuccess(operatorMapper.selectList(queryWrapper).stream().map(OperatorDO::getPosition).collect(Collectors.toList()));
    }


    @Override
    public ResponseBean updateUserpw(OperatorDO user, String oldPwd) throws Exception {

        user.setPasswd(user.getPasswd());

        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getCode, user.getCode());
        OperatorDO operator = operatorMapper.selectOne(queryWrapper);

        if (operator != null) {
            if (operator.getId().equals(1L)) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.admin_disable_update"));
            }
            if (!StringUtils.hasLength(oldPwd)) {
                return ResponseBean.getParamEmptyException(LangCode.of("permission.old_passwd"));
            }
            OperatorDO old = operator.clone();
            old.setPasswd(oldPwd);


            if (!Objects.equals(encrypt(old), operator.getPasswd())) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.erroneous"), LangCode.of("permission.old_passwd"));
            }
            if (Objects.equals(encrypt(old), encrypt(user))) {
                return ResponseBean.getParamUnmatchedException(LangCode.of("permission.old_passwd_not_equals_new"));
            }
            operator.setPasswdUpdateYmd(DateUtil.getDate());
            operator.setPasswd(encrypt(user));
            operatorMapper.updateById(operator);
            return ResponseBean.getSuccess(true);
        } else {
            return ResponseBean.getParamUnmatchedException(LangCode.of("permission.erroneous"), LangCode.of("permission.user_code"));
        }
    }

    @Override
    public OperatorDO queryOne(String code) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getCode, code);
        return operatorMapper.selectOne(queryWrapper);

    }

    @Override
    public OperatorDO queryOneByEmail(String email) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OperatorDO::getEmail, email);
        return operatorMapper.selectOne(queryWrapper);
    }

    @Override
    public OperatorDO queryOne(Long userId) {
        return operatorMapper.selectById(userId);
    }

    @Override
    public ResponseBean queryUserEnum(Long deptId) {
        QueryWrapper<OperatorDO> queryWrapper = new QueryWrapper<>();
        if (deptId != null) {
            queryWrapper.lambda().eq(OperatorDO::getDeptId, deptId);
        }
        List<OperatorDO> list = operatorMapper.selectList(queryWrapper);
        List<WebEnumVO> resultData = list.stream().map(it -> new WebEnumVO(it.getId(), it.getNickName())).collect(Collectors.toList());
        return ResponseBean.getSuccess(resultData);
    }

    @Override
    public ResponseBean restpassword(OperatorDO user) {


        OperatorDO tmp = operatorMapper.selectById(user.getId());
        if (tmp.getId().equals(1L)) {
            return ResponseBean.getParamUnmatchedException(LangCode.of("permission.admin_disable_update"));
        }
        tmp.setPasswdUpdateYmd(DateUtil.getDate());
        tmp.setPasswd("admin123.");
        tmp.setPasswd(encrypt(tmp));
        operatorMapper.updateById(tmp);
        return ResponseBean.getSuccess(true);
    }

    @Override
    public String encrypt(OperatorDO user) {
        return Encrypt.encryptToMD5(user.getCode() + user.getPasswd());
    }
}
