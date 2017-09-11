<template>
    <div id="app">
        <nav-menu></nav-menu>
        <br/>
        <wordCountPanel></wordCountPanel>
    </div>
</template>

<script>
    import navMenu from './views/navMenu.vue'
    import wordCountPanel from './views/wordCountPanel.vue'
    export default{
        data () {
            return {msg: 'hi'}
        },
        components:{
            navMenu,
            wordCountPanel
        }
    }
</script>

<style>

</style>