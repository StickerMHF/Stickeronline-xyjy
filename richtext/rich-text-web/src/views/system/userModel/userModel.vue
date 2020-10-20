<template>
    <a-drawer  :title="title+'用户'" width='500' :placement="placement" :closable="false" :visible="visible" @close="onClose">
        <a-form-model ref="userForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="username" label="用户名：" prop="username">
                <a-input  v-model="form.username" @blur=" () => { $refs.username.onFieldBlur(); }" placeholder="请输入用户名" :readOnly="!!form.id"/>
            </a-form-model-item>
            <template v-if="!form.id">
                <a-form-model-item ref="password" label="登陆密码" prop="password">
                    <a-input type='password'  v-model="form.password" placeholder="登陆密码"/>
                </a-form-model-item>
                <a-form-model-item ref="confirmpassword" label="确认密码" prop="confirmpassword">
                    <a-input type='password' v-model="form.confirmpassword" placeholder="确认登陆密码"/>
                </a-form-model-item>
            </template>
            <a-form-model-item ref="realname" label="用户名称" prop="realname">
                <a-input  v-model="form.realname" placeholder="用户名称"/>
            </a-form-model-item>

            <a-form-model-item label="头像">
                <a-upload listType="picture-card" class="avatar-uploader" :showUploadList="false" :action="UpFileUrl" :data="{'isup':1}" :headers="headers" :beforeUpload="beforeUpload"  @change="handleChange">
                    <img v-if="form.avatar" :src="form.avatar" alt="头像" style="height:104px;max-width:300px"/>
                    <div v-else>
                    <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
                    <div class="ant-upload-text">上传</div>
                    </div>
                </a-upload>
            </a-form-model-item>

            <a-form-model-item ref="birthday" label="出生日期" prop="birthday" >
                <a-date-picker style="width: 100%;" :value="form.birthday" valueFormat='YYYY-MM-DD' @change="birthdayChange"/>
            </a-form-model-item>

            <a-form-model-item ref="sex" label="性别" prop="sex" >
                <a-select style="width: 100%;" :value="form.sex" @change="sexChange">
                    <a-select-option :value="1">男</a-select-option>
                    <a-select-option :value="2">女</a-select-option>
                </a-select>
            </a-form-model-item>

            <a-form-model-item ref="email" label="邮箱" prop="email">
                <a-input  v-model="form.email" placeholder="请输入邮箱"/>
            </a-form-model-item>

            <a-form-model-item ref="phone" label="手机号" prop="phone">
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
export default {
  name:'userModel',
  data () {
    return {
        moment,
        UpFileUrl:this.$getBaseUrl()+"/stickeronline/file/fileUpload",
        visible:false,
        placement: 'right',
        title:'',
        labelCol: { span: 4 },
        wrapperCol: { span: 18 },
        form: {
            username: '',
            password:'',
            confirmpassword:'',
            realname:'',
            avatar:'',
            birthday:'',
            sex:1,//1男2女
            phone:'',
            email:'',
            roles:''
        },
        headers:{},
        uploadLoading:false,
        picUrl: "",
        rules: {
            username: [{ required: true, message: '请填写用户名', trigger: 'blur' },
                    { validator: this.validateUsername, trigger: 'change' }],
            password:[{required: true, message: '请输入密码!',}, 
                    {validator: this.validateToNextPassword,}],
            confirmpassword:[{required: true, message: '请重新输入登陆密码!',}, 
                    {validator: this.compareToFirstPassword,}],
            realname:[{ required: true, message: '请输入真实姓名', trigger: 'blur' },],
            birthday:[{ required: true, message: '请选择出生日期', trigger: 'blur' },]

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
            confirmpassword:'',
            realname:'',
            avatar:'',
            birthday:'',
            sex:1,
            phone:'',
            email:'',
        }
      },
      edit(value){
          this.visible=true
          this.form=value
      },
      resetForm() {//重置表单
        this.$refs.userForm.resetFields();
      },
      validateUsername(rule, value, callback){
        var params = {
          id:this.form.id,
          username:value
        };
        //接口的返回方式得改 返回码不能为400
        getAction("stickeronline/sys/user/checkOnlyUser",params).then((res)=>{
          if(res.success){
            callback();
          }else{
            callback("用户账号已存在！");
          }
        });
      },
      validateToNextPassword  (rule, value, callback) {
        // const form = this.form;
        const confirmpassword=this.form.confirmpassword;
        if (value && confirmpassword && value !== confirmpassword) {
          callback('两次输入的密码不一样！');
        }
        if (value && this.confirmDirty) {
          form.validateFields(['confirm'], { force: true })
        }
        callback();
      },
      compareToFirstPassword  (rule, value, callback) {
        const password=this.form.password;
        if (value && value !== password) {
          callback('两次输入的密码不一样！');
        } else {
          callback()
        }
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
      birthdayChange(data,String){
          this.form.birthday=String
      },
      sexChange(value){
          this.form.sex=value
      },
      handleSubmit(){
         this.$refs.userForm.validate(valid => {
             if (valid) {
                if(this.form.id){//编辑
                    putAction('/stickeronline/sys/user/edit',this.form).then(res=>{
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
                    postAction('/stickeronline/sys/user/add',this.form).then(res=>{
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
      }

  }
}

</script>
<style lang='scss' scoped>
</style>