/**
 * Created by shusesshou on 2017/9/21.
 */
import user from './modules/user'
import permission from './modules/permission'
import getters from './getters'

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        user,
        permission
    },
    getters
})

export default store
