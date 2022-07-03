package service;/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.yin.annotation.Service;
import com.yin.util.PropsUtil;

import java.util.Properties;

/**
 * @ClassName : TestService 
 * @Description :   
 */

@Service
public class TestService {


    public String test(){
        return "hello world";
    }

    public static void main(String[] args) {
        System.out.println(PropsUtil.loadProps("smart.properties"));

    }


}
