<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="person"></Icon>
                 {{ name }}
            </p>

        </Card>
    </div>
</template>

<style>
</style>

<script>
    import store from  '../../store'
    export default{
        data () {
            return {
                name: store.getters.name
            }
        }
    }
</script>