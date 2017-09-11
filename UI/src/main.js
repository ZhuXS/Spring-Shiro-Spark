/**
 * Created by shusesshou on 2017/9/8.
 */
import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './app.vue'
import RouterConfig from './router'


//iView UI
import iView from 'iview';
import 'iview/dist/styles/iview.css';

//加载路由中间件
Vue.use(iView)
Vue.use(VueRouter)

//定义路由
const router = new VueRouter(RouterConfig)

new Vue({
    el: '#app',
    router: router,
    render: h => h(App)
})
