package com.yin.util;/**
 * @author 17694
 * @date 2022/01/07
 **/

import java.net.URLDecoder;

/**
 * @ClassName : CodeUtil 
 * @Description :   
 */
public class CodeUtil {


    /**
     * 将 url 编码
     */
    public static String decodeURL(String source) {
            String target;
            try{
                target = URLDecoder.decode(source,"UTF-8");

            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            return target;
    }
}
