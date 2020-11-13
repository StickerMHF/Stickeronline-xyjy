<template>
  <view>
    <cu-custom bgColor="bg-gradual-green1" :isBack="true">
      <block slot="backText">返回</block>
      <block slot="content">{{ title }}</block>
    </cu-custom>
    <bjx-form
      labelType="inline"
      :rules="rules"
      labelWidth="150"
      :form="form"
      ref="form"
    >
      <view class="cu-form-group">
        <bjx-form-item class="basicinfo_item" label="姓名" prop="name">
          <input
            class="basicinfo_name input"
            v-model="form.name"
            name="input"
            placeholder="姓名"
          />
        </bjx-form-item>
      </view>
      <!-- <view class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="性别" prop="sex">
					<input v-model="form.sex" class="input" name="input" placeholder="性别" />
				</bjx-form-item>
			</view> -->
      <view class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="性别"
          label-right="right"
          prop="sex"
        >
          <picker @change="sexChange" :value="form.sex" :range="sex">
            <view class="picker">
              {{ form.sex }}
            </view>
          </picker>
        </bjx-form-item>
      </view>
      <view class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="身份证"
          label-right="left"
          prop="identityCard"
        >
          <input
            v-model="form.identityCard"
            class="input"
            name="input"
            placeholder="身份证号"
          />
        </bjx-form-item>
      </view>
      <view class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="所属学院"
          label-right="left"
          prop="college"
        >
          <input
            v-model="form.college"
            class="input"
            name="input"
            placeholder="所属学院"
            disabled="true"
          />
        </bjx-form-item>
      </view>
      <!-- 不是教师 -->
      <!-- <view v-if="type!='3'" class="cu-form-group">
				<bjx-form-item class="basicinfo_item" label="所在专业" label-right="right" prop='profession'>
					<input v-model="form.profession" class="input" name="input" placeholder="所在专业" />
				</bjx-form-item>
			</view> -->
      <view class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="所在专业"
          label-right="right"
          prop="profession"
        >
          <picker
            @change="professionChange"
            :value="form.profession"
            :range="profession"
          >
            <view class="picker">
              {{ form.profession }}
            </view>
          </picker>
        </bjx-form-item>
      </view>

      <view class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="学历"
          label-right="right"
          prop="education"
        >
          <picker
            @change="eduChange"
            :value="form.education"
            :range="eduPicker"
          >
            <view class="picker">
              {{ form.education }}
            </view>
          </picker>
        </bjx-form-item>
      </view>
      <!-- 曾经在校 -->
      <view v-if="type == '1'" class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="入校时间"
          label-right="right"
          prop="startDate"
        >
          <picker
            mode="date"
            fields="year"
            :value="form.startDate"
            start="1970-09-01"
            end="2030-09-01"
            @change="startDateChange"
          >
            <view class="picker">
              {{ form.startDate }}
            </view>
          </picker>
        </bjx-form-item>
      </view>
      <!-- 在校学生 -->
      <view v-else-if="type == '2'" class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="入学时间"
          label-right="right"
          prop="startDate"
        >
          <picker
            mode="date"
            fields="year"
            :value="form.startDate"
            start="1970-09-01"
            end="2030-09-01"
            @change="startDateChange"
          >
            <view class="picker">
              {{ form.startDate }}
            </view>
          </picker>
        </bjx-form-item>
      </view>
      <!-- 在职教师 -->
      <view v-else class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="入职时间"
          label-right="right"
          prop="startDate"
        >
          <picker
            mode="date"
            fields="year"
            :value="form.startDate"
            start="1970-09-01"
            end="2030-09-01"
            @change="startDateChange"
          >
            <view class="picker">
              {{ form.startDate }}
            </view>
          </picker>
        </bjx-form-item>
      </view>

      <!-- 曾经在校 -->
      <view v-if="type == '1'" class="cu-form-group">
        <bjx-form-item
          class="basicinfo_item"
          label="离校时间"
          label-right="right"
          prop="endtDate"
        >
          <picker
            mode="date"
            fields="year"
            :value="form.endDate"
            start="1970-09-01"
            end="2030-09-01"
            @change="endDateChange"
          >
            <view class="picker">
              {{ form.endDate }}
            </view>
          </picker>
        </bjx-form-item>
      </view>

      <button class="bg-gradual-green1 margin" type="primary" @tap="submit">
        提交
      </button>
    </bjx-form>
  </view>
</template>

