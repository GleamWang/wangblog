<template>
  <div>
    <Background
      style="position: fixed; z-index: -1; width: 100%; height: 100%"
      pic="4.jpg"
    />
    <Header position="fixed" title="Developer" />
    <About name="关于作者" />
  </div>
  <div style="display: flex; width: 70%; margin: 0 auto">
    <div id="classify-block">
      <div class="single-block">
        <h2>#个人简介</h2>
        <div class="demo-collapse">
          <el-collapse accordion style="margin-top: 15px">
            <el-collapse-item name="1">
              <template #title>
                昵称&nbsp;&nbsp;<i class="el-icon-edit-outline"></i>
              </template>
              <img src="../assets/icon/aojiao.png" alt="" style="width: 15px" />
              &nbsp;&nbsp;Gleam
            </el-collapse-item>
            <el-collapse-item name="2">
              <template #title>
                性别&nbsp;&nbsp;<i class="el-icon-female"></i>
              </template>
              <div class="el-icon-male">男</div>
            </el-collapse-item>
            <el-collapse-item name="3">
              <template #title>
                爱好&nbsp;&nbsp;<i class="el-icon-cpu"></i>
              </template>
              <div class="el-icon-cpu">电脑硬件</div>
              <br />
              <div class="el-icon-monitor">主机游戏</div>
              <br />
              <div class="el-icon-copy-document">编程</div>
            </el-collapse-item>
            <el-collapse-item name="4">
              <template #title>
                专业&nbsp;&nbsp;<i class="el-icon-guide"></i>
              </template>
              <div class="el-icon-connection">软件工程</div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>

      <div class="single-block">
        <h2>#开发计划</h2>
        <el-card class="box-card" style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>实现功能</span>
              <el-button class="el-icon-edit" type="text" style="float: right"
                >编辑</el-button
              >
            </div>
          </template>
          <div class="text item" style="margin-bottom: 10px;text-decoration:line-through">
            个人中心头像上传功能
          </div>
          <div class="text item" style="margin-bottom: 10px;text-decoration:line-through">
            完善用户信息数据库
          </div>
          <div class="text item" style="margin-bottom: 10px">
            实现个人中心菜单功能
          </div>
          <div class="text item" style="margin-bottom: 10px">
            完善主分类页面
          </div>
          <div class="text item" style="margin-bottom: 10px">完善友链页面</div>
          <div class="text item" style="margin-bottom: 10px">增加评论功能</div>
          <div class="text item" style="margin-bottom: 10px">
            实现访问量等数据的可视化
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 20px">
          <template #header>
            <div class="card-header">
              <span>Bug修复</span>
              <el-button class="el-icon-edit" type="text" style="float: right"
                >编辑</el-button
              >
            </div>
          </template>
          <div class="text item" style="margin-bottom: 10px;text-decoration:line-through">
            创作页面上传图片会直接提交到服务器
          </div>
          <div class="text item" style="margin-bottom: 10px;text-decoration:line-through">
            创作页面上传器无法在挂载前读取全局配置
          </div>
          <div class="text item" style="margin-bottom: 10px;text-decoration:line-through">
            全局变量会在刷新后失效
          </div>
          <div class="text item" style="margin-bottom: 10px">
            右侧信息块float:left不生效
          </div>
        </el-card>
      </div>
    </div>

    <div style="width: 40%">
      <div style="margin: 0 auto"><SelfBlock /><InfoBlock /><TagBlock /></div>
    </div>
  </div>
</template>

<script>
import Topvideo from "@/components/Topvideo";
import Header from "@/components/Header";
import About from "@/components/About";
import TagBlock from "@/components/TagBlock";
import SelfBlock from "@/components/SelfBlock";
import InfoBlock from "@/components/InfoBlock";
import Background from "../components/Background.vue";

export default {
  name: "Gleam",
  components: {
    Topvideo,
    Header,
    About,
    TagBlock,
    SelfBlock,
    InfoBlock,
    Background,
  },
};
</script>

<style scoped>
#classify-block {
  margin: 0 auto;
  width: 60%;
}
.single-block {
  padding: 40px;
  margin: 20px 0;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 15px;
}
article {
  margin-top: 20px;
}
</style>