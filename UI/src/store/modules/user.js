/**
 * Created by shusesshou on 2017/9/21.
 */
import Cookies from 'js-cookie'
import { login } from '../../api/login'

const user = {
    state: {
        username: '',
        status: ''
    },
    mutations: {
        SET_USERNAME: (state, username) => {
            state.username = username;
        },
        SET_STATUS: (state, status) => {
            state.status = status;
        }
    },
    actions: {
        //用户名登录
        Login({ commit }, userInfo){
            const username = userInfo.username.trim()
            return new Promise((resolve, reject) => {
                login(username,userInfo.password).then(response => {
                    const data = response.data;
                    commit('SET_USERNAME',data.username)
                    commit('SET_STATUS',true)
                    resolve();
                }).catch(error => {
                    reject(error)
                })
            })
        }
    }
}

export default user

