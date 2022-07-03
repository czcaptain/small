package com.yin.bean;/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.yin.util.CastUtil;

import java.util.Map;

/**
 * @ClassName : Param 
 * @Description :   
 */
public class Param {
    private Map<String,Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    public Long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
