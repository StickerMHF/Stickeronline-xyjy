<template>
  <div class="main">
      <a-form :form="form" class="user-layout-login" ref="formLogin" id="formLogin">
        <a-tabs :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }">
          <a-tab-pane key="tab1" tab="账号密码登陆">
            <a-form-item>
              <a-input size="large" v-decorator="['username',validatorRules.username]" type="text" placeholder="请输入帐户名 / sticker">
                <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>

            <a-form-item>
              <a-input v-decorator="['password',validatorRules.password]" size="large" type="password" autocomplete="false" placeholder="密码 / 123456">
                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
              </a-input>
            </a-form-item>

            <a-row :gutter="0">
              <a-col :span="14">
                <a-form-item>
                  <a-input
                    v-decorator="['inputCode',validatorRules.inputCode]"
                    size="large"
                    type="text"
                    @change="inputCodeChange"
                    placeholder="请输入验证码">
                    <a-icon slot="prefix" v-if=" inputCodeContent==verifiedCode " type="smile" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                    <a-icon slot="prefix" v-else type="frown" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                  </a-input>
                </a-form-item>
              </a-col>
              <a-col  :span="10">
                <j-graphic-code @success="generateCode" style="float: right"></j-graphic-code>
              </a-col>
            </a-row>

          </a-tab-pane>
        </a-tabs>

        <a-form-item>
          <a-checkbox v-model="formLogin.rememberMe">自动登陆</a-checkbox>
          <a-button type="link" class="forge-password" @click="wrapSwitch(true)" style="float: right;" >忘记密码</a-button>
        </a-form-item>

        <a-form-item style="margin-top:24px">
          <a-button
            size="large"
            type="primary"
            htmlType="submit"
            class="login-button"
            :loading="loginBtn"
            @click.stop.prevent="handleSubmit"
            :disabled="loginBtn">确定
          </a-button>
        </a-form-item>
      </a-form>
  </div>
</template>

<script>
  //import md5 from "md5"
  import api from '@/api'
  import { mapActions } from "vuex"
  import { timeFix } from "@/utils/util"
  import Vue from 'vue'
  import { ACCESS_TOKEN } from "@/store/mutation-types"
  import { putAction } from '@/api/manage'
  import JGraphicCode from '@/components/sticker/JGraphicCode'//验证码

  export default {
    components: {
      JGraphicCode
    },
    data () {
      return {
        form:this.$form.createForm(this),
        loginBtn: false,
        formLogin: {
          username: "",
          password: "",
          rememberMe: true
        },
        validatorRules:{
          username:{rules: [{ required: true, message: '请输入用户名!',validator: 'click'}]},
          password:{rules: [{ required: true, message: '请输入密码!',validator: 'click'}]},
          inputCode:{rules: [{ required: true, message: '请输入验证码!'},{validator: this.validateInputCode}]}
        },
        verifiedCode:"",
        inputCodeContent:"",
        inputCodeNull:true,
      }
    },
    created () {
      Vue.ls.remove(ACCESS_TOKEN)
    },
     computed: {

  },
    methods: {
      ...mapActions([ "Login", "Logout" ]),
      handleSubmit () {
        let that = this
        let loginParams = {
          remember_me: that.formLogin.rememberMe
        };
        // 使用账户密码登陆
          that.form.validateFields([ 'username', 'password','inputCode' ], { force: true }, (err, values) => {
            if (!err) {
              loginParams.username = values.username
              loginParams.password = values.password
              that.Login(loginParams).then((res) => {
                this.loginSuccess()
              }).catch((err) => {
                 this.loginBtn = false;
              })
            }
          })
      },
      loginSuccess () {
        this.loginBtn = false
        this.$router.push({ name: "index" })
        this.$notification.success({
          message: '欢迎',
          description: `${timeFix()}，欢迎回来`,
        });
      },
      // requestFailed (err) {
      //   this.$notification[ 'error' ]({
      //     message: '登录失败',
      //     description: ((err.response || {}).data || {}).message || err.message || "请求出现错误，请稍后再试",
      //     duration: 4,
      //   });
      //   this.loginBtn = false;
      // },

      validateInputCode(rule,value,callback){
        if(!value || this.verifiedCode==this.inputCodeContent){
          callback();
        }else{
          callback("您输入的验证码不正确!");
        }
      },
      generateCode(value){
        this.verifiedCode = value.toLowerCase()
      },
      inputCodeChange(e){
        this.inputCodeContent = e.target.value
        if(!e.target.value||0==e.target.value){
          this.inputCodeNull=true
        }else{
          this.inputCodeContent = this.inputCodeContent.toLowerCase()
          this.inputCodeNull=false
        }
      },
    }
  }
</script>

<style lang="scss" scoped>
  .user-layout-login {
    label {
      font-size: 14px;
    }
    button.login-button {
      padding: 0 15px;
      font-size: 16px;
      height: 40px;
      width: 100%;
    }
  }

</style>
<style>
  .valid-error .ant-select-selection__placeholder{
    color: #f5222d;
  }
</style>