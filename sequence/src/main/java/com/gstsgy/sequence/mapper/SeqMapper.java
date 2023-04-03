package com.gstsgy.sequence.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gstsgy.sequence.bean.Sequence;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SeqMapper extends BaseMapper<Sequence> {
    @Select("show tables like 'g_sequence'")
    String getMysqlTable();

    @Select("select name from sysobjects where xtype='U' and name =  'g_sequence'")
    String getMssqlTable();

    @Select("select tablename from pg_tables where schemaname='public' and tablename = 'g_sequence'")
    String getPostgresqlTable();
}
