<template>
  <div class="info_block">
    <div
      align="center"
      style="
        font-size: 200%;
        font-family: '楷体';
        font-weight: bold;
        margin-bottom: 10px;
      "
    >
      #标签
    </div>
    <el-tag
      v-for="tag in tags"
      :key="tag.tagName"
      :type="tag.type"
      :size="tag.size"
      :effect="tag.effect"
      class="tag"
      @click="pushTag(tag.tagName)"
    >
      {{ tag.tagName }} </el-tag
    >&nbsp;...
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TagBlock",
  data() {
    return {
      tags: [],
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    pushTag(name) {
      this.$router.push({
        name: "PartTag",
        params: {
          tag: name,
        },
      });
    },
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectAllTag")
        .then((res) => {
          this.tags = res;
        });
    },
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
.tag {
  margin: 7px;
}
</style>