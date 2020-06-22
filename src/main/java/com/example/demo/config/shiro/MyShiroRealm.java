package com.example.demo.config.shiro;

import com.example.demo.dao.EmpDao;
import com.example.demo.dao.EmpMapper;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Menu;
import com.example.demo.service.EmpService;
import com.example.demo.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by more-time on 2019/6/30.
 */
//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Autowired
    private EmpService empService;
    @Autowired
    private EmpMapper empMapper;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的方法...");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Emp emp = (Emp)principalCollection.getPrimaryPrincipal();

        //获取当前登陆用户的菜单权限
        List<Menu> menuList = empMapper.getMenusByEmpuuid(emp.getUuid());

        //加入授权
        for(Menu m : menuList){
            //这里我们使用menuname来做授权里的key值，那么在配置授权访问的url=perms[菜单名称]
            info.addStringPermission(m.getMenuname());
        }
        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        //通过令牌得到用户名和密码?
        UsernamePasswordToken upt = (UsernamePasswordToken)authenticationToken;
        //得到密码
        String pwd = new String(upt.getPassword());
        //调用登录查询
        Emp emp = loginService.findEmpByUsername(upt.getUsername(),pwd);
        if(null != emp){
            //构造参数1： 主角=登陆用户
            //参数2：授权码：密码
            //参数3：realm的名称
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp,pwd,getName());
            return info;
        }
        return null;
    }
}