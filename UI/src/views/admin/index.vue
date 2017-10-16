<template>
    <Tabs>
        <TabPane label="user" icon="person">
            <user-info-panel></user-info-panel>
        </TabPane>
        <TabPane label="role" icon="bowtie">
            <role-info-panel></role-info-panel>
        </TabPane>
        <TabPane label="permission" icon="ribbon-b">
            <permission-info-panel></permission-info-panel>
        </TabPane>
    </Tabs>
</template>

<style>

</style>

<script>
    import userInfoPanel from './userInfoPanel.vue'
    import roleInfoPanel from './roleInfoPanel.vue'
    import permissionInfoPanel from './permissionInfoPanel.vue'
    export default{
        components:{
            userInfoPanel,
            roleInfoPanel,
            permissionInfoPanel,
        },
        data () {
            return {msg: 'hello'}
        },
    }
</script>