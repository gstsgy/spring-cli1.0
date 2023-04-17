package com.gstsgy.test.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.gstsgy.base.utils.DateUtil;
import com.gstsgy.permission.utils.JWTUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "insertYmd", String.class, DateUtil.getDateTime()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateYmd", String.class, DateUtil.getDateTime());
        this.strictInsertFill(metaObject, "insertId", Long.class, JWTUtil.getUser());
        this.strictInsertFill(metaObject, "updateId", Long.class, JWTUtil.getUser());

        this.strictInsertFill(metaObject, "effective", Boolean.class, true);
        // this.strictInsertFill(metaObject, "seq", Integer.class, 0);
        this.strictInsertFill(metaObject, "updateFlag", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateYmd", String.class, DateUtil.getDateTime());
        this.strictInsertFill(metaObject, "updateId", Long.class, JWTUtil.getUser());

        TableInfo tableInfo = TableInfoHelper.getTableInfo(metaObject.getOriginalObject().getClass());
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        fieldList.forEach(item -> {
            if (Objects.equals(item.getField().getType(), Double.class) && metaObject.getValue(item.getField().getName()) == null) {
                metaObject.setValue(item.getField().getName(),  0.0);
            }
        });
    }

    @Override
    public MetaObjectHandler  strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal){
        Object obj = fieldVal.get();
        if (Objects.nonNull(obj)) {
            metaObject.setValue(fieldName, obj);
        }
        return this;
    }
}
