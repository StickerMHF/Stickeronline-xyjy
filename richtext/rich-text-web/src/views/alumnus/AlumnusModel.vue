<template>
    <a-drawer  :title="title+'校友会'" width='500' :placement="placement" :closable="false" :visible="visible" @close="onClose">
        <a-form-model ref="userForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="name" label="名称：" prop="name">
                <a-input  v-model="form.name" @blur=" () => { $refs.name.onFieldBlur(); }" placeholder="校友会名称" />
            </a-form-model-item>
            
            <a-form-model-item ref="type" label="分类" prop="type" >
                <a-select style="width: 100%;" :value="form.type" @change="typeChange">
                    <a-select-option value="2">校友之窗</a-select-option>
                    <a-select-option value="3">同城校友会</a-select-option>
                    <a-select-option value="4">行业校友会</a-select-option>
                </a-select>
            </a-form-model-item>
  <a-form-model-item ref="liveness" label="活跃度：" prop="liveness">
                <a-input  v-model="form.liveness" @blur=" () => { $refs.liveness.onFieldBlur(); }" placeholder="活跃度" />
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
            name: '',
            liveness:'',
            type:''
        },
        headers:{},
        uploadLoading:false,
        picUrl: "",
        rules: {
            name: [{ required: true, message: '名称', trigger: 'blur' }],
            liveness: [{ required: true, message: '活跃度', trigger: 'blur' }],
            type: [{ required: true, message: '类型', trigger: 'blur' }]

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
            name: '',
            liveness:'',
            type:''
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
      typeChange(data,value){
          this.form.type=data
      },
      statusChange(value){
          this.form.status=value
      },
      handleSubmit(){
         this.$refs.userForm.validate(valid => {
             if (valid) {
                if(this.form.id){//编辑
                    putAction('/stickeronline/alumnus/edit',this.form).then(res=>{
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
                    postAction('/stickeronline/alumnus/add',this.form).then(res=>{
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