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
      <a-form-model-item ref="content" label="内容" prop="content">
        <a-input v-model="form.content" placeholder="姓名" />
     </a-form-model-item>
     
      <a-form-model-item ref="img" label="照片">
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
              上传
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
      <a-form-model-item ref="status" label="审核状态" prop="status" >
                <a-select style="width: 100%;" :value="form.status" @change="statusChange">
                    <a-select-option :value="0">待审核</a-select-option>
                    <a-select-option :value="1">审核通过</a-select-option>
                    <a-select-option :value="-1">审核未通过</a-select-option>
                </a-select>
            </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
import { getAction, postAction, deleteAction, putAction } from "@/api/manage";
import { mapActions, mapGetters } from "vuex";
import wangEditor from "@/components/sticker/wangEditor/index"; //验证码
import { axios, getFileUrl } from "@/utils/request";
export default {
  components: {
    wangEditor,
  },
  data() {
    return {
      title: "",
      visible: false,
      UpFileUrl: getFileUrl(),
      uploadLoading: false,
      fileList: [],
      previewVisible: false,
      previewImage: "",
      commend: "注：最多展示9张图片",
      labelCol: { span: 2 },
      wrapperCol: { span: 20 },
      form: {
        content: "",
        photos: [],
        status: ""
      },
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        contact: [{ required: true, message: "请输入联系方式", trigger: "blur" }],
        describe: [{ required: true, message: "请输入简介", trigger: "blur" }],
      },
    };
  },
  computed: {},

  methods: {
    ...mapGetters(["nickname", "avatar", "userInfo"]),
    add() {
      this.visible = true;
      this.form = {
         content: "",
        photos: [],
        status: ""
      };
      this.fileList=[];
    },
    edit(record) {
        let that=this;
      this.visible = true;
      this.form = record;
      this.fileList=[];
       this.form.photos = JSON.parse(this.form.photos);
      this.form.photos.forEach((element) => {
        that.fileList.push({
          uid: Math.random(),
          name: "image.png",
          status: "done",
          url: element.url,
        });
      });
      
      // this.fileList=this.form.thumb;
    },
     sexChange(value){
          this.form.sex=value
      },
        statusChange(value){
          this.form.status=value
      },
    handleOk() {debugger
      let that = this;
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          that.form.updateBy = that.nickname();
          that.form.userName = that.nickname();
          if (that.form.id) {
            that.form.photos = JSON.stringify(that.form.photos);
            putAction("stickeronline/moments/edit", that.form).then((res) => {
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
            that.form.photos = JSON.stringify(that.form.photos);
            that.form.createBy = that.nickname();
             that.form.userName = that.nickname();
            postAction("stickeronline/moments/add", that.form).then((res) => {
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
      this.form.describe = value;
    },
    //图片上传回调
    handleChange({ fileList }) {
      this.form.photos = [];
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i].response) {
          this.form.photos.push({url:fileList[i].response.result[0].url,fileName:fileList[i].response.result[0].fileName});
        }
      }
      this.fileList = fileList;
    },
    //图片预览
    async handlePreview(file) {
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
