package com.yin.bean;/**
 * @author 17694
 * @date 2022/01/07
 **/

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : View 
 * @Description :   
 */
public class View {
    private String path;
    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<>();
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
