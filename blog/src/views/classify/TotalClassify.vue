<template>
  <div id="classify-block">
    <div class="single-block">
      <div
        align="center"
        style="
          font-size: 270%;
          font-family: '楷体';
          font-weight: bold;
          width: 60%;
          margin-top: 25px;
        "
      >
        #分类
        <el-image :src="url" :fit="fit" style="margin-top:30px"></el-image>
      </div>
      <div class="block">
        <el-timeline style="margin-top: 25px">
          <el-timeline-item
            v-for="(category, index) in categoryList"
            :key="index"
            :hollow="true"
            @click="pushClassify(category)"
          >
            <el-button type="success" circle>{{ category }}</el-button>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TotalClassify",
  data() {
    return {
      categoryList: [],
      url: require("@/assets/background/5.jpg"),
      fit: "cover",
    };
  },
  created() {
    this.$store.commit("newStatus", 1);
  },
  mounted() {
    this.load();
  },
  methods: {
    pushClassify(content) {
      this.$router.push({
        name: "PartClassify",
        params: {
          category: content,
        },
      });
    },
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectAllCategory")
        .then((res) => {
          this.categoryList = res;
        });
    },
  },
};
</script>

<style scoped>
#classify-block {
  margin: 0 auto;
}
.single-block {
  padding: 20px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
  display: flex;
}
.block {
  width: 40%;
  margin-left: 30px;
}
</style>