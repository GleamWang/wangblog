<template>
  <div id="classify-block">
    <div class="single-block">
      <div
        align="center"
        style="
          font-size: 270%;
          font-family: '楷体';
          font-weight: bold;
          margin-bottom: 10px;
        "
      >
        #标签-
      </div>
      <div style="width: 80%; margin: 0 auto">
        <el-tag
          v-for="tag in tags"
          :key="tag.tagName"
          :type="tag.type"
          :size="tag.size"
          :effect="tag.effect"
          class="tag"
          @click="pushTag(tag.tagName)"
        >
          {{ tag.tagName }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "TotalTag",
  data() {
    return {
      tags: [],
    };
  },
  created() {
    this.$store.commit("newStatus", 1);
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
#classify-block {
  margin: 0 auto;
}
.single-block {
  padding: 20px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
.tag {
  margin: 15px;
}
</style>