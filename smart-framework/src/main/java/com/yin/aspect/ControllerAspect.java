package com.yin.aspect;/**
 * @author 17694
 * @date 2022/01/29
 **/

import com.yin.annotation.Aspect;
import com.yin.annotation.Controller;
import com.yin.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @ClassName : ControllerAspect 
 * @Description :   拦截controller方法
 */


@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);
    private Long begin;

    @Override
    public void before(Class<?> cls, Method targetMethod, Object[] methodParams) throws Throwable{
        log.info("-----begin-----");
        log.info(String.format("class: %s",cls.getName()));
        log.info(String.format("method: %s",targetMethod.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method targetMethod, Object[] methodParams, Object result) throws Throwable{
            log.info(String.format("time: %s",System.currentTimeMillis() - begin));
            log.info("-----end------");
    }
}
