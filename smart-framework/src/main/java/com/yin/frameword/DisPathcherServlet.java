package com.yin.frameword;/**
 * @author 17694
 * @date 2022/01/07
 **/

import com.yin.bean.*;
import com.yin.helper.BeanHelper;
import com.yin.helper.ConfigHelper;
import com.yin.helper.ControllerHelper;
import com.yin.util.*;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @ClassName : DisPathcherServlet 
 * @Description :   
 * @// TODO: 2022/1/7  BeanHelper.java使用了未经检查或不安全的操作。 
 */
@WebServlet(urlPatterns="/*",loadOnStartup = 0)
public class DisPathcherServlet extends HttpServlet {

    //ServletConfig  : 当前servlet的初始化参数信息
    @Override
    public void init(ServletConfig config){
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath()+ "*");
    }


    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqMethod = req.getMethod().toLowerCase();
        String pathInfo = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(reqMethod, pathInfo);
        if (handler != null) {
            Class<?> ControllerClass = handler.getControllerClass();
            Object ControllerBean = BeanHelper.getBean(ControllerClass);//获取bean实例 Map<Class,instance>
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String paramValue = req.getParameter(parameterName);
                paramMap.put(parameterName, paramValue);
            }

            String body = CodeUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                String[] params = body.split("&");
                if (params.length != 0) {
                    for (String param : params) {
                        String[] split = param.split("=");
                        if (!Arrays.asList(split).isEmpty() && split.length == 2) {
                            String paramName = split[0];
                            String paramValue = split[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }

            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getGetActionMethod();//TestController.hello()方法
            Object result = ReflectionUtil.invokeMethod(ControllerBean,actionMethod,param);
//            if(result instanceof View){
            if(View.class.isInstance(result)){
                    View view = (View) result;
                    String path = view.getPath();
                    if(StringUtils.isNotEmpty(path)){
                        if (path.startsWith("/")){
                            resp.sendRedirect(req.getContextPath() + path);
                        } else {
                            Map<String,Object> model = view.getModel();
                           for(Map.Entry<String,Object>entry: model.entrySet()){
                               req.setAttribute(entry.getKey(),entry.getValue());
                           }
                           req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req,resp);
                        }
                    }

            }//                    else if(result instanceof Data){
            else {
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json =  JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }

            }

        }

    }
}
