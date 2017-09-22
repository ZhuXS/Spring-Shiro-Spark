<template>
    <div>
        <signIn></signIn>
    </div>
</template>

<style>
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