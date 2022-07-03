package com.yin.helper;
/**
 * @author 17694
 * @date 2022/01/19
 **/


import com.yin.annotation.Aspect;
import com.yin.annotation.Service;
import com.yin.proxy.AspectProxy;
import com.yin.proxy.Proxy;
import com.yin.proxy.ProxyManager;
import com.yin.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @ClassName : AopHelper 
 * @Description :   
 */
public final class AopHelper {
        private static final Logger log = LoggerFactory.getLogger(AopHelper.class);
        static {
            try {
                Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
                Map<Class<?>, List<Proxy>> targetMap= createTargetMap(proxyMap);
                for (Map.Entry<Class<?>,List<Proxy>> targetEntry : targetMap.entrySet()) {
                    Class<?> targetClass = targetEntry.getKey();
                    List<Proxy> proxyList = targetEntry.getValue();
                    Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                    BeanHelper.setBean(targetClass,proxy);
                    log.info("Aop success");
                }
            } catch (Exception e){
                log.error("aop fail",e);
            }

        }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
                Map<Class<?>,List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>,Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
                Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for(Class<?> targetClass : targetClassSet) {
                Proxy proxy =(Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
            return targetMap;
    }

    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        return proxyMap;
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
            Set<Class<?>> targetClassSet = new HashSet<>();
            Class<? extends Annotation> annotation = aspect.value();
            if(annotation != null && !annotation.equals(Aspect.class)) {
                targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
            }
            return targetClassSet;
    }


    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if(proxyClass.isAnnotationPresent(Aspect.class)) {
                log.info("Aspect load");
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }



    }
//
//    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap) {
//            Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
//        proxyMap.put(TransactionProxy.class,serviceClassSet);
//
//
//    }

}
