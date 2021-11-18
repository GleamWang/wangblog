<template>
    <div class="about">
        <div class="content">
            {{this.name}}
        </div>
    </div>
  
</template>

<script>
export default {
    name:'About',
    props:{
        name:String
    }
}
</script>

<style scoped>
.about{
    margin:0 auto;
    height:280px;
    text-align: center;
    line-height: 280px;
}
.content {
    color: white;
    font-size: 300%;
    font-family:"楷体";
    font-weight: bold;
}
</style>