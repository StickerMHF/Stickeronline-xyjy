<template>
  <a-modal
    :title="title"
    :width="1100"
    placement="right"
    v-model="visible"
    @ok="handleOk"
  >
    <a-form-model
      ref="addForm"
      :model="form"
      :rules="rules"
      :label-col="labelCol"
      :wrapper-col="wrapperCol"
    >
      <a-form-model-item ref="title" label="活动主题" prop="title">
        <a-input v-model="form.title" placeholder="请输入活动主题" />
      </a-form-model-item>
      <a-form-model-item ref="startTime" label="开始时间" prop="startTime">
      <a-range-picker :default-value="[moment(form.startTime, dateFormat), moment(form.endTime, dateFormat)]"  @change="dateChange"/>
      </a-form-model-item>

       <a-form-model-item ref="deadline" label="截止日期" prop="deadline">
         <a-date-picker style="width: 100%;" :value="form.deadline"  valueFormat='YYYY-MM-DD' @change="deadlineChange" placeholder="请输入报名截止日期"/>
      </a-form-model-item>
       <a-form-model-item ref="address" label="活动地址" prop="address">
        <a-input v-model="form.address" placeholder="请输入活动地址" />
      </a-form-model-item>
      <a-form-model-item ref="img" label="缩略图">
        <a-upload
          :action="UpFileUrl"
          list-type="picture-card"
          :file-list="fileList"
          @preview="handlePreview"
          @change="handleChange"
        >
          <div v-if="fileList.length < 8">
            <a-icon type="plus" />
            <div class="ant-upload-text">
              Upload
            </div>
          </div>
        </a-upload>
        <a-modal
          :visible="previewVisible"
          :footer="null"
          @cancel="handleCancel2"
        >
          <img alt="example" style="width: 100%" :src="previewImage" />
        </a-modal>
        {{ commend }}
      </a-form-model-item>
      <a-form-model-item label="内容" prop="context">
        <wangEditor
          v-model="form.context"
          :isClear="true"
          @change="change"
        ></wangEditor>
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import { getAction, postAction, deleteAction, putAction } from "@/api/manage";
import { mapActions, mapGetters } from "vuex";
import wangEditor from "@/components/sticker/wangEditor/index"; //验证码
import { axios, getFileUrl } from "@/utils/request";
import moment from 'moment';
export default {
  components: {
    wangEditor,
  },
  data() {
    return {
      title: "",
      dateFormat: 'YYYY-MM-DD',
      visible: false,
      UpFileUrl: getFileUrl(),
      uploadLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: "",
      commend: "注：最多展示1张图片",
      labelCol: { span: 2 },
      wrapperCol: { span: 20 },
      form: {
        context: "",
        title:"",
        img:"",
        fid:"",
        startTime:null,
        endTime:null,
        address:"",
        deadline:""
      },
      rules: {
        title: [{ required: true, message: "请输入活动主题", trigger: "blur" }],
         startTime: [{ required: true, message: "请输入活动开始时间", trigger: "blur" }],
           address: [{ required: true, message: "请输入活动地址", trigger: "blur" }],
            deadline: [{ required: true, message: "请输入报名截止日期", trigger: "blur" }],
        context: [
          { required: true, message: "请输入活动内容", trigger: "blur" },
        ],
      },
    };
  },
  computed: {},

  methods: {
    ...mapGetters(["nickname", "avatar", "userInfo"]),
    moment,
    dateChange(date, dateString){
      this.form.startTime=dateString[0];
      this.form.endTime=dateString[1];
    },
    add() {
      this.visible = true;
      this.form = {
         context: "",
        title:"",
        img:"",
        fid:"",
        startTime:moment(new Date(), this.dateFormat),
        endTime:moment(new Date(), this.dateFormat),
        address:"",
        deadline:""
      };
    },
    edit(record) {
        let that=this;
      this.visible = true;
      debugger;
      this.form = record;
      that.fileList=[];
      that.fileList.push({
          uid: Math.random(),
          name: "image.png",
          status: "done",
          url: this.form.img,
        });
      // this.fileList=this.form.thumb;
    },
       deadlineChange(data,value){
          this.form.deadline=data
      },
    handleOk() {
      let that = this;
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          that.form.updateBy = that.nickname();debugger
           that.form.startTime=moment(that.form.startTime, that.dateFormat).format('YYYY-MM-DD HH:mm:ss');
           that.form.endTime=moment(that.form.endTime, that.dateFormat).format('YYYY-MM-DD HH:mm:ss');
           that.form.deadline=moment(that.form.deadline, that.dateFormat).format('YYYY-MM-DD HH:mm:ss');
          if (that.form.id) {
            that.form.type=1;
            putAction("stickeronline/alumnusActivity/edit", that.form).then((res) => {
              if (res.success) {
                that.$message.success(res.result);
                that.$emit("close");
                that.form = {};
                that.visible = false;
              } else {
                that.$message.warning(res.result);
              }
            });
          } else {
            that.form.type = 1;
            that.form.createBy = that.nickname();
            postAction("stickeronline/alumnusActivity/add", that.form).then((res) => {
              if (res.success) {
                that.$message.success(res.result);
                that.$emit("close");
                that.form = {};
                that.visible = false;
              } else {
                that.$message.warning(res.result);
              }
            });
          }
        }
      });
    },
    //富文本编辑框
    change(value) {
      this.form.contents = value;
    },
    //图片上传回调
    handleChange({ fileList }) {
      this.form.img = "";
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i].response) {
          // this.form.thumb.push(fileList[i].response.result[0].url);
          this.form.img=fileList[i].response.result[0].url;
        }
      }
      this.fileList = fileList;
    },
    //图片预览
    async handlePreview(file) {
      debugger;
      this.previewImage = file.url || file.response.result[0].url;
      this.previewVisible = true;
    },
    handleCancel2() {
      this.previewVisible = false;
    },
  },

  watch: {},

  mounted() {},

  created() {},
};
</script>
<style lang="scss" scoped></style>
