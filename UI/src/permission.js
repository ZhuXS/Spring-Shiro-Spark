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
const whiteList = ['/login','/'] //不重定向白名单
router.beforeEach((to,from,next) => {
    NProgress.start(); //开启Progress
    if(store.getters.status) {
        next
    } else {
        if(whiteList.indexOf(to.path) !== -1){  //在登录白名单，直接进入
            //alert("test")
            next();
        } else {
            store.dispatch('GetUserInfo').then(res => {
                //拉取user_info,并测试用户是否登录
                const roles = res.data.roles
                store.dispatch('GenerateRoutes',roles).then(() => {
                    console.log(store.getters.addRouters)
                    router.addRoutes(store.getters.addRouters) //动态添加可访问路由表
                    console.log(router)
                    next()
                    //next({ ...to })
                })
            }).catch(e => {
                if(e.response.data.errorCode.code === 40010){
                    next('/login') //否则全部定向到登录页
                }
                if(e.response.data.errorCode.code === 1)
                next('/login') //否则全部定向到登录页
            })
        }
    }
})

router.afterEach(() => {
    NProgress.done() //结束Progress
})