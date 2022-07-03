package web;
/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.yin.annotation.Action;
import com.yin.annotation.Controller;
import com.yin.annotation.Inject;
import com.yin.bean.Data;
import service.TestService;

/**
 * @ClassName : TestController 
 * @Description :   
 */

@Controller
public class TestController {

    @Inject
    TestService testService;


    @Action("get:/hellosmart")
    public String hello(){
        System.out.println("接口访问成功");
       return (String) new Data(testService.test()).getModel();

    }




}
