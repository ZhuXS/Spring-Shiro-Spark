<template>
    <div id="table">
        <Table border :loading="loading" :columns="permissionColumns" :data="permissionData"></Table>
        <br/>
        <Button @click="showModal">New Permission</Button>
        <Modal
                v-model="modal"
                title="New Permission"
                @on-ok="addPermission"
                @on-cancel="cancel">
            <Form :model="formItem" :label-width="80">
                <FormItem label="type">
                    <Select v-model="formItem.resource" placeholder="resource type">
                        <Option value="0">WordCount</Option>
                    </Select>
                </FormItem>
                <FormItem label="action">
                    <Select v-model="formItem.action" placeholder="action type">
                        <Option value="0">ALL</Option>
                        <Option value="1">CREATE</Option>
                        <Option value="2">UPDATE</Option>
                        <Option value="3">READ</Option>
                        <Option value="4">DELETE</Option>
                    </Select>
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
                permissionColumns: [
                    {
                        title: 'id',
                        key: 'id'
                    },
                    {
                        title: 'name',
                        key: 'name'
                    },
                    {
                        title: 'resource',
                        key: 'resource'
                    },
                    {
                        title: 'resourceType',
                        key: 'resourceType'
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
                                            this.removePermission(params.index)
                                        }
                                    }
                                }, 'Delete')
                            ]);
                        }
                    }

                ],
                permissionData: [
                ],
                formItem: {
                    resource: '',
                    action: ''
                }
            }
        },
        created: function () {
            this.getPermissions()
        },
        methods: {
            showModal() {
               this.modal = true
            },
            getPermissions() {
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/permissions',
                        method: 'get',
                    }).then(response => {
                        const data = response.data
                        this.permissionData = data
                        this.loading = false
                    })
                })
            },
            addPermission() {
                var data = {
                    resourceType: this.formItem.resource,
                    action:this.formItem.action
                }
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/permissions',
                        method: 'post',
                        data
                    }).then(response => {
                        this.$Message.success('创建成功');
                        this.getPermissions()
                    })
                })
            },
            removePermission(index) {
                var id = this.permissionData[index].id
                return new Promise((resolve,reject) => {
                    fetch({
                        url: '/admin/permissions/' + id,
                        method: 'delete',
                    }).then(response => {
                        this.$Message.success('删除成功');
                        this.getPermissions()
                    })
                })
            },
            cancel() {

            },
        }
    }
</script>
