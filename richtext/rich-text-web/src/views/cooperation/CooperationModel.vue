<template>
    <a-drawer  :title="title+'用户'" width='500' :placement="placement" :closable="false" :visible="visible" @close="onClose">
        <a-form-model ref="userForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="title" label="合作事项：" prop="title">
                <a-input  v-model="form.title" @blur=" () => { $refs.title.onFieldBlur(); }" placeholder="合作事项" />
            </a-form-model-item>
             <a-form-model-item ref="contact" label="联系电话：" prop="contact">
                <a-input  v-model="form.contact" @blur=" () => { $refs.contact.onFieldBlur(); }" placeholder="联系电话" />
            </a-form-model-item>
           
            <a-form-model-item ref="contents" label="合作内容" prop="contents">
                <a-textarea
      v-model="form.contents" placeholder="合作内容"
      :auto-size="{ minRows: 3, maxRows: 5 }"
    />
            </a-form-model-item>
  <a-form-model-item ref="status" label="审核状态" prop="status" >
                <a-select style="width: 100%;" :value="form.status" @change="statusChange">
                    <a-select-option :value="0">待审核</a-select-option>
                    <a-select-option :value="1">审核通过</a-select-option>
                    <a-select-option :value="-1">审核未通过</a-select-option>
                </a-select>
            </a-form-model-item>

           

            <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
                <a-button type="primary" @click="handleSubmit"> 确认 </a-button>
                <a-button style="margin-left: 10px;" @click="resetForm"> 重置 </a-button>
            </a-form-model-item>
        </a-form-model>
    </a-drawer>
</template>

<script>
 import Vue from 'vue'
 import moment from 'moment'
 import {getAction,postAction} from '@/api/manage.js'
 import { ACCESS_TOKEN } from "@/store/mutation-types"
import { putAction } from '@/api/manage';
import { axios, getFileUrl } from "@/utils/request";
export default {
  name:'userModel',
  data () {
    return {
        moment,
        UpFileUrl:getFileUrl(),
        fileList:[],
        uploadLoading: false,
      previewVisible: false,
      previewImage: "",
      commend: "注：最多展示8张图片",
        visible:false,
        placement: 'right',
        title:'',
        labelCol: { span: 4 },
        wrapperCol: { span: 18 },
        form: {
            title: '',
            contact:'',
            contents:'',
            status:''
        },
        headers:{},
        uploadLoading:false,
        picUrl: "",
        rules: {
            title: [{ required: true, message: '合作事项', trigger: 'blur' }],
            contact: [{ required: true, message: '联系电话', trigger: 'blur' }],
            contents:[{ required: true, message: '合作内容', trigger: 'blur' }],
            status: [{ required: true, message: '审核状态', trigger: 'blur' }]

        }
    };
  },

  watch: {},
  created(){
    const token = Vue.ls.get(ACCESS_TOKEN);
    this.headers = {"X-Access-Token":token}
  },
  mounted(){},
  components: {},
  computed: {},
  methods: {
      onClose(){
          this.visible=false
      },
      add(){
          this.visible=true
          this.form= {
            title: '',
            contact:'',
            contents:'',
            status:''
        }
        this.previewImage="";
      },
      edit(value){
          this.visible=true
          this.form=value
     
      },
      resetForm() {//重置表单
        this.$refs.userForm.resetFields();
      },
     
    
      beforeUpload: function(file){
        var fileType = file.type;
        if(fileType.indexOf('image')<0){
          this.$message.warning('请上传图片');
          return false;
        }
        //TODO 验证文件大小
      },
      handleChange (info) {
        this.picUrl = "";
        if (info.file.status === 'uploading') {
          this.uploadLoading = true
          return
        }
        if (info.file.status === 'done') {
          var response = info.file.response;
          this.uploadLoading = false;
          console.log(response);
          if(response.success){
            this.form.avatar = response.result[0].url;
            this.picUrl = "Has no pic url yet";
          }else{
            this.$message.warning(response.msg);
          }
        }
      },
      startDateChange(data,value){
          this.form.startDate=data
      },
      statusChange(value){
          this.form.status=value
      },
      handleSubmit(){
         this.$refs.userForm.validate(valid => {
             if (valid) {
                if(this.form.id){//编辑
                    putAction('/stickeronline/cooperation/edit',this.form).then(res=>{
                        if(res.success){
                            this.$message.success('操作成功！')
                            this.resetForm()
                            this.visible=false
                            this.$emit('close')
                        }else{
                            this.$message.warning('操作失败！')
                        }
                    })
                }else{
                    postAction('/stickeronline/cooperation/add',this.form).then(res=>{
                        if(res.success){
                            this.$message.success('操作成功！')
                            this.resetForm()
                            this.visible=false
                            this.$emit('close')
                        }else{
                            this.$message.warning('操作失败！')
                        }
                    })
                }//新增
            } else {
            console.log('error submit!!');
                return false;
            }
          })
      },
        //图片上传回调
    handleChange({ fileList }) {
      this.form.avatarUrl = "";
      for (let i = 0; i < fileList.length; i++) {
        if (fileList[i].response) {
          this.form.avatarUrl=fileList[i].response.result[0].url;
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
    }

  }
}

</script>
<style lang='scss' scoped>
</style>