package com.gstsgy.permission.mapper;

import com.gstsgy.permission.bean.db.MenuDO;
import com.gstsgy.permission.bean.db.RoleMenuDO;
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
public interface RoleMenuMapper extends BaseMapper<RoleMenuDO> {
    @Select("select m.* from role_menu r,menu m\n" +
            "    where r.menu_id = m.id\n" +
            "      and r.effective = 1\n" +
            "      and m.effective = 1\n" +
            "      and r.role_id = #{roleId}\n" +
            "      and m.type = #{type}")
    List<MenuDO> selectMenuIdByRole(@Param("roleId") Long roleId, @Param("type") Integer type);
}
