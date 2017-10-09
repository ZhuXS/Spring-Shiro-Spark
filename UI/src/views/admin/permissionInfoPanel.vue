<template>
    <div id="table">
        <Table border :loading="loading" :columns="userColumns" :data="userData"></Table>
        <br/>
        <Button @click="newPermission">New Permission</Button>
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
    import newPermissionPanel from './newPermissionPanel.vue'
    export default {
        data () {
            return {
                loading: true,
                userColumns: [
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
                        title: 'type',
                        key: 'type'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
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
                                }, '查看'),
                                h('Button', {
                                    props: {
                                        type: 'ghost',
                                        size: 'small'
                                    },
                                    on: {
                                        click: () => {
                                            this.remove(params.index)
                                        }
                                    }
                                }, '删除')
                            ]);
                        }
                    }

                ],
                permissionData: [
                ]
            }
        },
        created: function () {
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
        methods: {
            newPermission() {
                this.$Modal.confirm({
                    render: (h) => {
                        return h(
                            newPermissionPanel
                        )
                    }
                })
            }
        }
    }
</script>
