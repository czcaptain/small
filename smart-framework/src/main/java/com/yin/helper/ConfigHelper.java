package com.yin.helper;/**
 * @author 17694
 * @date 2022/01/04
 **/


import com.yin.frameword.ConfigConstant;
import com.yin.util.PropsUtil;

import java.util.Properties;

/**
 * @ClassName : ConfigHelper 
 * @Description :   
 */
public final class ConfigHelper {
    //加载配置文件
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);


    /**
     * 获取 jdbc 驱动
     */
    public static String getjdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取 jdbc URL
     * @return
     */
    public static String getjdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }
    /**
     * 获取 jdbc username
     */
    public static String getjdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }

    /**
     *  获取 jdbc password
     */
    public static String getjdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }

    /**
     *  获取应用基础包
     *
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取jsp路径
     * @return
     */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH,"/WEN-INF/view/");
    }




    public static String getAppAssertPath() {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSERT_PATH,"/asset/");
    }
}
