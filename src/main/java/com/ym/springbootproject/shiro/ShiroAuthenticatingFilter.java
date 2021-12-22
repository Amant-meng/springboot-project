package com.ym.springbootproject.shiro;

import com.google.gson.Gson;
import com.ym.springbootproject.common.ResultBody;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/12/8 18:32
 */
public class ShiroAuthenticatingFilter extends AuthenticatingFilter {

    /**
     * 创建Shiro认证token
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new ShiroToken(token);
    }

    /**
     * 允许访问
     * 根据请求信息，参数等信息判断是否允许通过，如果返回false，则是不通过。
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    /**
     * 拒绝访问
     * 根据请求信息，参数等信息判断是否允许通过，如果返回false，则是不通过。
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //TODO 获取请求的token,如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String json = new Gson().toJson(ResultBody.error(HttpStatus.SC_UNAUTHORIZED,"token failed"));
            httpResponse.getWriter().print(json);
        }
        return executeLogin(request, response);
    }

    /**
     * 认证或者权限过滤时候，就会首先走到这个方法内，创建 token,然后 获取subject 执行login方法。
     * 如果成功了，会执行 FormAuthenticationFilter  类的onloginSuccess 方法，失败执行onLoginFailure 方法。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                //修复bug代码，也算个不小的坑吧
                Subject subject =  new WebSubject.Builder(request, response).buildSubject();
                subject.login(token);
                ThreadContext.bind(subject);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    /**
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     *  登录失败处理
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            Throwable throwable = e.getCause() == null ? e:e.getCause();
            ResultBody resultBody = ResultBody.error(HttpStatus.SC_UNAUTHORIZED,throwable.getMessage());
            httpResponse.getWriter().print(new Gson().toJson(resultBody));
        }catch (Exception ex){
            ex.getMessage();
        }
        return false;
    }

    /**
     * 获取请求中的token值
     * @param request
     * @return
     */
    private String getRequestToken(HttpServletRequest request) {
        //todo 从header中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
