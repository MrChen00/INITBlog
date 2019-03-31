package com.zhou.init.realm;

import com.zhou.init.pojo.UserAccount;
import com.zhou.init.service.UserAccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ZHOU
 * @create 2019-02-23 14:38
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserAccountService userAccountService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 获取用户信息
        UserAccount userAccount = userAccountService.getByName(userName);

        // 没有该用户名
        if(userAccount == null){
            throw new UnknownAccountException();
        }

        // 用户认证, 由Shiro操作
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userAccount.getEmail(),
                userAccount.getPassword(),
                ByteSource.Util.bytes(userAccount.getEmail()),
                getName()
        );
        return authenticationInfo;
    }
}
