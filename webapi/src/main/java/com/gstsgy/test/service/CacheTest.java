package com.gstsgy.test.service;

import com.gstsgy.base.model.ResponseBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheTest {
    private int num;

    @Cacheable(value = {"t.code"}, /*keyGenerator = "myKeyGenerator",*/key = "#code")
    public ResponseBean get(int code) {
        String res = String.format("code:%d,num:%d", code, num);
        return ResponseBean.getSuccess(res);
    }

    @Cacheable(value = {"t.id"}, /*keyGenerator = "myKeyGenerator",*/key = "#id")
    public ResponseBean getId(int id) {
        String res = String.format("id:%d,num:%d", id, num);
        return ResponseBean.getSuccess(res);
    }

    @CacheEvict(value = {"t.code", "t.id"} /*keyGenerator = "myKeyGenerator",*/, allEntries = true)
    public void update() {
        num++;
    }
}
