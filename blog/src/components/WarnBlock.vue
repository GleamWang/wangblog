<template>
  <div class="info_block">
    <div>
      <span class="el-icon-loading" style="color: orange"></span>
      <span style="color: orange; font-size: 130%; font-weight: bold"
        >重要通知</span
      >
      <span class="el-icon-loading" style="color: orange"></span>
      <article style="margin-top: 10px">
        由于项目还未完成，以及可能存在一些bug，部分功能会被禁用，请谅解。 _(:з」∠)_
        <br />
        具体开发进度以及更新时间可前往顶部菜单<span
          class="el-icon-info"
          style="font-weight: bold"
          >关于作者</span
        >模块查看。
        <el-divider></el-divider>
        以下为测试登录账号
        <br />
        un:test&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;pw:123
      </article>
    </div>
  </div>
</template>

<script>
import Space from "../layout/Space.vue";
export default {
  components: { Space },
  name: "InfoBlock",
  data() {
    return {};
  },
};
</script>

<style scoped>
.info_block {
  margin-top: 20px;
  margin-left: 20px;
  width: 300px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 20px;
  float: left;
}
</style>