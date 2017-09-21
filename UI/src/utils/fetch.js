/**
 * Created by shusesshou on 2017/9/21.
 */
import axios from 'axios'
import { getToken } from './auth'

//创建axios实例
const service = axios.create({
    baseURL: "localhost:8081",
    timeout: 5000   //请求超时时间
})

//request拦截器
