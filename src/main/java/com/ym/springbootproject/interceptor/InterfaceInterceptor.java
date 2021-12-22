package com.ym.springbootproject.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Meng
 * @Description: TODO 接口拦截器
 * @date 2021/11/25 18:31
 */
public class InterfaceInterceptor implements HandlerInterceptor {

    public static final String TOKEN =  "f4e2e52034856f86b67cde581c0f9eb5";

    /**
     * 是请求执行前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出签名
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String sign = request.getHeader("Sign");

        final String message = "接口鉴权失败，请在前端系统添加鉴权值";
        if (StringUtils.isEmpty(sign)) {
            try (PrintWriter out = response.getWriter()) {
                String responseJson = message;
                out.print(responseJson);
            } catch (IOException e) {
                throw new IOException("系统错误：接口鉴权异常");
            }
            return false;
        } else {
            if(sign.equals(TOKEN)){
                return true;
            }else {
                return false;
            }

        }
    }

    /**
     * 请求结束执行，只有preHandle方法返回true的时候才会执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 视图渲染完成后才执行
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
