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
    if(getSessionId()) {
        if(to.path === '/login'){
            next({path: '/'})
        }else {
            //if(store.getters.)
            if(store.getters.state){
                //用户已经登录

            }
        }
    }
})
