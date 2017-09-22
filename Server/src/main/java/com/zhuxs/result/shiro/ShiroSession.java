package com.zhuxs.result.shiro;

import org.apache.shiro.session.mgt.SimpleSession;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by shusesshou on 2017/9/22.
 * SimpleSession lastAccessTime更改后也会调用SessionDao update方法
 * 增加标识位，如果只是更新lastAccessTime方法直接返回
 */
public class ShiroSession extends SimpleSession implements Serializable{
    private Boolean isChanged;

    public ShiroSession(){
        super();
        this.setIsChanged(true);
    }

    public ShiroSession(String Host){
        super(Host);
        this.setIsChanged(true);
    }

    @Override
    public void setId(Serializable id){
        super.setId(id);
        this.setIsChanged(true);
    }

    @Override
    public void setStopTimestamp(Date stopTimeStamp){
        super.setStopTimestamp(stopTimeStamp);
        this.setIsChanged(true);
    }

    @Override
    public void setExpired(boolean expired){
        super.setExpired(expired);
        this.setIsChanged(true);
    }

    @Override
    public void setTimeout(long timeout){
        super.setTimeout(timeout);
        this.setIsChanged(true);
    }

    @Override
    public void setHost(String host){
        super.setHost(host);
        this.setIsChanged(true);
    }

    @Override
    public void setAttributes(Map<Object,Object> attributes){
        super.setAttributes(attributes);
        this.setIsChanged(true);
    }

    @Override
    public void setAttribute(Object key, Object value){
        super.setAttribute(key,value);
        this.setIsChanged(true);
    }

    @Override
    public Object removeAttribute(Object key){
        this.setIsChanged(true);
        return super.removeAttribute(key);
    }

    @Override
    protected void expire(){
        this.stop();
        this.setExpired(true);
    }

    public Boolean getIsChanged(){
        return isChanged;
    }

    public void setIsChanged(Boolean isChanged){
        this.isChanged = isChanged;
    }

    @Override
    public boolean equals(Object object){
        return super.equals(object);
    }

    @Override
    protected boolean onEquals(SimpleSession ss){
        return super.onEquals(ss);
    }

    @Override
    public int hashCode(){
        return super.hashCode();
    }

    @Override
    public String toString(){
        return super.toString();
    }
}
