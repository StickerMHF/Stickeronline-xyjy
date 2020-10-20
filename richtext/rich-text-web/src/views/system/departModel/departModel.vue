<template>
    <a-modal  :title="title" :width='600'  v-model="visible" @ok="handleSubmit" >
        <a-form-model ref="departForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item v-if="op" ref="roleName" label="上级部门：" prop="roleName">
                <a-tree-select disabled v-model="form.parentId" style="width: 100%" :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }" :tree-data="treeData"></a-tree-select>
            </a-form-model-item>
            <a-form-model-item ref="departName" label="部门名称" prop="departName">
                <a-input  v-model="form.departName" placeholder="请输入部门名称" />
            </a-form-model-item>
            <a-form-model-item ref="departNameEn" label="英文名称" prop="departNameEn">
                <a-input  v-model="form.departNameEn" placeholder="请输入英文名称" />
            </a-form-model-item>
            <a-form-model-item ref="orgCode" label="机构代码" prop="orgCode">
                <a-input  v-model="form.orgCode" placeholder="请输入机构代码" />
            </a-form-model-item>
            <a-form-model-item ref="departOrder" label="排序" prop="departOrder">
                <a-input  v-model="form.departOrder" placeholder="请输入部门顺序" type="number" />
            </a-form-model-item>
            <a-form-model-item ref="mobile" label="手机号" prop="mobile">
                <a-input  v-model="form.mobile" placeholder="请输入手机号" />
            </a-form-model-item>
            <a-form-model-item ref="address" label="地址" prop="address">
                <a-input  v-model="form.address" placeholder="请输入部门地址地址" />
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
  props:{
    treeData:{
        type:Array,
        default:[]
    }
  },
  data () {
    return {
        visible:false,
        title:'',
        labelCol: { span: 4 },
        wrapperCol: { span: 18 },
        form: {
            parentId:'',
            departName: '',
            departNameEn:'',
            departOrder:'',
            description:'',
            mobile:'',
            orgCode:'',
            address:'',
            parentId:''
        },
        rules: {
            departName: [{ required: true, message: '请填写角色名', trigger: 'blur' }],
            departOrder:[{required: true, message: '请输入部门顺序',}],
            description:[{ required: true, message: '请输入', trigger: 'blur' },],
        },
        op:true,//true 一级  false 子部门
    };
  },

  watch: {},
  created(){

  },
  mounted(){},
  components: {},
  computed: {},
  methods: {
      add(value,pid){
          this.visible=true
          this.op=value
          if(pid){
              this.form.parentId=pid[0]
          }
          this.$nextTick(function(){
                this.resetForm() //输出：修改后的值
            });
      },
      resetForm() {//重置表单
        this.$refs.departForm.resetFields();
      },

      handleSubmit(){
           this.$refs.departForm.validate(valid => {
               if (valid) {
                   postAction('stickeronline/sysdepart/sysDepart/add',this.form).then(res=>{
                       if(res.success){
                           this.$message.success(res.result)
                           this.visible=false
                           this.$emit('close')
                       }else{
                           this.$message.warning(res.result)
                       }
                   })
               }else{
                   return false
               }
           })
      }

  }
}

</script>
<style lang='scss' scoped>
</style>