/**
 * Created by shusesshou on 2017/9/8.
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from './app.vue'
import RouterConfig from './router'
import store from './store'


//iView UI
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//加载路由中间件
Vue.use(iView)
Vue.use(VueRouter)
Vue.use(Vuex)

//定义路由
const router = new VueRouter(RouterConfig)

new Vue({
    el: '#app',
    router: router,
    store,
    render: h => h(App)
})
