package com.yin.helper;/**
 * @author 17694
 * @date 2022/01/06
 **/

import com.sun.org.apache.bcel.internal.util.ClassSet;
import com.yin.annotation.Aspect;
import com.yin.annotation.Controller;
import com.yin.annotation.Service;
import com.yin.proxy.AspectProxy;
import com.yin.util.CastUtil;
import com.yin.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName : ClassHelper 
 * @Description :   
 */
public class ClassHelper {
    private static final Set<Class<?>> CLASS_SET ;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用下的包名
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用下的service类
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> ClassSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
                if(aClass.isAnnotationPresent(Service.class)){
                    ClassSet.add(aClass);
                }
        }
        return ClassSet;
    }

    /**
     * 获取包下所有Controller
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> ClassSet = new HashSet<>();
        for (Class<?> aClass : CLASS_SET) {
            if(aClass.isAnnotationPresent(Controller.class)){
                ClassSet.add(aClass);
            }
        }
        return ClassSet;
    }

    /**
     * 获取所有 bean
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        //beanClassSet.addAll(getClassSetBySuper())
        return beanClassSet;
    }


    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
            Set<Class<?>> classSet = new HashSet<>();
                for(Class<?> cla : CLASS_SET) {
                    if(superClass.isAssignableFrom(cla) && !superClass.equals(cla)){
                        classSet.add(cla);
                    }
                }
            return classSet;
    }

    /**
     * 获取指定的注解类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cla : CLASS_SET) {
            if (cla.isAnnotationPresent(annotationClass)) {
                classSet.add(cla);
            }
        }
        return classSet;
    }
}
