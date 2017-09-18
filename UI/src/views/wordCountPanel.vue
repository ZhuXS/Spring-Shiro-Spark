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
        <Alert v-if="words.trim().length == 0" banner closable type="info" show-icon>请输入需要统计的词</Alert>
    </div>
</template>

<style>
</style>

<script>
    import axios from 'axios'
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
                var postData = {"words" : this.words}
                axios.post("http://localhost:8081/jobs/0",postData).then(response => {
                    alert(response.status)
                    this.data = response.data
                    this.loading = false
                }).catch(e => {
                    alert(e)
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