package com.yin.proxy;/**
 * @author 17694
 * @date 2022/01/19
 **/

import java.lang.reflect.Method;

/**
 * @ClassName : AspectProxy 
 * @Description :   
 */
public abstract class AspectProxy implements Proxy{


    /**
     * @Desc Aop切入方法
     * @param proxyChain
     * @return
     */
    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable{
        Object result = null;
        Class<?> cls = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        try{
            if(intercept(cls,targetMethod,methodParams)) {
                before(cls,targetMethod,methodParams) ;
                result = proxyChain.doProxyChain();
                after(cls,targetMethod,methodParams,result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            error(cls,targetMethod,methodParams,e);
            throw e;
        } finally {
            end();
        }
            return result;
    }

    private void error(Class<?> cls, Method targetMethod, Object[] methodParams, Throwable e) {

    }


    public void before(Class<?> cls, Method targetMethod, Object[] methodParams) throws Throwable {

    }
    public void after(Class<?> cls, Method targetMethod, Object[] methodParams, Object result) throws Throwable {

    }

    private void begin() {

    }

    private void end() {
    }

    private boolean intercept(Class<?> cls, Method targetMethod, Object[] methodParams) {
        return true;
    }

}
