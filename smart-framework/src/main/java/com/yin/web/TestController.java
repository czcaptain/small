package com.yin.web;/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.yin.annotation.Action;
import com.yin.annotation.Controller;
import com.yin.bean.Data;
import com.yin.bean.Param;
import com.yin.helper.BeanHelper;
import com.yin.helper.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : TestController 
 * @Description :   
 */


@Controller
public class TestController {
    /**
     * CGlib
     */

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Action("get:/hello")
    public Data hello(Param param){  //todo 没加Param 报错
        log.info("+++TestController+++");
        Long id = param.getLong("id");
       // BeanHelper.getBeanMap().entrySet().stream().forEach(System.out::println);
        ClassHelper.getBeanClassSet().stream().forEach(System.out::println);
        return new Data(id.toString());
    }

}
