package com.yin.util;
/**
 * @author 17694
 * @date 2022/01/04
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName : PropsUtil 
 * @Description :   
 */
public class PropsUtil {
    private static final Logger log  = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * @des 读取配置文件信息
     * @param filename
     * @return
     */
    public static Properties loadProps(String filename) {
        Properties properties = null;
        InputStream inputStream = null;
        try{
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if(inputStream == null){
                    throw new FileNotFoundException(filename+"file not found");
                }
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e){
            log.error("close input stream failure",e);
        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch (IOException e){
                    log.error("close input stream failure",e);
                }
            }
        }
                return properties;
    }
    /**
     * 获取字符串属性
     */
    public static String getString(Properties properties,String key,String defaultValue){
            String value = defaultValue;
            if(properties.containsKey(key)){
                value = properties.getProperty(key);
            }
            return value;
    }

    public static String getString(Properties properties,String key){
                return getString(properties,key,"");

    }


}
