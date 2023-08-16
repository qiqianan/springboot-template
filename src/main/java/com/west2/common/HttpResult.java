package com.west2.common;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 1
 * http请求返回结果
 */
public class HttpResult extends LinkedHashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 200;

    public static final int CODE_ERROR = 500;


    public HttpResult() {
    }

    public HttpResult(int code, String msg, Object data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public HttpResult(Map<String, ?> map) {
        this.setMap(map);
    }

    public Integer getCode() {
        return (Integer)this.get("code");
    }

    public String getMsg() {
        return (String)this.get("msg");
    }

    public Object getData() {
        return this.get("data");
    }

    public HttpResult setCode(int code) {
        this.put("code", code);
        return this;
    }

    public HttpResult setMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public HttpResult setData(Object data) {
        this.put("data", data);
        return this;
    }

    public HttpResult set(String key, Object data) {
        this.put(key, data);
        return this;
    }

    public HttpResult setMap(Map<String, ?> map) {
        Iterator var2 = map.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            this.put(key, map.get(key));
        }

        return this;
    }

    public static HttpResult ok() {
        return new HttpResult(200, "ok", (Object)null);
    }

    public static HttpResult ok(String msg) {
        return new HttpResult(200, msg, (Object)null);
    }

    public static HttpResult code(int code) {
        return new HttpResult(code, (String)null, (Object)null);
    }

    public static HttpResult data(Object data) {
        return new HttpResult(200, "ok", data);
    }

    public static HttpResult error() {
        return new HttpResult(500, "error", (Object)null);
    }

    public static HttpResult error(String msg) {
        return new HttpResult(500, msg, (Object)null);
    }

    public static HttpResult get(int code, String msg, Object data) {
        return new HttpResult(code, msg, data);
    }

    @Override
    public String toString() {
        return "{\"code\": " + this.getCode() + ", \"msg\": " + this.transValue(this.getMsg()) + ", \"data\": " + this.transValue(this.getData()) + "}";
    }

    private String transValue(Object value) {
        return value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
    }

    public static void sendResponse(HttpServletResponse response , HttpResult result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        // 转化为json字符串 fastjson
        response.getWriter().write(result.toString());
    }
}
