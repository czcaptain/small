package com.yin.proxy;

/**
 * @author 17694
 * @date 2022/01/18
 **/
public interface Proxy {

        Object doProxy(ProxyChain proxyChain) throws Throwable;
}
