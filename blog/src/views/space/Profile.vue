<template>
  <div>
    <div class="single-block">
      <div style="display: flex">
        <div>
          <el-avatar
            :size="100"
            fit="contain"
            :src="this.userinfo.avatar"
            style=""
            @mouseover="mouseOver"
          ></el-avatar>

          <el-avatar
            :size="100"
            fit="scale-down"
            :src="require('@/assets/icon/camera.png')"
            style="position: absolute; opacity: 0.5; margin-left: -100px"
            v-show="avaStatus"
            @mouseleave="mouseLeave"
            @click="centerDialogVisible = true"
          ></el-avatar>
        </div>
        <div style="margin-left: 20px">
          <div style="font-size: 150%; font-weight: bold; margin-top: 10px">
            {{ this.userinfo.username }}
          </div>
          <div>
            <el-tag type="danger" effect="dark" style="margin-top: 20px">
              {{ this.userinfo.role }}
            </el-tag>
          </div>
        </div>
      </div>
    </div>
    <div class="single-block">
      <!-- 用户信息 -->
      <el-descriptions
        title="用户信息"
        :column="1"
        size="medium"
        v-if="infoStatus"
      >
        <template #extra>
          <el-button
            type="text"
            size="medium"
            class="el-icon-edit"
            @click="edit"
            >编辑</el-button
          >
        </template>

        <el-descriptions-item label="用户昵称">{{
          this.userinfo.username
        }}</el-descriptions-item>

        <el-descriptions-item label="用&nbsp;户&nbsp;&nbsp;ID">{{
          this.userinfo.userid
        }}</el-descriptions-item>

        <el-descriptions-item label="性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别">
          {{ this.userinfo.sex }}
        </el-descriptions-item>

        <el-descriptions-item label="个人简介">
          {{ this.userinfo.introduction }}
        </el-descriptions-item>

        <el-descriptions-item label="出生日期">
          {{ this.userinfo.birth }}
        </el-descriptions-item>

        <el-descriptions-item label="所在地区">{{
          this.userinfo.address
        }}</el-descriptions-item>
      </el-descriptions>
      <!-- 修改用户信息 -->
      <div v-else>
        <h4 style="margin-top: 8px">用户信息</h4>
        <div style="margin-top: 22px">
          <el-form ref="form" :model="form" label-width="15%">
            <el-form-item label="用户昵称">
              <el-input v-model="form.username" style="width: 85%"></el-input>
            </el-form-item>
            <el-form-item label="用&nbsp;户&nbsp;&nbsp;ID">
              <el-input
                v-model="form.userid"
                disabled
                style="width: 85%"
              ></el-input>
            </el-form-item>
            <el-form-item label="性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别">
              <el-radio-group v-model="form.sex">
                <el-radio label="男"></el-radio>
                <el-radio label="女"></el-radio>
                <el-radio label="未知"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="个人简介">
              <el-input
                v-model="form.introduction"
                type="textarea"
                style="width: 85%"
              ></el-input>
            </el-form-item>
            <el-form-item label="出生日期">
              <el-col :span="11">
                <el-date-picker
                  v-model="form.birth"
                  type="date"
                  placeholder="选择一个日期"
                  style="width: 100%"
                  format="YYYY 年 MM 月 DD 日"
                  value-format="YYYY年MM月DD日"
                ></el-date-picker>
              </el-col>
            </el-form-item>
            <el-form-item label="所在地区">
              <el-cascader
                placeholder="请点击选择地址"
                :options="options"
                v-model="form.address"
                @change="handleChange"
                style="width: 85%"
              ></el-cascader>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="onSubmit">确认更改</el-button>
              <el-button @click="cancel">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <!-- 教育信息 -->
    <!-- <div class="single-block">
      <el-descriptions title="教育信息" :column="1" size="medium">
        <template #extra>
          <el-button type="text" size="medium" class="el-icon-edit"
            >编辑</el-button
          >
        </template>
        <el-descriptions-item label="学校名称"></el-descriptions-item>

        <el-descriptions-item label="专业"></el-descriptions-item>

        <el-descriptions-item label="入学时间"></el-descriptions-item>

        <el-descriptions-item label="学历"></el-descriptions-item>
      </el-descriptions>
    </div> -->

    <!-- 头像上传框 -->
    <el-dialog title="上传头像" v-model="centerDialogVisible" width="30%">
      <el-upload
        :action="filesUploadUrl"
        :on-success="uploadSuccess"
        ref="upload"
        align="center"
      >
        <el-button size="medium" type="danger">点击上传</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png files with a size less than 500kb
          </div>
        </template>
      </el-upload>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Profile",
  data() {
    return {
      user: {
        token: "",
        userid: "",
      },
      userinfo: {},
      avaStatus: 0,
      infoStatus: 1,
      centerDialogVisible: false,
      filesUploadUrl: "http://" + window.server.ip + ":9090/userinfo/upload",
      form: {
        username: "",
        userid: "",
        sex: "",
        introduction: "",
        birth: "",
        address: [],
        strAddress: "",
      },
      options: [],
      cityArr: [], //城市列表
      areaArr: [], //区县列表
      province: "", //省
      city: "", //市
      area: "", // 区县,
      provinceCityArea: "", //选择器选择的省市区
    };
  },
  created() {
    this.$store.commit("newStatus", 0);
    this.user.token = JSON.parse(window.localStorage.getItem("access-token"));
    this.user.userid = JSON.parse(window.localStorage.getItem("access-userid"));
    if (this.user.token === null || this.user.userid === null) {
      this.$router.push("/login");
    } else {
      request
        .get("http://" + window.server.ip + ":9090/userinfo", {
          params: {
            userid: this.user.userid,
          },
        })
        .then((res) => {
          this.userinfo = res;
        });
    }
  },
  mounted() {
    this.initDistPicker();
  },
  methods: {
    mouseOver() {
      this.avaStatus = 1;
    },
    mouseLeave() {
      this.avaStatus = 0;
    },
    edit() {
      this.infoStatus = 0;
      this.form.username = this.userinfo.username;
      this.form.userid = this.userinfo.userid;
      this.form.sex = this.userinfo.sex;
      this.form.introduction = this.userinfo.introduction;
      this.form.birth = this.userinfo.birth;
      this.form.address = this.userinfo.address;
    },
    cancel() {
      this.infoStatus = 1;
    },
    uploadSuccess(response) {
      this.userinfo.avatar = response.data;
      request
        .put("http://" + window.server.ip + ":9090/userinfo", this.userinfo)
        .then((res) => {
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: " 上传成功",
            });
            this.$refs.upload.clearFiles();
            this.centerDialogVisible = false;
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
        });
    },
    onSubmit() {
      this.userinfo.username = this.form.username;
      this.userinfo.sex = this.form.sex;
      this.userinfo.introduction = this.form.introduction;
      this.userinfo.birth = this.form.birth;
      this.userinfo.address = this.form.strAddress;
      request
        .put("http://" + window.server.ip + ":9090/userinfo", this.userinfo)
        .then((res) => {
          console.log(res);
          if (res.code === "0") {
            this.$message({
              type: "success",
              message: "编辑成功",
            });
          } else {
            this.$message({
              type: "error",
              message: res.msg,
            });
          }
          this.infoStatus = 1;
        });
    },
    //选择地区
    handleChange(value) {
      let self = this;
      console.log("value=>", value);
      //获取省名
      console.log("value.length=>", value.length);
      if (value.length == 3) {
        self.options.map((item, index) => {
          if (item.value == value[0]) {
            self.cityArr = item.children; //存储城市列表
            self.province = item.label;
          }
        });
        //获取市名
        self.cityArr.map((item, index) => {
          if (item.value == value[1]) {
            self.areaArr = item.children; //存储区县列表
            self.city = item.label;
          }
        });
        //获取区县名
        self.areaArr.map((item, index) => {
          if (item.value == value[2]) {
            self.area = item.label;
          }
        });
      } else if (value.length == 2) {
        self.options.map((item, index) => {
          if (item.value == value[0]) {
            self.cityArr = item.children; //存储城市列表
            self.province = item.label;
          }
        });
        //获取市名
        self.cityArr.map((item, index) => {
          if (item.value == value[1]) {
            self.areaArr = item.children; //存储区县列表
            self.city = item.label;
          }
        });
        //获取区县名
        self.area = "";
      } else {
        self.options.map((item, index) => {
          if (item.value == value[0]) {
            self.cityArr = item.children; //存储城市列表
            self.province = item.label;
          }
        });
        //获取市名
        self.city = "";
        //获取区县名
        self.area = "";
      }

      self.provinceCityArea = self.province + self.city + self.area;
      console.log("address=>" + this.form.address);
      //将地区编号数组转化为地区名后赋值给form.address
      this.form.strAddress = self.provinceCityArea;
      console.log("strAddress=>" + this.form.strAddress);
    },
    initDistPicker() {
      let self = this;
      request.get("/static/districts.json").then(function (respones) {
        let distData = respones;
        let options = [];
        // 省
        for (var provinceKy in distData["100000"]) {
          let optProvinceItem = {
            value: provinceKy,
            label: distData["100000"][provinceKy],
          };
          if (distData[provinceKy]) {
            // 市
            for (var cityKy in distData[provinceKy]) {
              optProvinceItem.children = optProvinceItem.children
                ? optProvinceItem.children
                : [];
              let optCityItem = {
                value: cityKy,
                label: distData[provinceKy][cityKy],
              };
              if (distData[cityKy]) {
                // 区
                for (var areaKy in distData[cityKy]) {
                  optCityItem.children = optCityItem.children
                    ? optCityItem.children
                    : [];
                  let optAreaItem = {
                    value: areaKy,
                    label: distData[cityKy][areaKy],
                  };
                  optCityItem.children.push(optAreaItem);
                }
              }
              optProvinceItem.children.push(optCityItem);
            }
          }
          options.push(optProvinceItem);
        }
        self.distData = distData;
        self.options = options;
      });
    },
  },
};
</script>

<style scoped>
.single-block {
  margin-top: 10px;
  margin-left: 10px;
  width: 80%;
  box-shadow: border-box;
  background: rgba(255, 255, 255, 1);
  border-radius: 10px;
  padding: 20px;
  float: left;
}
</style>