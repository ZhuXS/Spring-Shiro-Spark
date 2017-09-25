package com.zhuxs.result.shiro;

import com.zhuxs.result.utils.SerializeUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by shusesshou on 2017/9/24.
 */
@Service
public class RedisSessionDao extends AbstractSessionDAO{

    private static final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);
    // 保存到Redis中key的前缀 prefix+sessionId
    private String prefix = "";
    // 设置会话的过期时间
    private int expireTime = 30;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 如DefaultSessionManager在创建完session后会调用该方法；
     * 如保存到关系数据库/文件系统/NoSQL数据库；即可以实现会话的持久化；
     * 返回会话ID；主要此处返回的ID.equals(session.getId())；
     */
    @Override
    protected Serializable doCreate(Session session) {
        // 创建一个Id并设置给Session
        Serializable sessionId = this.generateSessionId(session);
        assignSessionId(session, sessionId);
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            if (session instanceof SimpleSession) {
                byte[] key = SerializationUtils.serialize(prefix + sessionId);
                byte[] value = SerializationUtils.serialize(((SimpleSession) session));
                jedis.setex(key, expireTime, value);
            } else if (session instanceof Serializable) {
                byte[] key = SerializationUtils.serialize(prefix + sessionId);
                byte[] value = SerializationUtils.serialize((Serializable) session);
                jedis.setex(key, expireTime, value);
            } else {
                throw new RuntimeException("Session不能被序列化");
            }
        }catch (Exception e){
            logger.warn("创建Session失败", e);
        }finally {
            jedis.close();
        }

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        Session session = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            byte[] key = SerializationUtils.serialize(prefix + sessionId);
            byte[] value = jedis.get(key);
            if(value != null){
                session = SerializationUtils.deserialize(value);
                logger.info("sessionId ttl : " + jedis.ttl(key));
                jedis.expire(key, expireTime);
                logger.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
            }
        }catch (Exception e){
            logger.warn("读取Session失败", e);
        }finally {
            jedis.close();
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (session instanceof SimpleSession) {
                byte[] key = SerializationUtils.serialize(prefix + session.getId());
                byte[] value = SerializationUtils.serialize(((SimpleSession) session));
                jedis.setex(key, expireTime, value);
                logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else if (session instanceof Serializable) {
                byte[] key = SerializationUtils.serialize(prefix + session.getId());
                byte[] value = SerializationUtils.serialize((Serializable) session);
                jedis.setex(key, expireTime, value);
                logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else {
                throw new RuntimeException("Session不能被序列化");
            }
        } catch (Exception e) {
            logger.warn("修改Session失败", e);
        } finally {
            jedis.close();
        }
    }

    @Override
    public void delete(Session session) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(SerializationUtils.serialize(prefix + session.getId()));
        } catch (Exception e){
            logger.warn("删除Session失败", e);
        } finally {
            jedis.close();
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
