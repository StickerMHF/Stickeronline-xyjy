<template>
    <a-card title="常用组件库" style="width: 100%">
        <a-form :form="form" class="user-layout-login" ref="formLogin" id="formLogin">
            <a-collapse v-model="activeKey" class="commonComponent" accordion>
                <!-- 验证码 -->
                <a-collapse-panel key="1" header="验证码">
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
                </a-collapse-panel>
                <!-- 代码编辑器 -->
                <a-collapse-panel key="2" header="代码编辑器">
                    <codemirror ref="codemirror" :value="codeValue" @input="input"></codemirror>
                </a-collapse-panel>
                <!-- 富文本编辑器 -->
                <a-collapse-panel key="3" header="富文本编辑器">
                    <wangEditor v-model="editorValue" :isClear="true" @change="change"></wangEditor>
                </a-collapse-panel>
                <a-collapse-panel key="4" header="文件上传">
                    <stopUpload></stopUpload>
                </a-collapse-panel>
            </a-collapse>
        </a-form>
    </a-card>
</template>

<script>
  import JGraphicCode from '@/components/sticker/JGraphicCode'//验证码
  import codemirror from '@/components/sticker/codemirror/index'//验证码
  import wangEditor from '@/components/sticker/wangEditor/index'//验证码
  import stopUpload from '@/components/filesUpload/stopUpload'//验证码
  export default {
    components: {
        JGraphicCode,
        codemirror,
        wangEditor,
        stopUpload
    },
    data () {
      return {
          activeKey: ['1'],//条目活跃值
          form:this.$form.createForm(this),
          verifiedCode:'',//验证码值
          inputCodeContent:'',//输入的值
          inputCodeNull:true,
          validatorRules:{
            inputCode:{rules: [{ required: true, message: '请输入验证码!'},{validator: this.validateInputCode}]}
          },
          codeValue:'',//代码编辑器值，
          editorValue:'',//富文本编辑
      }
    },
    created () {

    },
     computed: {

  },
    methods: {
        // 验证码
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
      validateInputCode(rule,value,callback){
        if(!value || this.verifiedCode==this.inputCodeContent){
          callback();
        }else{
          callback("您输入的验证码不正确!");
        }
      },
    //   代码编辑器
      input(value){//双向绑定值
          this.codeValue=value
      },
      //富文本编辑框
      change(value){
          this.editorValue=value
      }
    }
  }
</script>

<style lang="scss" scoped>

.commonComponent{

}
</style>
