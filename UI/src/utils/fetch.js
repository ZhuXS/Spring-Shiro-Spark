/**
 * Created by shusesshou on 2017/9/21.
 */
import axios from 'axios'
import { getToken } from './auth'
import store from '../store'

//创建axios实例
const service = axios.create({
    baseURL: "http://localhost:8081",
    timeout: 5000,  //请求超时时间
    withCredentials : true
})

//是否保留跨域请求凭证
service.defaults.withCredentials = true

//request拦截器
service.interceptors.request.use(config => {
    if(store.getters.status){
        //take token or sessionId
    }
    return config
},error => {
    console.log(error)
    Promise.reject(error)
})

//response拦截器
service.interceptors.response.use(response => {
    //alert(response.data)
    return response
},error => {
    //alert(error.response.data.errorCode.code)
    if(error.response.data.errorCode.code === 40100){
        window.location .href = "login"
        return Promise.reject(error)
    } else if(error.response.data.errorCode.code === 40101){
        window.location.href = "dashboard"
        return Promise.reject(error)
    }else {
        return Promise.reject(error)
    }
})

export default service