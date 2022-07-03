package com.yin.helper;/**
 * @author 17694
 * @date 2022/01/13
 **/

import com.yin.annotation.Action;
import com.yin.bean.Handler;
import com.yin.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName : ControllerHelper 
 * @Description :   
 */
public final class ControllerHelper {
      private static final Map<Request,Handler> ACTION_MAP = new HashMap<>();

      static {
          //获取所有的Controller注解的类
          Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
          if(!CollectionUtils.isEmpty(controllerClassSet)){
              for (Class<?> aClass : controllerClassSet) {
                  Method []declaredMethod = aClass.getDeclaredMethods();
                  if(declaredMethod.length!=0){
                      for (Method method : declaredMethod) {
                          if(method.isAnnotationPresent(Action.class)){
                              //获取注解上的值
                              String value = method.getAnnotation(Action.class).value();
                              if(value.matches("\\w+:/\\w*")){
                                  //Action 注解格式 get:/hello
                                  String[] split = value.split(":");
                                  //get
                                  String requestMethod = split[0];
                                  //hello
                                  String requestPath = split[1];
                                  Request request = new Request(requestMethod, requestPath);
                                  Handler handler = new Handler(aClass, method);
                                  ACTION_MAP.put(request,handler);
                              }

                          }
                      }
                  }



              }
          }

      }


    /**
     *
     * @param requestMethod
     * @param requestPath
     * @return
     * @desc 将有action注解路径 和 具体方法 放入一个map
     */
    public static Handler getHandler(String requestMethod,String requestPath){
            Request request = new Request(requestMethod, requestPath);
            return ACTION_MAP.get(request);
        }


}
