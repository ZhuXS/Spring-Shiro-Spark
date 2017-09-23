package com.zhuxs.result.shiro;

import com.zhuxs.result.utils.SerializeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import

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

    @Autowired
    private JedisPool jedisPool;
    /**
     * 如果session中没有登录信息就调用doReadSession方法从Redis中重读
     * session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null 代表没有登录，登录后Shiro会放入该值
     * @param sessionId
     * @return
     * @throws UnknownSessionException
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException{
        Session session = getCachedSession(sessionId);
        if(session == null || session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
            session =  this.doReadSession(sessionId);
            if(session == null){
                throw new UnknownSessionException("There is no session with id [" + sessionId + "]");
            }else {
                cache(session,session.getId());
            }
        }
        return session;
    }

    /**
     * 更新会话
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        try {
            if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()){
                return;
            }
        } catch (Exception e){
            logger.error("ValidatingSession error");
        }
        try {
            if(session instanceof ShiroSession){
                //如果没有字段（除lastAccessTime以外其他字段）发生改变
                ShiroSession shiroSession = (ShiroSession) session;
                if(!shiroSession.getIsChanged()){
                    return;
                }
                Jedis jedis = null;
                Transaction transaction = null;
                try {
                     jedis = jedisPool.getResource();
                     //开启事务
                    transaction = jedis.multi();
                    shiroSession.setIsChanged(false);
                    shiroSession.setLastAccessTime(DateTime.now().toDate());
                    transaction.setex(prefix + session.getId(),seconds,SerializeUtils.serializaToString(shiroSession));
                    logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
                    //执行事务
                    transaction.exec();
                } catch (Exception e){
                    if(transaction != null){
                        transaction.discard();
                    }
                    throw e;
                } finally {
                    jedis.close();
                }
            }else {
                logger.debug("sessionId {} name {} 更新失败", session.getId(), session.getClass().getName());
            }
        } catch (Exception e){
            logger.warn("更新Session失败", e);
        }

    }

    /**
     * 删除会话
     * 当会话过期／会话停止时会调用
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        logger.debug("begin doDelete {}", session);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(prefix + session.getId());
            this.uncache(session.getId());
            logger.debug("shiro session id {} 被删除", session.getId());
        } catch (Exception e){
            logger.warn("删除session失败",e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除cache中缓存的Session
     */
    public void uncache(Serializable sessionId) {
        try {
            Session session = super.getCachedSession(sessionId);
            super.uncache(session);
            logger.debug("shiro session id {} 的缓存失效", sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * SessionManager创建完session后会调用该方法
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session,sessionId);
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //session由Redis缓存失效决定，这里作简单标识
            session.setTimeout(seconds);
            jedis.setex(prefix + sessionId, seconds, SerializeUtils.serializaToString((ShiroSession) session));
            logger.info("sessionId {} name {} 被创建", sessionId, session.getClass().getName());
        }catch (Exception e){
            logger.warn("创建session失败",e);
        }finally {
            jedis.close();
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("begin doReadSession {}", sessionId);
        Jedis jedis = jedisPool.getResource();
        Session session = null;
        try {
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if(StringUtils.isNotBlank(value)){
                session = SerializeUtils.deserializeFromString(value);
                logger.info("sessionId {} ttl {}: ", sessionId, jedis.ttl(key));
                //重置Redis中缓存过期的时间
                jedis.expire(key,seconds);
                logger.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
            }
        } catch (Exception e){
            logger.warn("读取session失败");
        } finally {
            jedis.close();
        }
        return session;
    }

    /**
     * 从Redis中读取，但不重置Redis中缓存过期时间
     */
    public Session doReadSessionWithoutExpire(Serializable sessionId) {
        Session session = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String key = prefix + sessionId;
            String value = jedis.get(key);
            if(StringUtils.isNotBlank(value)){
                session = SerializeUtils.deserializeFromString(value);
            }
        } catch (Exception e){
            logger.warn("读取Session失败", e);
        } finally {
            jedis.close();
        }
        return session;
    }

    /**
     * 获取当前所有活跃用户
     */
    @Override
    public Collection<Session> getActiveSessions(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> keys = jedis.keys(prefix + "*");
            if(CollectionUtils.isEmpty(keys)){
                return null;
            }
            List<String> values = jedis.mget(keys.toArray(new String[keys.size()]));
            return SerializeUtils.deserializeFromStrings(values);
        } catch (Exception e){
            logger.warn("统计Session信息失败", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 返回本机Ehcache中Session
     */
    public Collection<Session> getEhCacheActiveSessions() {
        return super.getActiveSessions();
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
