package com.gstsgy.permission.mapper;

import com.gstsgy.permission.bean.db.RoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RoleMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:16
 **/
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<RoleDO> {
}
