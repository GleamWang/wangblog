<template>
  <Background
    style="position: fixed; z-index: -1; width: 100%; height: 100%"
    pic="2.jpg"
  />
  <Header position="absolute" title="Composition" />
  <About name="创 作 页" />
  <div id="toolbar-container" class="toolbar"></div>
  <div>
    <div>
      <el-form ref="form" :model="form">
        <el-form-item align="center">
          <el-input
            v-model="form.title"
            style="width: 70%"
            size="medium"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <div>
            <div
              id="text-container"
              class="text"
              style="width: 70%; margin: 0 auto; height: 700px"
              @input="change"
            ></div>
          </div>
        </el-form-item>

        <div class="creation_block">
          <el-form-item label="展示封面：">
            <el-radio-group v-model="resource">
              <el-radio :label="true">重新上传</el-radio>
              <el-radio :label="false">不更改原封面</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-upload
            class="upload-demo"
            :action="filesUploadUrl"
            ref="upload"
            :auto-upload="false"
            :file-list="fileList"
            list-type="picture-card"
            v-show="resource"
            :before-upload="beforeAvatarUpload"
            :on-change="handleChange"
            :on-success="filesUploadSuccess"
            style="margin-bottom: 10px; margin-top: -20px; margin-left: 80px"
          >
            <i class="el-icon-plus"></i>
          </el-upload>

          <el-form-item label="文章标签：">
            <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              :disable-transitions="false"
              :hit="true"
              effect="dark"
              size="medium"
              @close="handleClose(tag)"
              class="outer"
            >
              {{ tag }}
            </el-tag>

            <el-popover placement="bottom" :width="400" trigger="click">
              <template #reference>
                <el-button class="button-new-tag" size="small"
                  >+ 重新添加标签(若不想更改则无需选择)</el-button
                >
              </template>

              <h2 align="center">标签</h2>

              <hr style="margin-top: 10px; margin-bottom: 10px" />
              <el-tabs tab-position="left" style="height: 200px">
                <el-tab-pane
                  v-for="tagItem in tagList"
                  :key="tagItem.name"
                  :label="tagItem.name"
                >
                  <h4>添加标签</h4>
                  <el-tag
                    v-for="tag in tagItem.group"
                    :key="tag.name"
                    :type="tag.type"
                    @click="addTag(tag.name)"
                    v-model="tag.name"
                  >
                    {{ tag.name }}
                  </el-tag>
                </el-tab-pane>
              </el-tabs>

              <div :style="`float:right;display:${state1}`">
                还可添加{{ tagNum }}个标签
              </div>
              <div :style="`float:right;display:${state2};color:red`">
                最多只可添加{{ this.dynamicTags.length }}个标签
              </div>
            </el-popover>
          </el-form-item>

          <el-form-item label="分类专栏：">
            <el-input
              v-model="form.category"
              maxlength="15"
              placeholder="请输入内容"
              show-word-limit
              type="text"
              style="width: 200px"
            >
            </el-input>
          </el-form-item>

          <el-form-item label="文章类型：">
            <el-switch
              v-model="form.original"
              active-text="原创"
              inactive-text="转载"
            >
            </el-switch>
          </el-form-item>
          <el-button
            type="danger"
            round
            style="float: right"
            size="large"
            @click="submit"
            >重新发布</el-button
          >
        </div>
      </el-form>
    </div>

    <div class="footer" style="height: 500px"></div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import About from "@/components/About";
import Background from "../components/Background.vue";
import E from "wangeditor";
import request from "../utils/request";

let editor;

