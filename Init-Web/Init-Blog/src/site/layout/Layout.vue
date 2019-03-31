<template>
    <div>
        <Header></Header>
        <div class="mainMe">
            <Main></Main>
        </div>
    </div>
</template>


<script>
import Header from './components/Header'
import Main from './components/Main'

export default {
    name: "Layout",
    components: {
        Header,
        Main
    }
}
</script>

<style scoped>
    .mainMe{
        width: 100%; 
        float:left; 
        margin-top: 40px
    }
</style>

