/**
 * Created by shusesshou on 2017/9/21.
 */
import Cookies from 'js-cookie'
import { login,logout,getUserInfo } from '../../api/login'
import {getSessionId,setSessionId,removeSessionId} from '../../utils/auth'

const user = {
    state: {
        username: '',
        name: '',
        sessionId: getSessionId(),
        roles:[],
        status: ''
    },
    mutations: {
        SET_USERNAME: (state, username) => {
            state.username = username
        },
        SET_NAME: (state, name) => {
            state.name = name
        },
        SET_SESSIONID: (state,sessionId) => {
            state.sessionId = sessionId
        },
        SET_ROLES: (state,roles) => {
            state.roles = roles
        },
        SET_STATUS: (state, status) => {
            state.status = status
        }
    },
    actions: {
        //用户名登录
        Login({ commit }, userInfo){
            const username = userInfo.username.trim()
            return new Promise((resolve, reject) => {
                login(username,userInfo.password).then(response => {
                    commit('SET_STATUS',true)
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        },

        //获取用户信息
        GetUserInfo({ commit } ){
            return new Promise((resolve,reject) => {
                getUserInfo().then(() => {
                    commit('SET_NAME',response.name)
                    commit('SET_USERNAME',response.username)
                    commit('SET_ROLES',response.roles)
                    //commit('SET_')
                    commit('SET_STATUS',true)
                })
            })
        },

        //登出
        LogOut({ commit,state }) {
            return new Promise((resolve,reject) => {
                logout(state.sessionId).then(() => {
                    commit('SET_NAME',"")
                    commit('SET_USERNAME',"")
                    commit('SET_SESSIONID',"")
                    commit('SET_ROLES',"")
                    //commit('SET_')
                    commit('SET_STATUS',false)
                    removeSessionId()
                    resolve()
                })
            })
        }
    }
}

export default user

