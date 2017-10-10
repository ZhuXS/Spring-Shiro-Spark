/**
 * Created by shusesshou on 2017/9/8.
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from './app.vue'
import router from './router/index'
import store from './store'

//agile for index
import VueAgile from 'vue-agile'
Vue.use(VueAgile)

//iView UI
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import './permission'

//加载路由中间件
Vue.use(iView)
Vue.use(Vuex)

//Global Error Handler
Vue.config.errorHandler = function (err,vm,info) {
    //Server Error
    if(err.response){
        //not Auth
        if(err.response.data.errorCode === 40010){
            Vue.$router.push({
                path:'/login'
            })
        }
    }
}

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
