<template>
    <a-drawer  :title="title+'用户'" width='500' :placement="placement" :closable="false" :visible="visible" @close="onClose">
        <a-form-model ref="userForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="openid" label="微信ID：" prop="openid">
                <a-input  v-model="form.openid" @blur=" () => { $refs.openid.onFieldBlur(); }" placeholder="请绑定微信ID" :readOnly="!!form.openid"/>
            </a-form-model-item>
             <a-form-model-item ref="name" label="姓名：" prop="name">
                <a-input  v-model="form.name" @blur=" () => { $refs.name.onFieldBlur(); }" placeholder="请输入用户名" />
            </a-form-model-item>
            <a-form-model-item ref="sex" label="性别" prop="sex" >
                <a-select style="width: 100%;" :value="form.sex" @change="sexChange">
                    <a-select-option :value="1">男</a-select-option>
                    <a-select-option :value="2">女</a-select-option>
                </a-select>
            </a-form-model-item>

            <a-form-model-item ref="identityCard" label="身份证号" prop="identityCard">
                <a-input  v-model="form.identityCard" placeholder="身份证号"/>
            </a-form-model-item>

            <!-- <a-form-model-item label="头像">
                <a-upload listType="picture-card" class="avatar-uploader" :showUploadList="false" :action="UpFileUrl" :data="{'isup':1}" :headers="headers" :beforeUpload="beforeUpload"  @change="handleChange">
                    <img v-if="form.avatarUrl" :src="form.avatarUrl" alt="头像" style="height:104px;max-width:300px"/>
                    <div v-else>
                    <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
                    <div class="ant-upload-text">上传</div>
                    </div>
                </a-upload>
            </a-form-model-item> -->
             <a-form-model-item ref="img" label="头像">
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

          
            <a-form-model-item ref="college" label="所属学院：" prop="college">
                <a-input  v-model="form.college" @blur=" () => { $refs.college.onFieldBlur(); }" placeholder="请输入所属学院" />
            </a-form-model-item>
             <a-form-model-item ref="profession" label="所在专业：" prop="profession">
                <a-input  v-model="form.profession" @blur=" () => { $refs.profession.onFieldBlur(); }" placeholder="请输入所在专业" />
            </a-form-model-item>
            
             <a-form-model-item ref="education" label="学历" prop="education" >
                <a-select style="width: 100%;" :value="form.education" @change="educationChange">
                    <a-select-option value="大专">大专</a-select-option>
                    <a-select-option value="本科">本科</a-select-option>
                     <a-select-option value="硕士">硕士</a-select-option>
                      <a-select-option value="博士">博士</a-select-option>
                </a-select>
            </a-form-model-item>

             <a-form-model-item ref="type" label="类型" prop="type" >
                <a-select style="width: 100%;" :value="form.type" @change="typeChange">
                    <a-select-option :value="1">曾在校学习或工作</a-select-option>
                    <a-select-option :value="2">在校生</a-select-option>
                    <a-select-option :value="3">教职工</a-select-option>
                </a-select>
            </a-form-model-item>
           <a-form-model-item ref="startDate" label="入校时间" prop="startDate" >
                <a-date-picker style="width: 100%;" :value="form.startDate" valueFormat='YYYY-MM-DD' @change="startDateChange"/>
            </a-form-model-item>


            <a-form-model-item ref="email" label="邮箱" >
                <a-input  v-model="form.email" placeholder="请输入邮箱"/>
            </a-form-model-item>

            <a-form-model-item ref="phone" label="手机号" >
                <a-input  v-model="form.phone" placeholder="请输入手机号"/>
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
            username: '',
            password:'',
            education:'',
            realname:'',
            avatar:'',
            type:'',
            sex:1,//1男2女
            phone:'',
            email:'',
            roles:''
        },
        headers:{},
        uploadLoading:false,
        picUrl: "",
        rules: {
            openid: [{ required: true, message: '微信ID', trigger: 'blur' }],
            name: [{ required: true, message: '姓名', trigger: 'blur' }],
            type:[{ required: true, message: '请选择类型', trigger: 'blur' }],
            identityCard: [{ required: true, message: '身份证号', trigger: 'blur' }],
            college: [{ required: true, message: '所属学院', trigger: 'blur' }],
            startDate:[{ required: true, message: '请选择入校时间', trigger: 'blur' },]

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
            username: '',
            password:'',
            type:'',
            avatar:'',
            startDate:'',
            sex:1,
            education:'',
            phone:'',
            email:'',
            avatarUrl:''
        }
        this.previewImage="";
      },
      edit(value){
          this.visible=true
          this.form=value
          this.previewImage=this.form.avatarUrl;
          this.fileList=[];
        this.fileList.push({
          uid: Math.random(),
          name: "image.png",
          status: "done",
          url: this.form.avatarUrl,
        });
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
      typeChange(data,value){
this.form.type=data
      },
       educationChange(data,value){
this.form.education=data
      },
      sexChange(value){
          this.form.sex=value
      },
      handleSubmit(){
         this.$refs.userForm.validate(valid => {
             if (valid) {
                if(this.form.id){//编辑
                    putAction('/stickeronline/wechat/users/edit',this.form).then(res=>{
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
                    postAction('/stickeronline/wechat/users/add',this.form).then(res=>{
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