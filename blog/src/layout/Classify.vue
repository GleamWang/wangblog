<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="3.jpg"
    />
    <Header position="fixed" title="Classificatin" />
    <About name="分 类" />
  </div>
  <div style="display: flex; width: 70%; margin: 0 auto">
    <div style="width: 60%">
      <router-view />
    </div>
    <div style="width: 40%">
      <div style="margin: 0 auto"><SelfBlock /><WarnBlock /><InfoBlock /><TagBlock /></div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import WarnBlock from "@/components/WarnBlock";
import TagBlock from "@/components/TagBlock";
import Background from "../components/Background.vue";

export default {
  name: "Classify",
  components: {
    Header,
    About,
    SelfBlock,
    InfoBlock,
    WarnBlock,
    TagBlock,
    Background,
  },
};
</script>

<style>
</style>