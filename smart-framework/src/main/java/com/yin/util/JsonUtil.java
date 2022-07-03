package com.yin.util;/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : JsonUtil 
 * @Description :   
 */
public final class JsonUtil<T> {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T>String toJson(T Object) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(Object);
        } catch (Exception e) {
            log.error("toJson fail",e);
            throw new RuntimeException(e);
        }
        return json;
    }
    public static <T> T formJson(String json,Class <T> type){
        return null;
    }

}
