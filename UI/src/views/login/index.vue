<template>
    <div v-if="status" id="form">
        <signed></signed>
    </div>
    <div v-else id="guide">
        <img id="img" src="../../../images/login.jpeg"/>
        <signIn></signIn>
    </div>
</template>

<style>
    #img{
        width: 500px;
        height: 100px;
    }
    #form{
        width: 500px;/* 宽度值，随便啦 */
        height:500px;/* 高度值，随便啦 */
        position: absolute;
        margin: auto;
        top: 0; left: 0; right: 0; bottom: 0;
        overflow: auto;
    }
    #guide{
        width: 500px;/* 宽度值，随便啦 */
        height:500px;/* 高度值，随便啦 */
        position: absolute;
        margin: auto;
        top: 0; left: 0; right: 0; bottom: 0;
        overflow: auto;
    }
</style>

<script>
    import store from  '../../store'
    import signIn from "./signIn.vue"
    import signed from "./signed.vue"
    export default{
        components: {
            signIn,
            signed
        },
        data () {
            return {
                msg: 'hello',
                status: store.getters.status
            }
        }
    }
</script>