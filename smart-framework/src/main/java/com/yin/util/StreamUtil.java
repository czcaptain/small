package com.yin.util;/**
 * @author 17694
 * @date 2022/01/07
 **/


import javax.servlet.ServletInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @ClassName : StreamUtil 
 * @Description :   
 */
public class StreamUtil {


    public static String getString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            reader.lines().forEach(i->sb.append(i));

        }catch (Exception e){
                throw new RuntimeException(e);
        }
        return sb.toString();

    }
}
