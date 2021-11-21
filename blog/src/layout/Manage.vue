<template>
  <Background
    style="position: fixed; z-index: -1; width: 100%; height: 100%"
    pic="2.jpg"
  />
  <Header position="fixed" title="Management" />
  <div style="display: flex">
    <div style="width: 200px">
      <el-menu
        default-active="1-1"
        @mouseover="this.isCollapse = false"
        @mouseleave="this.isCollapse = true"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        :collapse="isCollapse"
        style="margin-top: 45px"
      >
        <el-sub-menu index="1">
          <template #title>
            <i class="el-icon-s-data"></i>
            <span>后台管理</span>
          </template>
          <el-menu-item-group>
            <template #title>文章</template>
            <el-menu-item index="1-1" class="el-icon-reading" @click="this.$router.push('/manage/ofArticle')"> Article表</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group>
            <template #title>用户</template>
            <el-menu-item index="1-2" class="el-icon-user" @click="this.$router.push('/manage/ofUser')"> User表</el-menu-item>
            <el-menu-item index="1-3" class="el-icon-folder-opened" @click="this.$router.push('/manage/ofUserInfo')"> Userinfo表</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group>
            <template #title>动漫</template>
            <el-menu-item index="1-4" class="el-icon-headset" @click="this.$router.push('/manage/ofAnimation')"> Animation表</el-menu-item>
          </el-menu-item-group>
        </el-sub-menu>
        <el-menu-item index="2">
          <i class="el-icon-edit-outline"></i>
          <template #title>文章编辑</template>
        </el-menu-item>
        <el-menu-item index="4" @click="this.$router.push('/')">
          <i class="el-icon-setting"></i>
          <template #title>安全退出</template>
        </el-menu-item>
      </el-menu>
    </div>
    <div style="margin-top: 45px"><router-view /></div>
  </div>
</template>

<script>
import Background from "@/components/Background.vue";
import Header from "@/components/Header";

export default {
  name: "Manage",
  components: {
    Background,
    Header,
  },
  data() {
    return {
      isCollapse: true,
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  /* min-height: 400px; */
  height: calc(100vh - 45px);
  opacity: 0.9;
}
.el-menu--collapse {
  height: calc(100vh - 45px);
  opacity: 0.9;
}
</style>