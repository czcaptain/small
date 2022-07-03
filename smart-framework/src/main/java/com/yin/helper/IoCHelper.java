package com.yin.helper;
/**
 * @author 17694
 * @date 2022/01/06
 **/

import com.yin.annotation.Inject;
import com.yin.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * @ClassName : IoCHelper
 * @De scription :
 */
public class IoCHelper {
    //jvm 链接过程中就已经实现静态代码块的初始化
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //不为空
        if (!beanMap.isEmpty()) {
            //遍历实例
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //从Bean 类定义中获取所有成员变量
                Field[] beanFields = beanClass.getFields();
                if (!Arrays.asList(beanFields).isEmpty()) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在 Bean Map 中获取 Bean
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(beanFieldInstance != null){
                                ReflectionUtil.setField(beanFieldInstance,beanField,beanFieldInstance);
                            }


                        }
                    }
                }


            }

        }

    }
}
