<template>
    <div id="form">
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
</style>

<script>
    import signIn from "./signIn.vue"
    export default{
        components: {
            signIn
        },
        data () {
            return {msg: 'hello'}
        }
    }
</script>