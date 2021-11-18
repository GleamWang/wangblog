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
        :key="tag.name"
        :type="tag.type"
        :size="tag.size"
        :effect="tag.effect"
        class="tag"
        @click="pushTag(tag.name)"
      >
        {{ tag.name }}
      </el-tag>...
  </div>
</template>

<script>
export default {
  name: "TagBlock",
  data() {
    return {
      tags: [
        { name: "Springboot", type: "", size: "medium", effect: "dark" },
        { name: "JVM", type: "success", size: "mini", effect: "light" },
        { name: "J2EE", type: "info", size: "small", effect: "plain" },
        { name: "Jar", type: "success", size: "mini", effect: "light" },
        { name: "Spring", type: "warning", size: "medium", effect: "light" },
        { name: "Tomcat", type: "danger", size: "small", effect: "plain" },
        { name: "Springmvc", type: "warning", size: "medium", effect: "dark" },
        { name: "IDEA", type: "info", size: "mini", effect: "light" },
        { name: "MySQL", type: "info", size: "small", effect: "plain" },
        { name: "SpringCloud", type: "warning", size: "medium", effect: "light" },
        { name: "Linux", type: "danger", size: "small", effect: "plain" },
        { name: "JavaScript", type: "warning", size: "mini", effect: "light" },
        { name: "Vue", type: "danger", size: "medium", effect: "dark" },
        { name: "Shiro", type: "success", size: "mini", effect: "light" },
        { name: "Nginx", type: "info", size: "small", effect: "plain" },
        { name: "Zookeeper", type: "success", size: "mini", effect: "dark" },
        { name: "RabbitMQ", type: "info", size: "mini", effect: "dark" },
      ],
    };
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
  },
};
</script>

<style scoped>
.info_block {
  margin-top: 20px;
  margin-left: 20px;
  max-width: 300px;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 20px;
  float: left;
}
.tag {
  margin: 5px;
}
</style>