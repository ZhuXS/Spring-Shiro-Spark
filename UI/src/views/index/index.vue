<template>
    <main>
        <agile :arrows="false" :speed="750"
               :timing="'linear'" :fade="true"
               :autoplay="true" :pauseOnHover="false">
            <div class="slide slide--1"></div>
            <div class="slide slide--2"></div>
            <div class="slide slide--3"></div>
        </agile>
    </main>
</template>
<style lang="scss">
    .agile {
        &__dots {
            bottom: 0;
            display: block;
            left: 10px;
            position: absolute;
            width: 30px;
        }

        &__dot {
            margin-bottom: 0px;

            button {
                background-color: transparent;
                border: 1px solid #fff;
                margin-top: 10px;

                &:hover {
                    background-color: #fff;
                }
            }

            &--current {
                button {
                    background-color: #fff;
                }
            }
        }
    }

    .slide {
        background: {
            position: center;
            size: cover;
        }
        height: 990px;

        &--1 {
            background-image: url(../../../images/index_1.jpg);
        }
        &--2 {
            background-image: url(../../../images/index_2.jpg);
        }
        &--3 {
            background-image: url(../../../images/index_3.jpg);
        }
    }

</style>
<script>
    export default{
        data () {
            return {msg: 'Hi RESult'}
        }
    }
</script>