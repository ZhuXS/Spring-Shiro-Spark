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

//test the user login or not and get the userInfo
export function getUserInfo() {
    return fetch({
        url:'/userInfo',
        method:'get'
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
