package com.yin.proxy;/**
 * @author 17694
 * @date 2022/01/21
 **/

import com.yin.annotation.Transaction;
import com.yin.frameword.HelperLoader;
import com.yin.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @ClassName : TransactionProxy 
 * @Description :   
 */
public class TransactionProxy implements Proxy{
    private static final Logger log = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        if(!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DataBaseHelper.beginTransaction();
                log.debug("begin transaction");
                result = proxyChain.doProxyChain();
                DataBaseHelper.commitTransaction();
                log.debug("commit transaction");
            } catch (Exception e) {
                DataBaseHelper.rollBackTransaction();
                log.error("rollback transaction");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
                return result;
    }
}
