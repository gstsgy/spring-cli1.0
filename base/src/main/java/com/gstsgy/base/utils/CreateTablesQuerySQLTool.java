package com.gstsgy.base.utils;


import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Classname CreateTablesQuerySQLTool
 * @Description TODO
 * @Date 2021/1/5 下午1:07
 * @Created by guyue
 *
 * */


public final class CreateTablesQuerySQLTool {
    static String Commas = ",";
    static String Periods = ".";
    static String and = "and";
    static String line = "\r\n";
    static String blank = "  ";
    static String effective = "effective";
    static String equal = "=";


    private CreateTablesQuerySQLTool() {
    }


    public static void createSql(Class param, Class resultType, List<Class> tableClasses, String paramValue) {
        List<TableInfo> tableInfos = tableClasses.stream().map(TableInfoHelper::getTableInfo).collect(Collectors.toList());
        Map<String, String> tableNames = new HashMap<>();
        for (TableInfo info : tableInfos) {
            if (!tableNames.containsKey(info.getKeyProperty())) {
                tableNames.put(info.getKeyProperty(), String.format("%s.%s", info.getTableName(), info.getKeyColumn()));
            }
            for (TableFieldInfo fieldInfo : info.getFieldList()) {
                if (!tableNames.containsKey(fieldInfo.getField().getName())) {
                    tableNames.put(fieldInfo.getField().getName(), String.format("%s.%s", info.getTableName(), fieldInfo.getColumn()));
                }
            }
        }

        // 获取selece语句
        String selectSql = selectSql(resultType, tableNames);

        //获取from语句
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(line);
        for (int i = 0; i < tableInfos.size(); i++) {

            stringBuilder.append(tableInfos.get(i).getTableName());
            if (i < tableInfos.size() - 1) {
                stringBuilder.append(Commas);
            }
        }
        String fromSql = stringBuilder.toString();

        StringBuffer logicDeleteFieldInfo = new StringBuffer();

        tableInfos.forEach(field -> {
            // 逻辑删除
            logicDeleteFieldInfo.append(line);
            logicDeleteFieldInfo.append(and);
            logicDeleteFieldInfo.append(blank);
            logicDeleteFieldInfo.append(field.getTableName());
            logicDeleteFieldInfo.append(Periods);
            logicDeleteFieldInfo.append(effective);
            logicDeleteFieldInfo.append(blank);
            logicDeleteFieldInfo.append(equal);
            logicDeleteFieldInfo.append(blank);
            logicDeleteFieldInfo.append("1");
        });


        String whereSql = whereSql(paramValue, param, tableNames);
        String sql = String.format("select %s from %s where 1 = 1 %s %s", selectSql, fromSql, logicDeleteFieldInfo.toString(), whereSql);
        System.err.println("表关联语句自己写");
        System.out.println(sql);

    }

    private static String selectSql(Class resultType, Map<String, String> tableNames) {

        List<String> fields =
                Arrays.stream(resultType.getMethods()).map(Method::getName).filter(it -> it.startsWith("get")).
                        filter(it -> !it.equals("getClass")).map(it -> it.substring(3)).map(it -> {
                            String firstChar = it.substring(0, 1);
                            return it.replaceFirst(firstChar, firstChar.toLowerCase());
                        }
                ).collect(Collectors.toList());


        // 获取selece语句
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (!tableNames.containsKey(fields.get(i))) {
                System.err.printf("select 缺失字段：%s%n", fields.get(i));
            }
            stringBuffer.append(tableNames.get(fields.get(i)));
            if (i < fields.size() - 1) {
                stringBuffer.append(Commas);
            }

            if (i % 5 == 0) {
                stringBuffer.append(line);
            }
        }
        return stringBuffer.toString();

    }

    private static String whereSql(String pre, Class paramType, Map<String, String> tableNames) {
        // where 语句
        // 获取selece语句
        Map<String, Class> fields =
                Arrays.stream(paramType.getMethods()).filter(it ->
                        it.getName().startsWith("get")
                ).filter(it -> !it.getName().equals("getClass")).collect(Collectors.toMap(it -> {
                    String name = it.getName();
                    name = name.substring(3);
                    String firstChar = name.substring(0, 1);
                    return name.replaceFirst(firstChar, firstChar.toLowerCase());
                }, Method::getReturnType));


        // 获取selece语句
        StringBuilder stringBuffer = new StringBuilder();

        for (String field : fields.keySet()) {
            if (!tableNames.containsKey(field)) {
                System.err.printf("where 缺失字段：%s%n", field);
                continue;
            }
            stringBuffer.append(line);
            stringBuffer.append(getWhereTest(pre, field, tableNames.get(field), fields.get(field)));
        }
        return stringBuffer.toString();
    }

    private static String getWhereTest(String pre, String fieldName, String column, Class fieldType) {

        String whereContent = String.format("%s.%s", pre, fieldName);
        String sqlContent = column;
        String lastSql = null;
        if (Objects.equals(String.class, fieldType)) {
            lastSql = String.format(" like concat('%%',#{%s},'%%')", whereContent);
        } else {
            lastSql = String.format(" = #{%s}", whereContent);
        }
        String testSql = String.format("        <if test=\"%s!=null\">\n" +
                "                and %s \n" +
                "        </if>", whereContent, sqlContent + lastSql);


        return testSql;
    }
}
