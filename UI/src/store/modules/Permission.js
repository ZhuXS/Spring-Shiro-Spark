/**
 * Created by shusesshou on 2017/9/28.
 */
import { asyncRouterMap,constantRouterMap } from "../../router"

/**
 * 通过meta.role判断是否与当前用户权限匹配
 */
function hasPermission(roles,route) {
    if(route.meta && route.meta.roles) {
        return roles.some(roles => route.meta.indexOf(roles) >= 0)
    } else {
        return true
    }
}

