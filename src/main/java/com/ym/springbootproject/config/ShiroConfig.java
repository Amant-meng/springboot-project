package com.ym.springbootproject.config;

import com.ym.springbootproject.shiro.ShiroAuthenticatingFilter;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Meng
 * @Description: TODO Shiro配置类
 * @date 2021/12/8 18:08
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(null);
        securityManager.setSessionManager(sessionManager);
        return securityManager;

    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // Shiro过滤
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("shiro",new ShiroAuthenticatingFilter());
        shiroFilter.setFilters(filterMap);

        /**
         * anon 没有参数，表示可以匿名使用。
         * authc 表示需要认证(登录)才能使用，没有参数
         */
        Map<String, String> map = new HashMap<>();
        map.put("/person/**", "anon");
        map.put("/**", "shiro");

        shiroFilter.setFilterChainDefinitionMap(map);
        return shiroFilter;
    }
    
}
