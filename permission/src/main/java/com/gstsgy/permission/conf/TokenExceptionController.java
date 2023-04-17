package com.gstsgy.permission.conf;

/*
 * @Description
 * @author  guyue
 * @date    2020-05-14 16:27
 */

import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.permission.exception.TokenExpetion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Order(-1)
@Slf4j
@RestControllerAdvice
public class TokenExceptionController {
//    @Autowired
//    private LogService logService;


    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TokenExpetion.class)
    public ResponseBean handle401(TokenExpetion e) {
        return ResponseBean.getNoLogin(e.getMessage());
    }

}
