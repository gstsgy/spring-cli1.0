package com.gstsgy.permission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gstsgy.permission.bean.db.OperatorDO;
import com.gstsgy.permission.bean.view.OperatorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName OperatorMapper
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/14 下午1:10
 **/
@Mapper
@Repository
public interface OperatorMapper extends BaseMapper<OperatorDO> {

    IPage<OperatorVO> selectAllUser(IPage<OperatorVO> page, @Param("operatorDO") OperatorDO operatorDO);
}
