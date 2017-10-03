/**
 * Created by shusesshou on 2017/9/26.
 */
import router from './router'
import store from  './store'
import { getSessionId } from "./utils/auth"
import NProgress from 'nprogress' //进度条
import 'nprogress/nprogress.css'

//permission judge
function hasPermission(roles,permissions) {
    if (roles.indexOf('admin') > 0) return true //admin权限，直接通过
    if(!permissions) return true

}

//register global progress
const whiteList = ['/login'] //不重定向白名单
router.beforeEach((to,from,next) => {
    NProgress.start(); //开启Progress
    //alert(getSessionId())
    //alert(store.getters.status)
    alert("sessionId: " + getSessionId())
    if(store.getters.status) {
        if(to.path === '/login'){
            next({path: '/'})
        }else {
            if(store.getters.status){
                //用户已经登录
                //生成可以访问的路由表
                store.dispatch('GenerateRoutes',store.getters.roles).then(() => {
                    //动态添加可以访问的路由表
                    router.addRoutes(store.getters.addRouters)
                    next({
                        to
                    })
                })
            } else {
                next({path: '/'})
            }
        }
    } else {
        if(whiteList.indexOf(to.path) !== -1){  //在登录白名单，直接进入
            next();
        } else {
            next('/login') //否则全部定向到登录页
        }
    }
})

router.afterEach(() => {
    NProgress.done() //结束Progress
})