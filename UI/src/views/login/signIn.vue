<template>
    <Form ref="userInfo" :model="userInfo" :rules="ruleInline">
        <FormItem prop="user">
            <Input type="text" v-model="userInfo.username" placeholder="Username">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem prop="password">
            <Input type="password" v-model="userInfo.password" placeholder="Password">
            <Icon type="ios-locked-outline" slot="prepend"></Icon>
            </Input>
        </FormItem>
        <FormItem>
            <Button type="primary" @click="handleSubmit('userInfo')">登录</Button>
        </FormItem>
    </Form>
</template>
<script>
    export default {
        data () {
            return {
                userInfo: {
                    username: '',
                    password: ''
                },
                ruleInline: {
                    username: [
                        { required: true, message: '请填写用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请填写密码', trigger: 'blur' },
                        { type: 'string', min: 1, message: '密码长度不能小于6位', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$store.dispatch('Login',this.userInfo).then(() => {
                            this.$router.push({
                                path: '/dashboard'
                            })
                            //this.$Message.success('提交成功!');
                        }).catch(e => {
                            this.$Notice.error({
                                title: e.response.data.errorCode.desc,
                                desc: e.response.data.msg,
                                duration: 2
                            })
                        })
                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                })
            }
        }
    }
</script>
