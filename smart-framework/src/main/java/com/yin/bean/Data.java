package com.yin.bean;/**
 * @author 17694
 * @date 2022/01/07
 **/

/**
 * @ClassName : Data 
 * @Description :   
 */
public class Data {
    private Object model;

    public Data() {
    }

    public Data(Object model) {
        this.model = model;
    }


    /**
     * 反射方法调用
     */
//    public void test() {
//        System.out.println("通过反射调用方法!!!");
//    }
    public Object getModel() {
        return model;
    }
}
