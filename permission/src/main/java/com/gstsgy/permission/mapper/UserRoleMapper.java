package com.gstsgy.permission.mapper;

import com.gstsgy.permission.bean.db.UserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRoleMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:18
 **/
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
}
