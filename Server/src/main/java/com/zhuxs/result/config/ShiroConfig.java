package com.zhuxs.result.config;

import com.zhuxs.result.shiro.ShiroSessionDao;
import com.zhuxs.result.shiro.ShiroRealm;
import com.zhuxs.result.shiro.ShiroSessionFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by shusesshou on 2017/9/20.
 */
@Configuration
public class ShiroConfig {
    /**
     * LifecycleBeanPostProcessor,DestructionAwareBeanPostProcessor的子类
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期，初始化和销毁
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * HashCredentialsMatcher,对密码进行编码
     */
    @Bean(name = "hashCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2); //散列两次
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * ShiroRealm, 自定义的认证类
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }

    /**
     * CachingShiroSessionDao
     */
    @Bean(name = "shiroSessionDao")
    public ShiroSessionDao shiroSessionDao(){
        return new ShiroSessionDao();
    }
    /**
     * EhCacheManager,缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来
     */
    @Bean(name = "sessionFactory")
    public ShiroSessionFactory shiroSessionFactory() {
        return new ShiroSessionFactory();
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        //manager.setCacheManager(cacheManager);// 加入缓存管理器
        manager.setSessionFactory(shiroSessionFactory());//设置sessionFactory
        manager.setSessionDAO(shiroSessionDao());// 设置SessionDao
        manager.setDeleteInvalidSessions(true);// 删除过期的session
        manager.setGlobalSessionTimeout(shiroSessionDao().getExpireTime());// 设置全局session超时时间
        manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        return manager;
    }

    /**
     * SecurityManager,权限管理，组合了登录、登出、权限和session的处理
     *
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置Realm
        securityManager.setRealm(shiroRealm());
        //设置session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * ShirpFilterFactoryBean, 生成ShiroFilter
     * securityManager,filters,filterChainDefinitionManager
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filters = new LinkedHashMap<String,Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setLoginUrl("/notAuthc");

        Map<String,String> filterChainDefinitionManager = new LinkedHashMap<String,String>();
        filterChainDefinitionManager.put("/logout","logout");
        filterChainDefinitionManager.put("/userInfo","authc");
        filterChainDefinitionManager.put("/admin/**","authc");
        filterChainDefinitionManager.put("/jobs/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        return shiroFilterFactoryBean;
    }

    /**
     * 由Advisor决定对哪些类的方法进行AOP代理
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     *
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
