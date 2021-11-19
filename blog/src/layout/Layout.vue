<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="2.jpg"
    />
    <!-- <Topvideo /> -->
    <Header position="fixed" title="lw的学习笔记"/>
  </div>
  <About name="首 页"/>
  <div style="display: flex;width:70%;margin:0 auto">
    <div style="width:70%">
    <router-view />
    </div>
    <div style="width:30%">
    <div style="margin:0 auto"><SelfBlock /><WarnBlock /><InfoBlock /><TagBlock /></div>
    </div>
  </div>
</template>

<script>
import Topvideo from "@/components/Topvideo";
import Header from "@/components/Header";
import About from "@/components/About";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import WarnBlock from "@/components/WarnBlock";
import TagBlock from "@/components/TagBlock";
import Background from "../components/Background.vue";

export default {
  name: "Layout",
  components: {
    Topvideo,
    Header,
    About,
    SelfBlock,
    InfoBlock,
    WarnBlock,
    TagBlock,
    Background,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
</style>