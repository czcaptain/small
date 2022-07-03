package com.yin.frameword;
/**
 * @author 17694
 * @date 2022/01/06
 **/


import com.yin.helper.*;
import com.yin.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : HelperLoader 
 * @Description :   
 */
public class HelperLoader {
    private static final Logger log = LoggerFactory.getLogger(HelperLoader.class);
    public static void init(){
        log.info("servlet init...");
        Class<?> [] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IoCHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classList) {
            ClassUtil.loadClass(aClass.getName());
        }
    }


}
