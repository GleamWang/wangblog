<template>
  <div class="self_block">
    <div class="imgDiv">
      <!-- <el-image class="headImg" :src="user.header" @click="this.$router.push('/author')"> </el-image> -->
      <el-avatar class="headImg" :src="user.header" @click="this.$router.push('/author')"></el-avatar>
    </div>
    <div class="headName">Gleam</div>
    <nav style="text-align: center">
      <ul>
        <li>文章</li>
        <li>标签</li>
        <li>分类</li>
      </ul>
    </nav>
    <nav style="text-align: center">
      <ul>
        <li>123</li>
        <li>2333</li>
        <li>999</li>
      </ul>
    </nav>
    <div align="center">
      <el-button type="primary" class="button" @click="githubClick"
        >Follow Me</el-button
      >
    </div>
    <nav style="text-align: center">
      <ul>
        <li>
          <img
            :src="require('../assets/icon/bilibili.png')"
            alt="bilibili"
            style="width: 20px; height: 20px"
            @click="bilibiliClick"
          />
        </li>
        <li>
          <img
            :src="require('../assets/icon/github.png')"
            alt="github"
            style="width: 20px; height: 20px"
            @click="githubClick"
          />
        </li>
        <li>
          <img
            :src="require('../assets/icon/QQ.png')"
            alt="QQ"
            style="width: 20px; height: 20px"
            @click="qqClick"
          />
        </li>
      </ul>
    </nav>
  </div>
</template>

<script>
export default {
  name: "SelfBlock",
  data() {
    return {
      user: {
        header: require("../assets/avatar/avatar.jpg"),
      },
    };
  },
  methods: {
    githubClick() {
      window.open("https://github.com/GleamWang", "_blank");
    },
    bilibiliClick() {
      window.open("https://space.bilibili.com/107982271", "_blank");
    },
    qqClick() {
      window.open(
        "https://wpa.qq.com/msgrd?v=3&uin=644823098&site=qq&menu=yes",
        "_blank"
      );
    },
  },
};
</script>

<style scoped>
.self_block {
  margin-top: 20px;
  margin-left: 20px;
  width: 300px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding-bottom: 15px;
  float: left;

}
.imgDiv {
  margin: 0 auto;
  display: flex;
}
.headImg {
  width: 100px;
  height: 100px;
  position: relative;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.headName {
  text-align: center;
  /* font-weight: bold; */
  font-size: 200%;
  margin-top: -50px;
}
ul {
  margin-top: 10px;
  list-style-type: none;
}
li {
  display: inline;
  margin: 0 15px;
}
.button {
  margin-top: 15px;
  width: 180px;
  text-align: center;
}
</style>