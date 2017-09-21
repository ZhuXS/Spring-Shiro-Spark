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
        url: '/',
        method: 'post',
        data
    })
}
