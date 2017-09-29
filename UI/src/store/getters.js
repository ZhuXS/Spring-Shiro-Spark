/**
 * Created by shusesshou on 2017/9/21.
 */
const getters = {
    status: state => state.status,
    username: state => state.username,
    name: state => state.name,
    roles: state => state.roles,
    addRouters: state => state.permission.addRouters
}

export default getters
