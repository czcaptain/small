package com.yin.bean;
/**
 * @author 17694
 * @date 2022/01/06
 **/

import java.lang.reflect.Method;

/**
 * @ClassName : handler
 * @Description :  处理对象
 */
public class Handler {
    /**
     * controller 类
     */
    private Class<?> controllerClass;

    /**
     * action 方法
     */
    private Method getActionMethod;

    public Handler(Class<?> controllerClass, Method getActionMethod) {
        this.controllerClass = controllerClass;
        this.getActionMethod = getActionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getGetActionMethod() {
        return getActionMethod;
    }
}
