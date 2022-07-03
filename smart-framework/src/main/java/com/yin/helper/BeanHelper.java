package com.yin.helper;
/**
 * @author 17694
 * @date 2022/01/06
 **/

import com.yin.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : BeanHelper 
 * @Description :   
 */
public final class BeanHelper {
        private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();
        static {
            Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
            for(Class<?> beanClass : beanClassSet){
                  Object o = ReflectionUtil.newInstance(beanClass);
                    BEAN_MAP.put(beanClass,o);
            }
        }

    /**
     *  获取 Bean 映射
     */
    public static Map<Class<?>,Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     *  获取 Bean 实例
     */
    public static <T> T getBean(Class<T> cla){
        if(!BEAN_MAP.containsKey(cla)){
           throw new RuntimeException("can not get bean by class" + cla);
        }
        return (T) BEAN_MAP.get(cla);
    }

    /**
     * 设置bean实例
     */
    public static void setBean(Class<?> targetClass, Object proxy) {
        BEAN_MAP.put(targetClass,proxy);
    }
}
