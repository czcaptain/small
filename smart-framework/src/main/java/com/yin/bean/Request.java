package com.yin.bean;
/**
 * @author 17694
 * @date 2022/01/06
 **/

import java.util.Objects;

/**
 * @ClassName : Request 
 * @Description :   
 */
public class Request {
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求路径
     */
    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return Objects.equals(getRequestMethod(), request.getRequestMethod()) && Objects.equals(getRequestPath(), request.getRequestPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestMethod(), getRequestPath());
    }
}
