/**
 * Created by shusesshou on 2017/9/8.
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import App from './app.vue'
import router from './router/index'
import store from './store'


//iView UI
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import './permission'

//加载路由中间件
Vue.use(iView)
Vue.use(Vuex)

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
