package com.gstsgy.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gstsgy.permission.bean.db.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName DeptMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 上午9:57
 **/
@Mapper
@Repository
public interface DeptMapper extends BaseMapper<DeptDO> {
}
