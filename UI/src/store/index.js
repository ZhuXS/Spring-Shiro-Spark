/**
 * Created by shusesshou on 2017/9/21.
 */
import user from './modules/user'
import getters from './getters'

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        user
    },
    getters
})

export default store
