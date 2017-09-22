/**
 * Created by shusesshou on 2017/9/21.
 */
const getters = {
    status: state => state.userstatus,
    wordCount: state => state.permission.wordCount,
    userManagerment: state => state.role.admin
}

export default getters
