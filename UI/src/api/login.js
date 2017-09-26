/**
 * Created by shusesshou on 2017/9/21.
 */
import fetch from '../utils/fetch'

export function login(username, password) {
    const data = {
        username,
        password
    }
    return fetch({
        url: '/login',
        method: 'post',
        data
    })
}

export function logout(sessionId) {
    const data = {
        sessionId
    }
    return fetch({
        url:'/logout',
        method: 'post',
        data
    })
}
