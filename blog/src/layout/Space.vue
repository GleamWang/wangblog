<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="2.jpg"
    />
    <Header position="fixed" title="Myspace" />
    <About name="个人空间" />
  </div>
  <div style="display: flex; width: 70%; margin: 0 auto">
    <div style="width: 30%" align="center">
      <el-affix :offset="44">
        <div class="left-block">
        <el-button type="text" size="medium" style="font-size:105%;color:black;margin-top:10px" @click="this.$router.push('/space/profile')">个人资料</el-button>
        <br>
        <el-button type="text" size="medium" style="font-size:105%;color:black" @click="this.$router.push('/space/account')">账号设置</el-button>
        <br>
        <el-button type="text" size="medium" style="font-size:105%;color:black" @click="this.$router.push('/space/privacy')" disabled>隐私设置</el-button>
        <br>
        <el-button type="text" size="medium" style="font-size:105%;color:black" @click="this.$router.push('/space/collection')" disabled>我的收藏</el-button>
        <br>
        <el-button type="text" size="medium" style="font-size:105%;color:black;margin-bottom:10px" @click="this.$router.push('/space/content')" disabled>内容管理</el-button>
        </div>
      </el-affix>
    </div>
    <div style="width: 70%">
      <div style="margin: 0 auto">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import Background from "../components/Background.vue";

export default {
  name: "Space",
  components: {
    Header,
    About,
    Background,
  },
};
</script>

<style scoped>
.left-block {
  margin-left: 20px;
  margin-top: 10px;
  width: 180px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  float: right;
}
</style>