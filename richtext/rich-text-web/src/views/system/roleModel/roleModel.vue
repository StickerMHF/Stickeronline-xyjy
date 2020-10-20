<template>
    <a-modal  :title="title+'角色'" :width='500'  v-model="visible" @ok="handleSubmit" >
        <a-form-model ref="roleForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="roleName" label="角色名：" prop="roleName">
                <a-input  v-model="form.roleName" placeholder="请输入角色名" />
            </a-form-model-item>
            <a-form-model-item ref="roleCode" label="角色编码" prop="roleCode">
                <a-input  v-model="form.roleCode" placeholder="请输入角色编码" />
            </a-form-model-item>
            <a-form-model-item ref="description" label="备注" prop="description">
                <a-textarea :rows="4" v-model="form.description" placeholder="请输入..." />
            </a-form-model-item>
        </a-form-model>
    </a-modal>
</template>

<script>
 import Vue from 'vue'
 import moment from 'moment'
 import {getAction,postAction,putAction} from '@/api/manage.js'
export default {
  name:'roleModel',
  data () {
    return {
        visible:false,
        title:'',
        labelCol: { span: 4 },
        wrapperCol: { span: 18 },
        form: {
            roleName: '',
            roleCode:'',
            description:'',
        },
        rules: {
            roleName: [{ required: true, message: '请填写角色名', trigger: 'blur' }],
            roleCode:[{required: true, message: '请输入角色编码',},
                    {pattern: /^[a-z|A-Z|0-9|+|.|\-|*|/]{0,}$/, message: '编码只能为字母、数字、+-*/.'}],
            description:[{ required: true, message: '请输入', trigger: 'blur' },],
        }
    };
  },

  watch: {},
  created(){

  },
  mounted(){},
  components: {},
  computed: {},
  methods: {
      add(){
          this.visible=true
          this.form= {
            roleName: '',
            roleCode:'',
            description:'',
        }
      },
      edit(value){
          this.visible=true
          this.form=value
      },
      resetForm() {//重置表单
        this.$refs.roleForm.resetFields();
      },

      handleSubmit(){
         this.$refs.roleForm.validate(valid => {
             if (valid) {
                if(this.form.id){//编辑
                    putAction('/stickeronline/sys/role/edit',this.form).then(res=>{
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
                    postAction('/stickeronline/sys/role/add',this.form).then(res=>{
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