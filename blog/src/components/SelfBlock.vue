<template>
  <div class="self_block">
    <div class="imgDiv">
      <el-avatar class="headImg" :src="user.header" @click="this.$router.push('/author')"></el-avatar>
    </div>
    <div class="headName">Gleam</div>
    <nav style="text-align: center">
      <ul>
        <li><el-button type="text" style="color:black;font-size:105%" size="medium" @click="this.$router.push('/result')">文章<br><br>{{myNum.articleNum}}</el-button></li>
        <li><el-button type="text" style="color:black;font-size:105%" size="medium" @click="this.$router.push('/classify')">分类<br><br>{{myNum.categoryNum}}</el-button></li>
        <li><el-button type="text" style="color:black;font-size:105%" size="medium" @click="this.$router.push('/tag')">标签<br><br>{{myNum.tagNum}}</el-button></li>
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
import request from "../utils/request";

export default {
  name: "SelfBlock",
  data() {
    return {
      user: {
        header: require("../assets/avatar/Rosetta.jpg"),
      },
      myNum: {},
    };
  },
  created(){
    request.get("http://" + window.server.ip + ":9090/article/selectMyNum").then(res =>{
      this.myNum.categoryNum = res.categoryNum
      this.myNum.tagNum = res.tagNum
      this.myNum.articleNum = res.articleNum
    })
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
  margin-top: 5px;
  width: 180px;
  text-align: center;
}
</style>