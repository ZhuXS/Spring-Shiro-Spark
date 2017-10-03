/**
 * Created by shusesshou on 2017/9/21.
 */
import Vue from 'vue'
import Router from "vue-router"

const _import = require('./_import_development')

Vue.use(Router)

//所有权限通用路由表
export const constantRouterMap = [
    {
        path: '/',
        component: _import('index/index'),
        name: "hello"
    },
    {
        path: '/dashboard',
        component: _import('dashboard/index'),
        name: "RESult",
    },
    {
        path: "/login",
        component: _import('login/index'),
        name: "login"
    }
]

export default new Router({
    mode: 'history',
    routes: constantRouterMap
})

export const asyncRouterMap = [
    {
        path: "/admin",
        component: _import('admin/index'),
        name: "admin"
    }
]