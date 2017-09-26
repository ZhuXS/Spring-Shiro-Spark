/**
 * Created by shusesshou on 2017/9/21.
 */
import Cookies from 'js-cookie'

const sessionId = 'JSESSIONID'

export function getSessionId() {
    return Cookies.get(sessionId)
}

export function setSessionId(token) {
    return Cookies.set(sessionId,token)
}

export function removeSessionId() {
    return Cookies.remove(sessionId)
}
