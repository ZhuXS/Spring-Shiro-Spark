<template>
    <div id="table">
        <Table border :loading="loading" :columns="roleColumns" :data="roleData"></Table>
        <br/>
        <Button @click="showModal">New Role</Button>
        <Modal
                v-model="modal"
                title="New Permission"
                @on-ok="addRole"
                @on-cancel="cancel">
            <Form :model="formItem" :label-width="80">
                <FormItem label="Name">
                    <Input v-model="formItem.name" placeholder="Role Name"></Input>
                </FormItem>
                <FormItem label="Description">
                    <Input v-model="formItem.description" placeholder="Role Description"></Input>
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
                roleColumns: [
                    {
                        title: 'id',
                        key: 'id'
                    },
                    {
                        title: 'name',
                        key: 'name'
                    },
                    {
                        title: 'desc',
                        key: 'desc'
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
                                            this.removeRole(params.index)
                                        }
                                    }
                                }, 'Delete')
                            ]);
                        }
                    }

                ],
                roleData: [
                ],
                formItem: {
                    name: '',
                    description: ''
                }
            }
        },
        created: function () {
            this.getRoles()
        },
        methods: {
            showModal() {
               this.modal = true
            },
            getRoles() {
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/roles',
                        method: 'get',
                    }).then(response => {
                        const data = response.data
                        this.roleData = data
                        this.loading = false
                    })
                })
            },
            addRole() {
                var data = {
                    name: this.formItem.name,
                    desc: this.formItem.description
                }
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/roles',
                        method: 'post',
                        data
                    }).then(response => {
                        this.$Message.success('创建成功');
                        this.getRoles()
                    })
                })
            },
            removeRole(index) {
                var id = this.roleData[index].id
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/roles/' + id,
                        method: 'delete',
                    }).then(response => {
                        this.$Message.success('删除成功');
                        this.getRoles()
                    })
                })
            },
            cancel() {

            },
        }
    }
</script>
