<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="4.jpg"
    />
    <Header position="fixed" title="Article" />
  </div>
  <div class="header">
    <h1 style="font-size: 400%; font-family: '楷体'">
      {{ this.article.title }}
    </h1>
    <div style="margin-top: 10px">
      <span class="el-icon-date">发表于{{ this.article.time1 }}</span>
      <el-divider direction="vertical"></el-divider>
      <span class="el-icon-refresh">最后更新于{{ this.article.time2 }}</span>
    </div>
    <div style="margin-top: 5px">
      <span class="el-icon-user">{{ this.article.author }}</span>
      <el-divider direction="vertical"></el-divider>
      <span class="el-icon-receiving">{{ this.article.category }}</span>
      <el-divider direction="vertical"></el-divider>
      <span class="el-icon-price-tag">{{ this.article.tag }}</span>
    </div>
  </div>
  <div>
    <div class="content">
      <span v-html="this.article.content2"></span>
    </div>
  </div>
  <Footer />
</template>

<script>
import Background from "../components/Background.vue";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import request from "../utils/request";

export default {
  name: "Article",
  props: ["id"],
  components: {
    Background,
    Header,
    Footer,
  },
  data() {
    return {
      article: {},
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectId", {
          params: {
            id: this.id,
          },
        })
        .then((res) => {
          this.article = res;
        });
    },
  },
};
</script>

<style scoped>
.header {
  height: 400px;
  margin: 0 auto;
  padding-top: 130px;
  text-align: center;
  color: white;
}
.content {
  width: 70%;
  padding: 30px;
  margin: 0 auto;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
  margin-bottom: 100px;
}
</style>