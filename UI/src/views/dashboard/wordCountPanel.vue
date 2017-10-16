<template>
    <div>
        <Row>
            <Col span="12">
                <ButtonGroup>
                    <Button>上传文件</Button>
                    <Button @click="cleanWords">清空</Button>
                </ButtonGroup>
            </Col>
            <Col span="12">
                <ButtonGroup>
                    <Button type="primary" :loading=loading @click="count">
                        <span v-if="!loading">Count</span>
                        <span v-else>Counting...</span>
                    </Button>
                    <Button @click="cleanTable">清空</Button>
                </ButtonGroup>
            </Col>
        </Row>
        <br/>
        <Row>
            <Col span="12">
                <Input type="textarea" v-model="words" :rows="14"  placeholder="Word"></Input>
            </Col>
            <Col span="12">
                <Table height="304" :columns="columns" :data="data"></Table>
            </Col>
        </Row>
        <br/>
    </div>
</template>

<style>
</style>

<script>
    import fetch from '../../utils/fetch'
    export default{
        data () {
            return {
                columns: [
                    {
                        title: 'Word',
                        key: 'word'
                    },
                    {
                        title: 'Count',
                        key: 'count'
                    }
                ],
                data:[

                ],
                loading:false,
                words:""
            }
        },
        methods: {
            count () {
                this.loading = true
                if(this.words.trim() == ""){
                    this.$Notice.warning({
                        title: 'WARNING',
                        desc: "请输入要统计的文字",
                        duration: 1
                    })
                    this.loading = false
                    return
                }
                var data = {"words" : this.words}
                return new fetch({
                    url:'/jobs/0',
                    method:"post",
                    data
                }).then(res => {
                    this.data = res.data
                    this.loading = false
                }).catch(e => {
                    this.$Notice.error({
                        title: e.response.data.errorCode.desc,
                        desc: e.response.data.msg,
                        duration: 2
                    })
                    this.loading = false
                })
            },
            cleanWords() {
                this.words = ""
            },
            cleanTable() {
                this.data = []
            }
        }
    }
</script>