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
      </el-tag>
    </div>
  </div>
</template>

<script>
export default {
  name: "TotalTag",
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
        { name: "Hibernate", type: "info", size: "small", effect: "plain" },
        { name: "SpringCloud", type: "warning", size: "medium", effect: "light" },
        { name: "Linux", type: "danger", size: "small", effect: "plain" },
        { name: "JavaScript", type: "warning", size: "mini", effect: "light" },
        { name: "Vue", type: "danger", size: "medium", effect: "dark" },
        { name: "Shiro", type: "success", size: "mini", effect: "light" },
        { name: "Nginx", type: "info", size: "small", effect: "plain" },
        { name: "Zookeeper", type: "success", size: "mini", effect: "dark" },
        { name: "CSS", type: "danger", size: "small", effect: "plain" },
        { name: "Html", type: "warning", size: "mini", effect: "light" },
        { name: "React", type: "danger", size: "medium", effect: "dark" },
        { name: "ElementPlus", type: "success", size: "mini", effect: "light" },
        { name: ".net", type: "success", size: "mini", effect: "light" },
        { name: "Ajax", type: "info", size: "small", effect: "plain" },
        { name: "Promise", type: "success", size: "mini", effect: "light" },
        { name: "Axios", type: "info", size: "mini", effect: "light" },
        { name: "Netty", type: "info", size: "small", effect: "plain" },
        { name: "Kafka", type: "", size: "medium", effect: "dark" },
        { name: "RabbitMQ", type: "success", size: "mini", effect: "light" },
        { name: "C++", type: "danger", size: "medium", effect: "dark" },
        { name: "Linux", type: "success", size: "mini", effect: "light" },
        { name: "C", type: "danger", size: "small", effect: "plain" },
        { name: "Golang", type: "warning", size: "medium", effect: "dark" },
        { name: "Python", type: "info", size: "small", effect: "plain" },
        { name: "C#", type: "info", size: "small", effect: "plain" },
        { name: "MySQL", type: "success", size: "mini", effect: "dark" },
        { name: "Redis", type: "danger", size: "medium", effect: "dark" },
      ],
    };
  },
  created(){
    this.$store.commit("newStatus", 1);
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
  margin: 20px;
}
</style>