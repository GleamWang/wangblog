<template>
  <div style="margin-top: 80px; margin-bottom: 80px" align="center">
    <div style="display: flex; align-items: center; justify-content: center">
      <span style="color: white; font-size: 90%">Copyright</span>

      <el-image
        style="width: 20px; height: 20px"
        :src="require('../assets/icon/copyright.png')"
        alt="copyright"
      >
      </el-image>
      <span style="color: white; font-size: 90%"
        >2021-2022&nbsp;&nbsp;&nbsp;Gleam</span
      >
    </div>
    <div
      style="
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 5px;
      "
    >
      <el-image
        style="width: 20px; height: 20px"
        :src="require('../assets/icon/icp1.png')"
        alt="copyright"
      >
      </el-image>
      <el-link
        href="https://beian.miit.gov.cn"
        target="_blank"
        :style="switchStyle"
        :underline="false"
        @mouseover="this.switchStyle = this.style2"
        @mouseleave="this.switchStyle = this.style1"
        >&nbsp;备案号：苏ICP备2021051960号-1</el-link
      >
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer",
  data() {
    return {
      style1: "color: white",
      style2: "color: white;text-decoration:underline",
      switchStyle: "",
    };
  },
  created() {
    this.switchStyle = this.style1;
  },
};
</script>

<style>
</style>