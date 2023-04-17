package com.gstsgy.test.conf;

/*
 * @Description
 * @author  guyue
 * @date    2020-05-14 16:27
 */


import com.gstsgy.base.model.APIException;
import com.gstsgy.base.model.ResponseBean;
import com.gstsgy.base.utils.LangUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

@Order(0)
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {

        System.out.println("url:" + request.getRequestURI() + ";  method:" + request.getMethod());
        //request.getParameterMap().values().stream().map(JSON::toJSON).forEach(System.out::println);
        ex.printStackTrace();

        StackTraceElement[] stackTraceElements = ex.getStackTrace();// 得到异常棧的首个元素
        log.error("********************************");
        StackTraceElement stackTraceElement = stackTraceElements[0];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ex.printStackTrace(new PrintStream(baos));
        log.error(baos.toString());

        // }
        log.error("********************************");

        if (ex instanceof APIException apiException) {
            return ResponseBean.getInstance(apiException.getApiExceptionType(), apiException.getMessage(), null);
        }
        HttpStatus httpStatus = getStatus(request);
        if (Objects.equals(httpStatus, HttpStatus.INTERNAL_SERVER_ERROR)) {
            return ResponseBean.getInstance(httpStatus, ex.getMessage(), null);
        }

        return ResponseBean.getInstance(httpStatus, ex.getMessage(), null);
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
