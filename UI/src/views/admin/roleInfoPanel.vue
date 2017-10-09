<template>
    <div id="table">
        <Table border :loading="loading" :columns="userColumns" :data="userData"></Table>
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
                userColumns: [
                    {
                        title: 'id',
                        key: 'id'
                    },
                    {
                        title: 'username',
                        key: 'username'
                    },
                    {
                        title: 'password',
                        key: 'password'
                    },
                    {
                        title: 'name',
                        key: 'name'
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
                userData: [
                ]
            }
        },
        created: function () {
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
        methods: {

        }
    }
</script>
