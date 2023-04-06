package com.gstsgy.permission.mapper;

import com.gstsgy.permission.bean.db.RoleDataDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName RoleMenuMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午3:17
 **/
@Mapper
@Repository
public interface RoleDataMapper extends BaseMapper<RoleDataDO> {
    @Select("select warehouse_id from role_data where effective=1 and  role_id = #{roleId}")
    List<String> selectWarehouseIdByRole(@Param("roleId") Long roleId);

    @Select("select distinct warehouse_id from warehouse where effective = 1")
    List<String> selectWarehouses();
}