<script>
import bjxForm from "@/components/bjx-form/bjx-form.vue";
import bjxFormItem from "@/components/bjx-form/bjx-form-item.vue";
import {
  addWechatUser,
  updateWechatUser,
  getWechatUserById,
} from "@/api/user.js";
export default {
  components: {
    bjxForm,
    bjxFormItem,
  },
  data() {
    return {
      type: "1",
      isEdit: false,
      form: {
        openid: "",
        name: "",
        sex: "男",
        identityCard: "",
        college: "地测学院",
        profession: "勘查技术与工程",
        education: "请选择",
        startDate: "2016",
        endDate: "2020",
      },
      index: -1,
      eduIndex: -1,
      sexIndex: -1,
      professionIndex: -1,
      picker: ["喵喵喵", "汪汪汪", "哼唧哼唧"],
      sex: ["男", "女"],
      eduPicker: ["大专", "本科", "硕士", "博士"],
      profession: [
        "勘查技术与工程",
        "地球物理学",
        "测绘工程",
        "地理信息科学",
        "遥感科学与技术",
        "安全工程",
        "地质工程",
      ],
      rules: {
        name: {
          required: true,
          rule: "type:string|length:~,20",
        },
        identityCard: {
          required: true,
          rule: "type:string|length:0,18",
        },
        college: {
          required: true,
          rule: "type:string|length:~,50",
        },
        education: {
          required: true,
          rule: "type:string|length:~,50",
        },
        startDate: {
          required: true,
          rule: "type:string|date",
        },
      },
    };
  },
  onLoad(options) {
    // 初始化页面数据
    this.title = options.title;
    this.type = options.type;
    this.isEdit = options.isEdit;
    if (!this.type) {
    }
    if (this.isEdit || this.isEdit == "true") {
      this.isEdit = true;
      this.getWechatUserInfo();
    }
  },
  methods: {
    submit() {
      let that = this;
      this.$refs.form.validate(val => {
        if (val) {
          this.save(that.form);
        }
      });
    },
    getWechatUserInfo() {
      let that = this;
      let openid = uni.getStorageSync("openid");
      if (openid && openid != "") {
        let param = {
          openid: openid,
        };
        getWechatUserById(param).then(data => {
          var [error, res] = data;
          if (res && res.data.success) {
            let ss = res.data.result;
            if (ss != null) {
              that.form = ss;
              that.type = that.form.type;
            } else {
            }
          }
        });
      } else {
        getApp().getUserInfo();
      }
    },
    formatTime(date) {
      return getApp().formatTime(date);
    },
    textareaInput(e) {
      this.contents = e.detail.value;
    },
    pickerChange(e) {
      this.index = e.detail.value;
    },
    eduChange(e) {
      this.eduIndex = e.detail.value;
      this.form.education = this.eduPicker[this.eduIndex];
    },
    sexChange(e) {
      this.sexIndex = e.detail.value;
      this.form.sex = this.sex[this.sexIndex];
    },
    professionChange(e) {
      this.professionIndex = e.detail.value;
      this.form.profession = this.profession[this.professionIndex];
    },
    startDateChange(e) {
      this.form.startDate = e.detail.value;
    },
    endDateChange(e) {
      this.form.endDate = e.detail.value;
    },
    inputContactChange(e) {
      this.contact = e.detail.value;
    },
    inputTitleChange(e) {
      this.name = e.detail.value;
    },
    save(formData) {
      let that = this;
      let userInfo = uni.getStorageSync("userInfo");
      let params = Object.assign(userInfo, formData);
      let openid = uni.getStorageSync("openid");
      if (openid && openid != "") {
        params.openid = openid;
        params.type = that.type;
        params.updateTime = that.formatTime(params.updateTime);
        params.createTime = that.formatTime(params.createTime);
        if (!this.isEdit) {
          this.addWechatUser(params);
        } else {
          this.updateWechatUser(params);
        }
      } else {
        getApp().getUserInfo();
      }
    },
    addWechatUser(params) {
      addWechatUser(params).then(data => {
        var [error, res] = data;
        if (res && res.data && res.data.success) {
          uni.navigateBack({
            delta: 2,
          });
        } else {
          uni.showModal({
            content: "保存失败，请稍后再试：" + JSON.stringify(res.data),
            showCancel: false,
          });
        }
      });
    },
    updateWechatUser(params) {
      updateWechatUser(params).then(data => {
        var [error, res] = data;
        if (res && res.data && res.data.success) {
          uni.navigateBack({
            delta: 2,
          });
        } else {
          uni.showModal({
            content: "保存失败，请稍后再试：" + JSON.stringify(res.data),
            showCancel: false,
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.basicinfo_item {
  width: 100% !important;
}

.basicinfo_name {
  display: inline-block;

  .renzheng {
    float: right;
  }
}
</style>
