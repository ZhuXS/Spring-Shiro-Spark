<template>
    <div>
        <nav-menu></nav-menu>
        <br/>
        <word-count-panel></word-count-panel>
    </div>
</template>

<style>
    body {
    }
</style>

<script>
    import navMenu from './navMenu.vue'
    import wordCountPanel from './wordCountPanel.vue'
    export default{
        components:{
            navMenu,
            wordCountPanel
        },
        data () {
            return {msg: 'hello'}
        }
    }
</script>