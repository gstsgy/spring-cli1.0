package com.gstsgy.permission.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gstsgy.permission.bean.db.MenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName MenuMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:15
 **/
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuDO> {
}
