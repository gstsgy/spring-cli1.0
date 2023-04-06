/*
package com.gstsgy.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private DeptService deptService;

    @GetMapping("/t1")
    public ResponseBean t1(@RequestParam(value="id")int id) {
        return deptService.queryDept();
    }

    @GetMapping("/t2")
    public TestDB t2() {
        return null;
    }

    @Autowired
    private CacheTest cacheTest;

    @GetMapping("/c1")
    public ResponseBean c1(int code) {
        return cacheTest.get(code);
    }

    @GetMapping("/c2")
    public ResponseBean c2(int id) {
        return cacheTest.getId(id);
    }

    @GetMapping("/c3")
    public void c3() {
         cacheTest.update();
    }

}
*/
