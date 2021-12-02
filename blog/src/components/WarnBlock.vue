<template>
  <div class="info_block">
    <div>
      <span class="el-icon-loading" style="color: mediumseagreen"></span>
      <span style="color: mediumseagreen; font-size: 130%; font-weight: bold"
        >友情提示</span
      >
      <span class="el-icon-loading" style="color: mediumseagreen"></span>
      <article style="margin-top: 10px">
        wangblog为个人博客，其中登录注册功能不受限制。如果想要发布博客或添加友链，可以联系作者申请权限
        <el-divider></el-divider>
        如果浏览时发现bug，欢迎在Github上提交issue给我。
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