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
                }
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
            cancel() {

            },
        }
    }
</script>
