<template>
      <a-modal  :title="title" :width='1100'  v-model="visible" @ok="handleOk">
        <a-form-model ref="addForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="name" label="标题" prop="name">
                <a-input  v-model="form.name" placeholder="请输入标题"/>
            </a-form-model-item>
            <a-form-model-item label="内容">
                <wangEditor v-model="form.content" :isClear="true" @change="change"></wangEditor>
            </a-form-model-item>
        </a-form-model>
    </a-modal>
</template>

<script>
import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
import wangEditor from '@/components/sticker/wangEditor/index'//验证码
export default {
  components: {
      wangEditor
  },
  data () {
    return {
        title:'',
        visible:false,
        labelCol: { span: 2 },
        wrapperCol: { span: 20 },
        form: {
            content:""
        },
        rules:{
            name:[{ required: true, message: '请输入记录标题', trigger: 'blur' },]
        }

    };
  },
  computed: {},

  methods: {
      add(){
        this.visible=true
        this.form={}

      },
      edit(record){
            this.visible=true
            this.form=record
      },
      handleOk(){
        let that =this
        this.$refs.addForm.validate(valid => {
            if (valid) {
                if(this.form.id){
                    putAction('rich/text/edit',this.form).then(res=>{
                        if(res.success){
                            this.$message.success(res.result)
                            this.$emit("close")
                            this.form={}
                            this.visible=false
                        }else{
                            this.$message.warning(res.result)
                        }
                    })
                }else{
                    postAction('rich/text/add',this.form).then(res=>{
                        if(res.success){
                            this.$message.success(res.result)
                            this.$emit("close")
                            this.form={}
                            this.visible=false
                        }else{
                            this.$message.warning(res.result)
                        }
                    })
                }
            }
        })
      },
      //富文本编辑框
      change(value){
          this.form.content=value
      }
  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>