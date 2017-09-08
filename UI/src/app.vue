<template>
    <div>
        <div>
            <router-link to="/index">HOME</router-link>
        </div>
        <div>
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    export default{
        data () {
            return {msg: 'hi'}
        }
    }
</script>