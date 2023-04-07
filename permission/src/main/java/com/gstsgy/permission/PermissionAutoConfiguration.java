package com.gstsgy.permission;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author guyue
 * @version 3.0
 * @description: TODO
 * @date 2022/2/28 上午9:35
 */
@MapperScan(value = {"com.gstsgy.permission.mapper"})
@ComponentScan("com.gstsgy.permission")

public class PermissionAutoConfiguration {
}
