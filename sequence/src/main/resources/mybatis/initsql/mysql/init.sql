CREATE TABLE IF NOT EXISTS `g_sequence`(
                                           `id` BIGINT ,
                                           `g_key` VARCHAR(255) NOT NULL COMMENT '主键',
    `subkey` VARCHAR(255) COMMENT '副键',
    `startnum` BIGINT COMMENT '开始',
    `step` int COMMENT '步长',
    `maxnum` BIGINT COMMENT '最大值',
    `dayresetting` bit COMMENT '是否重置',
    `lastday` varchar(255) COMMENT '日期',
    `value` BIGINT COMMENT '当前值',
    `insert_id` bigint DEFAULT NULL COMMENT '新增人',
    `insert_ymd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '新增日期',
    `update_ymd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新日期',
    `update_id` bigint DEFAULT NULL COMMENT '更新人',
    `remark` varchar(255) ,
    `remark1` varchar(255)  COMMENT '备用字段',
    `remark2` varchar(255)  COMMENT '备用字段',
    `remark3` varchar(255)  COMMENT '备用字段',
    `remark4` varchar(255)  COMMENT '备用字段',
    `remark5` varchar(255)  COMMENT '备用字段',
    `effective` int  COMMENT '是否有效',
    `update_flag` int ,
    PRIMARY KEY ( `id` )
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;