export default {
  name: "Modify",
  props: ["id"],
  components: {
    Header,
    About,
    Background,
  },
  data() {
    return {
      form: {},
      resource: false,
      dialogImageUrl: "",
      fileList: [],
      dynamicTags: [],
      state1: "",
      state2: "",
      value: true,
      filesUploadUrl: "http://" + window.server.ip + ":9090/article/upload",
      tagList: {},
    };
  },
  created() {
    this.$store.commit("newStatus", 0);
    this.load();
  },
  mounted() {
    this.init();
    this.initTags();
    this.initUsername();
  },
  computed: {
    tagNum: {
      get() {
        let res = 5 - this.dynamicTags.length;
        if (res > 0) {
          this.state1 = "";
          this.state2 = "none";
          return 5 - this.dynamicTags.length;
        } else {
          this.state1 = "none";
          this.state2 = "";
        }
      },
    },
  },
  methods: {
    //初始化富文本编辑器
    init() {
      editor = new E("#toolbar-container", "#text-container");
      editor.config.focus = false;
      editor.config.height = 500;
      editor.create();
      this.$watch("value", () => {
        editor.txt.html(this.value);
      });
    },
    change(e) {
      // v-model 指令是会给此节点添加一个input事件，我们在给富文本编辑器添加一个input事件，这么做在于，当输入内容后会获取到最新的内容，通过$emit 触发 父组件v-model所添加的input事件并将最新的内容传递过去，传递过去的值默认就会赋给v-model所绑定的属性。
      this.$emit("input", e.target.innerHTML);
    },
    //初始化tags的json文件
    initTags() {
      request.get("/static/tags.json").then((res) => {
        this.tagList = res;
      });
    },
    //通过userid获取username并赋值
    initUsername() {
      let userid = JSON.parse(window.localStorage.getItem("access-userid"));
      request
        .get("http://" + window.server.ip + ":9090/userinfo/selectUsername", {
          params: { userid: userid },
        })
        .then((res) => {
          //初始化文章的作者
          this.form.author = res.username;
        });
    },
    load() {
      request
        .get("http://" + window.server.ip + ":9090/article/selectId", {
          params: {
            id: this.id,
          },
        })
        .then((res) => {
          this.form = res;
          this.value = this.form.content2;
        });
    },
    beforeAvatarUpload(file, fileList) {
      if (file.size / (1024 * 1024) > 5) {
        // 限制文件大小
        this.$message.warning(`当前限制文件大小不能大于5M`);
        return false;
      }
    },
    handleChange(file, fileList) {
      //只显示最后一个上传的文件
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]];
      }
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    addTag(tagName) {
      if (tagName) {
        if (this.dynamicTags.length < 5)
          if (this.dynamicTags.indexOf(tagName) == -1)
            this.dynamicTags.push(tagName);
          else return false;
        else return false;
      }
    },
    submit() {
      if (this.resource == false) {
        this.form.content1 = editor.txt.text();
        this.form.content2 = editor.txt.html();
        if(this.dynamicTags.length == 0){
            //保持原有标签
        }
        else{
            this.form.tag = this.dynamicTags.toString();
        }
        
        request
          .put("http://" + window.server.ip + ":9090/article", this.form)
          .then((res) => {
            if (res.code === "0") {
              this.$message({
                type: "success",
                message: "发布成功",
              });
              this.form = {};
              editor.txt.clear();
              this.dynamicTags = [];
              this.fileList = [];
            } else {
              this.$message({
                type: "error",
                message: res.msg,
              });
            }
          });
      } else {
        this.$refs.upload.submit();
      }
    },
    filesUploadSuccess(res) {
      this.form.img = res.data;
      this.form.content1 = editor.txt.text();
      this.form.content2 = editor.txt.html();
      this.form.tag = this.dynamicTags.toString();
      this.form.author = "Gleam";
      request
        .post("http://" + window.server.ip + ":9090/article", this.form)
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "新增成功",
            });
            this.form = {};
            editor.txt.clear();
            this.dynamicTags = [];
            this.fileList = [];
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
  },
};
</script>

<style scoped>
.main {
  background: rgb(232, 232, 232);
}
.creation_block {
  width: 70%;
  height: auto;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 50px;
  margin: 0 auto;
  padding-left: 70px;
  padding-bottom: 75px;
}
.el-tag {
  margin-left: 5px;
  margin-right: 5px;
  margin-top: 10px;
}
.outer {
  margin-top: 0;
}
.button-new-tag {
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
.upload-demo {
  display: flex;
}
.toolbar {
  z-index: 999;
  position: absolute;
  top: 45px;
  width: 100%;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
}
.text {
  z-index: 1000;
}
</style>