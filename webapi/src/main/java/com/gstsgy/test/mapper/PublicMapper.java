package com.gstsgy.test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
@Mapper
public interface PublicMapper {
    @Select("${sql}")
    List<Map> query(@Param("sql") String sql);
}
