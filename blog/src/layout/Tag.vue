<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="4.jpg"
    />
    <Header position="fixed" title="Tag" />
    <About name="标 签" />
  </div>
  <div style="width: 60%; margin: 0 auto">
    <div style="width: 100%">
      <router-view />
    </div>
  </div>
  <Footer />
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import Footer from "@/components/Footer";
import Background from "../components/Background.vue";

export default {
  name: "Tag",
  components: {
    Header,
    About,
    Footer,
    Background,
  },
};
</script>

<style>
</style>