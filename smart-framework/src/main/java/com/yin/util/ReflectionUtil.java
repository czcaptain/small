package com.yin.util;
/**
 * @author 17694
 * @date 2022/01/06
 **/

import com.yin.bean.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName : ReflectionUtil 
 * @Description :    反射工具类 用来初始化对象
 */



public final class ReflectionUtil {


    /**
     * 测试反射
     * @param args
     */
    public static void main(String[] args) {
        //refleTest();
        refleTest1();

    }
    private static void refleTest1() {
        Class cla = null;
        try {
            cla =   Class.forName("com.yin.bean.Data");
            //获取构造对象
            Data o =  (Data) cla.newInstance();
            System.out.println(o.getModel()+"136");
            Constructor constructor = cla.getConstructor();

            Field field = cla.getDeclaredField("model");
            field.setAccessible(true);
            Data data = (Data) constructor.newInstance();
            field.set(data,"1");
            System.out.println(data.getModel());

            Method getModel = cla.getMethod("test");
            getModel.invoke(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void refleTest() {
        try {
            Class<?> aClass = Class.forName("com.yin.bean.Data");
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2
        Class<Data> dataClass = Data.class;
        //3
//        Data data = new Data();
//        Class<? extends Data> aClass = data.getClass();
//        System.out.println(dataClass);
//        System.out.println(aClass);
//        System.out.println(dataClass==aClass);
    }

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 类的初始化
     * @param cla
     * @return
     */
    public static Object newInstance(Class<?> cla){
        Object instance ;
        try{
            instance = cla.newInstance();
        } catch (Exception e){
            log.error("newInstance fail",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     *  调用方法
     */
    public static Object invokeMethod(Object obj,Method method,Object ...arg){
        Object result;
        try {
            if(arg.length==0){
                arg = new Object[]{};
            }
            method.setAccessible(true);
            result = method.invoke(obj,arg);
        }catch (Exception e){
            log.error("invoke method fail",e );
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * 设置成员变量
     */
    public static void setField(Object obj, Field field, Object value){
        try{
            field.setAccessible(true);
            field.set(obj,value);
        } catch (Exception e){
            log.error("set field fail",e );
            throw new RuntimeException(e);
        }
    }

}
