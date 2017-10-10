<template>
    <div id="table">
        <Table border :loading="loading" :columns="userColumns" :data="userData"></Table>
        <br/>
        <Button @click="showModal">New User</Button>
        <Modal
                v-model="modal"
                title="New User"
                @on-ok="addUser"
                @on-cancel="cancel">
            <Form :model="formItem" :label-width="80">
                <FormItem label="User Name">
                    <Input v-model="formItem.username" placeholder="User Name"></Input>
                </FormItem>
                <FormItem label="Name">
                    <Input v-model="formItem.name" placeholder="Name"></Input>
                </FormItem>
                <FormItem label="Password">
                    <Input v-model="formItem.password" placeholder="Password"></Input>
                </FormItem>
                <FormItem label="Salt">
                    <Input v-model="formItem.salt" placeholder="Salt"></Input>
                </FormItem>
            </Form>
        </Modal>
        <Modal
                v-model="userModal"
                title="User Management"
                @on-cancel="cancel">
            <Tabs value="Role">
                <TabPane label="Role" name="Role">
                    <Transfer
                            :data="rolesData"
                            :target-keys="rolesTarget"
                            :render-format="renderRoles"
                            :titles='["All","User"]'
                            @on-change="changeRoles"
                            ></Transfer>
                    <br/>
                    <Button @click="updateUserRoles">Assign Roles</Button>
                </TabPane>
                <TabPane label="Permission" name="Permission">标签二的内容</TabPane>
            </Tabs>
            <div slot="footer">
                <Button type="primary" size="large" long @click="cancelManagement">Cancel</Button>
            </div>
        </Modal>
    </div>
</template>
<style>
    #table{
        width: 99%;
        margin-left: auto;
        margin-right: auto;
    }
</style>
<script>
    import fetch from '../../utils/fetch'
    export default {
        data () {
            return {
                loading: true,
                modal: false,
                userModal: false,
                userColumns: [
                    {
                        title: 'Id',
                        key: 'id'
                    },
                    {
                        title: 'UserName',
                        key: 'username'
                    },
                    {
                        title: 'Name',
                        key: 'name'
                    },
                    {
                        title: 'Password',
                        key: 'password'
                    },
                    {
                        title: 'Salt',
                        key: 'salt'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'ghost',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.index)
                                        }
                                    }
                                }, 'Manage'),
                                h('Button', {
                                    props: {
                                        type: 'ghost',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.removeUser(params.index)
                                        }
                                    }
                                }, 'Delete')
                            ]);
                        }
                    }

                ],
                userData: [
                ],
                formItem: {
                    name: '',
                    username: '',
                    password: '',
                    salt: ''
                },
                userRoles:[
                ],
                rolesTarget:[

                ],
                allRoles:[
                ],
                rolesData:[

                ],
                userPermissions:[
                ],
                permissionsTarget:[

                ],
                allPermissions:[
                ],
                permissionsData:[

                ],
                currentUserId:''
            }
        },
        created: function () {
            this.getUsers()
        },
        methods: {
            showModal() {
               this.modal = true
            },
            getUsers() {
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/users',
                        method: 'get',
                    }).then(response => {
                        const data = response.data
                        this.userData = data
                        this.loading = false
                    })
                })
            },
            addUser() {
                var data = {
                    name: this.formItem.name,
                    username: this.formItem.username,
                    password: this.formItem.password,
                    salt: this.formItem.salt
                }
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/users',
                        method: 'post',
                        data
                    }).then(response => {
                        this.$Message.success('创建成功');
                        this.getUsers()
                    })
                })
            },
            removeUser(index) {
                var id = this.userData[index].id
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/users/' + id,
                        method: 'delete',
                    }).then(response => {
                        this.$Message.success('删除成功');
                        this.getUsers()
                    })
                })
            },
            show(index) {
                this.currentUserId = this.userData[index].id
                Promise.all([
                    this.getRoles(),
                    this.getPermissions(),
                    this.getRolesByUserId(index),
                    this.getPermissionsByUserId(index)
                ]).then(() => {
                    this.getRolesData()
                    this.getPermissionData()
                    this.getRolesTarget()
                    this.getPermissionsTarget()
                    this.userModal = true
                })
            },
            cancelManagement() {
                this.userModal = false
            },
            cancel() {

            },
            getRolesByUserId(index){
                var id = this.userData[index].id
                return new Promise((resolve,reject) => {
                    fetch({
                        url:'/admin/roles?userId=' + id,
                        method:'get'
                    }).then(response => {
                        this.userRoles = response.data
                        resolve()
                    })
                })
            },
            getRoles(){
                return new Promise((resolve,reject) => {
                    fetch({
                        url: "/admin/roles",
                        method:'get'
                    }).then(response => {
                        this.allRoles = response.data
                        resolve()
                    })
                })
            },
            getPermissionsByUserId(index){
                var id = this.userData[index].id
                return new Promise((resolve,reject) => {
                    fetch({
                        url:'/admin/permissions?userId=' + id,
                        method:'get'
                    }).then(response => {
                        this.userPermissions = response.data
                        resolve()
                    })
                })
            },
            getPermissions(){
                return new Promise((resolve,reject) => {
                    fetch({
                        url: "/admin/permissions",
                        method:'get'
                    }).then(response => {
                        this.allPermissions = response.data
                        resolve()
                    })
                })
            },
            updateUserRoles(){
                return new Promise((resolve,reject) => {
                    fetch({
                        url: "/admin/users/" + this.currentUserId +"/roles",
                        method: "put",
                        data:this.allRoles.filter(item => {
                            for(let i = 0; i < this.rolesTarget.length;i++){
                                if(item.id.toString() === this.rolesTarget[i]){
                                    return true
                                }
                            }
                        })
                    }).then(() => {
                        this.$Message.success("Assign 成功")
                        this.userModal = false
                    })
                })
            },
            renderRoles(item){
                return item.label
            },
            getRolesData(){
                this.rolesData = []
                for(let i = 0;i < this.allRoles.length;i++){
                    this.rolesData.push({
                        key: this.allRoles[i].id.toString(),
                        label:this.allRoles[i].name + "-" + this.allRoles[i].desc,
                        disabled:false
                    })
                }
            },
            getPermissionData(){
                this.permissionsData = []
                for(let i = 0;i < this.allPermissions.length;i++){
                    this.permissionsData.push({
                        key: this.allPermissions[i].id.toString(),
                        label:this.allPermissions[i].name,
                        disabled:false
                    })
                }
            },
            getRolesTarget(){
                this.rolesTarget = this.userRoles.map(item => {
                    return item.id.toString()
                })
                //alert(this.rolesTarget.length)
            },
            getPermissionsTarget(){
                this.permissionsTarget = this.userPermissions.map(item => {
                    return item.id.toString()
                })
            },
            changeRoles(roleKeys){
                this.rolesTarget = roleKeys
            },
            changePermissions(permissionKeys){
                this.permissionsTarget = permissionKeys
            }
        }
    }
</script>
