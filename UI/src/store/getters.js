/**
 * Created by shusesshou on 2017/9/21.
 */
const getters = {
    status: state => state.user.status,
    username: state => state.user.username,
    name: state => state.user.name,
    roles: state => state.user.roles,
    addRouters: state => state.permission.addRouters
}

export default getters
