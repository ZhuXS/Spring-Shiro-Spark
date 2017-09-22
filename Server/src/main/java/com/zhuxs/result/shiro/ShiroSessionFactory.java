package com.zhuxs.result.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by shusesshou on 2017/9/22.
 */
public class ShiroSessionFactory implements SessionFactory{
    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionFactory.class);

    @Override
    public Session createSession(SessionContext sessionContext) {
        ShiroSession session = new ShiroSession();
        return session;
    }
}
