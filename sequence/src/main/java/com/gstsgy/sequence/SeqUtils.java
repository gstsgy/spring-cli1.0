package com.gstsgy.sequence;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.gstsgy.base.utils.DateUtil;
import com.gstsgy.base.utils.SpringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gstsgy.sequence.bean.Sequence;
import com.gstsgy.sequence.mapper.SeqMapper;
import com.gstsgy.sequence.util.SnowFlake;

import javax.sql.DataSource;

public final class SeqUtils {
    private SeqUtils(){}
    private static SeqMapper sequenceMapper;

    private static final Map<String, Object> lockObjs = new HashMap<>();
    public static long getSeq(){
        return SnowFlake.generate();
    }

    public static long getSeq(Sequence sequence){
        if (!isInit) {
            init();
        }
        String lockKey = sequence.getGKey() + "-" + sequence.getSubkey();
        Object lockObj = lockObjs.get(lockKey);
        if (lockObj == null) {
            synchronized (SeqUtils.class) {
                lockObj = lockObjs.computeIfAbsent(lockKey, k -> new Object());
            }
        }
        synchronized (lockObj) {
            Sequence sequenceDO = select(sequence.getGKey(), sequence.getSubkey());
            if (sequenceDO == null) {
                sequenceDO = sequence;
                sequenceDO.setId(SnowFlake.generate());
                sequenceDO.setLastday(DateUtil.getYYMMdd());
                sequenceMapper.insert(sequenceDO);
            } else {
                if (sequenceDO.getValue() > sequenceDO.getMaxnum()) {
                    sequenceDO.setValue(sequenceDO.getStartnum());
                    rest(sequenceDO.getId(), DateUtil.getYYMMdd());
                } else if (sequenceDO.isDayresetting() && !DateUtil.getYYMMdd().equals(sequenceDO.getLastday())) {
                    sequenceDO.setValue(sequenceDO.getStartnum());
                    rest(sequenceDO.getId(), DateUtil.getYYMMdd());
                } else {
                    sequenceDO.setValue(sequenceDO.getValue() + sequenceDO.getStep());
                    sequenceMapper.updateById(sequenceDO);
                }
            }
            return sequenceDO.getValue();
        }
    }

    private static Sequence select(String key, String subKey) {
        QueryWrapper<Sequence> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Sequence::getGKey, key);
        if (StringUtils.hasText(subKey)) {
            queryWrapper.lambda().eq(Sequence::getSubkey, subKey);
        }
        return sequenceMapper.selectOne(queryWrapper);
    }

    //update sequence set value = start_num,last_day = #{lastday} where id = #{id}

    private static void rest(long id,String lastday){
        UpdateWrapper<Sequence> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(Sequence::getValue,"startnum").
                set(Sequence::getLastday,lastday).
                eq(Sequence::getId,id);
        sequenceMapper.update(null,updateWrapper);
    }

    // 初始化 判断表是否存在 不存在 则新建
    private static boolean isInit;

    private static void init() {
        isInit = true;

        if (sequenceMapper == null) {
            sequenceMapper = SpringUtils.getBean(SeqMapper.class);
        }
        boolean tableExist = false;
        String dbtype = "";
        DataSource dataSource = SpringUtils.getBean(DataSource.class);

        //通过数据源获取数据库链接
        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            if (databaseProductName.contains("SQL Server")) {
                dbtype = "mssql";
                tableExist = sequenceMapper.getMssqlTable() != null;
            } else if (databaseProductName.contains("MySQL")) {
                dbtype = "mysql";
                tableExist = sequenceMapper.getMysqlTable() != null;
            } else if (databaseProductName.contains("PostgreSQL")) {
                dbtype = "postgresql";
                tableExist = sequenceMapper.getPostgresqlTable() != null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (tableExist) {
            return;
        }

        //创建脚本执行器
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        //创建字符输出流，用于记录SQL执行日志
        StringWriter writer = new StringWriter();
        PrintWriter print = new PrintWriter(writer);
        //设置执行器日志输出
        scriptRunner.setLogWriter(print);
        //设置执行器错误日志输出
        scriptRunner.setErrorLogWriter(print);
        //设置读取文件格式
        Resources.setCharset(StandardCharsets.UTF_8);
        String filePath = String.format("mybatis/initsql/%s/init.sql", dbtype);

        Reader reader = null;
        try {
            //获取资源文件的字符输入流
            reader = Resources.getResourceAsReader(filePath);
        } catch (IOException e) {
            //文件流获取失败，关闭链接
            scriptRunner.closeConnection();
            return;
        }
        //执行SQL脚本
        scriptRunner.runScript(reader);
        //关闭文件输入流
        try {
            reader.close();
        } catch (IOException ignored) {
        }
        //关闭输入流
        scriptRunner.closeConnection();
    }
}
