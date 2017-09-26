<template>
    <user-info-panel></user-info-panel>
</template>

<style>

</style>

<script>
    import userInfoPanel from './userInfoPanel.vue'
    export default{
        components:{
            userInfoPanel
        },
        data () {
            return {msg: 'hello'}
        }
    }
</script>