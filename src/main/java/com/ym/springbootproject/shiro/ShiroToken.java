package com.ym.springbootproject.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Meng
 * @Description: TODO
 * @date 2021/12/8 18:52
 */
public class ShiroToken implements AuthenticationToken {

    private String token;

    public ShiroToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
