<template>
    <div>
        <Table :loading="loading" :columns="userColumns" :data="userData"></Table>
        <br>
        切换 Loading 状态 <Switch v-model="loading"></Switch>
    </div>
</template>
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
