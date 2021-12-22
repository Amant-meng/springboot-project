package com.ym.springbootproject.shiro;

import com.ym.springbootproject.common.utils.StringUtils;
import com.ym.springbootproject.moudle.sys.entity.Person;
import com.ym.springbootproject.moudle.sys.service.PersonService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author Meng
 * @Description: TODO 自定Shiro认证类
 * @date 2021/12/8 18:54
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private PersonService personService;

    /**
     * 重写supports方法，识别自定义token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token !=null && token instanceof ShiroToken;
    }

    /**
     *  权限配置类
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        Person person = (Person) principal.getPrimaryPrincipal();
        int id = person.getId();
        //具体业务，查询用户权限
        Set<String> permSet = personService.getUserPermissions();
        //添加角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //添加角色权限
        return authorizationInfo;
    }

    /**
     * 认证配置类
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (StringUtils.isEmpty((String) token.getPrincipal())) {
            return null;
        }

        String name = token.getPrincipal().toString();
        /**
         * 以前登录逻辑是拿到用户名密码去数据库匹配
         * shiro是根据用户名把用户对象找出来，再做密码匹配
         */
        Person user = personService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, name, getName());
            return simpleAuthenticationInfo;
        }
    }

}
