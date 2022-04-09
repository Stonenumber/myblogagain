package com.stone.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect //切面
@Component //可被自动扫描到
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //切点，所有web下的方法
    //整体为一个切面
    @Pointcut("execution(* com.stone.blog.web.*.*(..))")
    public void log(){}

    //切面执行之前
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //得到Request的属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        logger.info("Request: {}" , requestLog);
    }

    //切面执行之后
    @After("log()")
    public void doAfter(){
        logger.info("------After-------");
    }

    //得到执行之后的结果，放在了Result里
    @AfterReturning(returning = "result", pointcut="log()")
    public void doAfterReturning(Object result){
        logger.info("Result: {}", result);
    }

    //放置结果的内部类
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        //Alt + Insert 可自动生成
        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }

}
