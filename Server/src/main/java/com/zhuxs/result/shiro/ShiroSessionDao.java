package com.zhuxs.result.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by shusesshou on 2017/9/22.
 * 针对自定义的ShiroSession的Redis CRUD操作，通过isChanged标识符，确定是否需要Update方法
 * 通过配置securityManager在属性cacheManager从缓存中查找Session是否存在，如果找不到再调用方法
 * Shiro内部相应的组件(DefaultSecurityManager)会自动检测相应的对象(Realm)是否实现了CacheManagerAware并注入相应的CacheManager
 */
public class ShiroSessionDao extends CachingSessionDAO{

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionDao.class);

    //保存到Redis中key的前缀
    private String prefix = "";

    //设置会话的过期时间
    private int seconds = 30;

    //特殊配置 只用于没有redis时，将session放到EhCache中
    private Boolean onlyEhcache;

    //@Autowired
    //private JedisUtils jedisUtils

    /**
     * 如果session中没有登录信息就调用doReadSession方法从Redis中重读
     * @param sessionId
     * @return
     * @throws UnknownSessionException
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException{
        Session cached = null;

        return null;
    }

    @Override
    protected void doUpdate(Session session) {

    }

    @Override
    protected void doDelete(Session session) {

    }

    @Override
    protected Serializable doCreate(Session session) {
        return null;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return null;
    }
}
