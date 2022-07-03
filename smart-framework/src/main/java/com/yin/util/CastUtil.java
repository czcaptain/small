package com.yin.util;/**
 * @author 17694
 * @date 2022/01/04
 **/

/**
 * @ClassName : CastUtil 
 * @Description :   
 */

import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * 数据转换
 */
public final class CastUtil {

    public static String castString(Object o){
               return CastUtil.castString(o,"");
    }

    private static String castString(Object o, String s) {

        return o != null ? String.valueOf(o) : s;
    }

    public static Long castLong(Object o) {
       return castLong(o,0);
    }

    public static Long castLong(Object o, long defaultValue) {
        long longValue = defaultValue;
        if (o != null) {
            String value = castString(o);
            longValue =  Long.parseLong(value);
        }
        return longValue;
    }
}
