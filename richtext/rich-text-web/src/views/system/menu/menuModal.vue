<template>
    <a-drawer
    :title="title"
    :width="700"
    @close="handleCancel"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :wrapStyle="{overflow: 'auto',paddingBottom: '108px'}">
    <a-form-model ref="roleForm"   :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
        <a-form-model-item ref="menuType" label="菜单类型" prop="menuType">
            <a-radio-group v-model="form.menuType" :options="options" :readOnly="disableSubmit"/>
        </a-form-model-item>
        <a-form-model-item ref="name" label="菜单名称" prop="name">
            <a-input  v-model="form.name" placeholder="请输入菜单名称" :readOnly="disableSubmit"/>
        </a-form-model-item>
        <a-form-model-item v-show="form.menuType!=3" ref="parentId" label="上级菜单" prop="parentId">
            <a-tree-select v-model="form.parentId" style="width: 100%" :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }" :treeData="treeData" placeholder="请选择父级菜单" :disabled="disableSubmit" :readOnly="disableSubmit">
            </a-tree-select>
        </a-form-model-item>
        <a-form-model-item ref="url" label="菜单路径" prop="url">
            <a-input  v-model="form.url" placeholder="请输入菜单路径" :readOnly="disableSubmit"/>
        </a-form-model-item>
        <a-form-model-item ref="component" label="前端组件" prop="component">
            <a-input  v-model="form.component" placeholder="请输入前端组件" :readOnly="disableSubmit"/>
        </a-form-model-item>
        <a-form-model-item ref="redirect" label="默认跳转地址" prop="redirect">
            <a-input  v-model="form.redirect" placeholder="请输入默认跳转地址" :readOnly="disableSubmit"/>
        </a-form-model-item>
        <a-form-model-item ref="icon" label="菜单图标" prop="icon">
          <a-input placeholder="点击右侧按钮选择图标" v-model="form.icon" :readOnly="disableSubmit">
            <a-icon slot="addonAfter" type="setting" @click="selectIcons" />
          </a-input>
        </a-form-model-item>
        <a-form-model-item ref="sortNo" label="排序" prop="sortNo" :readOnly="disableSubmit">
            <a-input  v-model="form.sortNo" type="number" />
        </a-form-model-item>
    </a-form-model>

      <!-- 选择图标 -->
      <icons @choose="handleIconChoose" @close="handleIconCancel" :iconChooseVisible="iconChooseVisible"></icons>
      <a-row :style="{textAlign:'right'}">
        <a-button :style="{marginRight: '8px'}"  @click="visible=false">
          关闭
        </a-button>
        <a-button :disabled="disableSubmit" @click="confirmA" type="primary">确定</a-button>
      </a-row>

    </a-drawer>
</template>

<script>
  import Icons from './icon/Icons'
  import {getAction,postAction,putAction} from '@/api/manage.js'
const options = [
  { label: '一级菜单', value: 0 },
  { label: '子菜单', value: 1 },
  { label: '子系统', value: 3 },
];
export default {

  components: {Icons},
  data () {
    return {
        options,
        title:'',
        visible:false,
        confirmLoading:false,
        disableSubmit:false,//是否有保存按钮
        parentId:'',
        form:{
         menuType:0,//0一级 ，1 子菜单，3 系统
         parentId:''
        },
        rules:{
            name: [{ required: true, message: '请填写菜单名称', trigger: 'blur' }],
        },
        labelCol: { span: 6 },
        wrapperCol: { span: 18 },
        treeData:[],
        iconChooseVisible:false,
    };
  },
  computed: {},

  methods: {
      loadTree(){
        var that = this;
        getAction('stickeronline/sys/permission/queryTreeList').then((res)=>{
          if(res.success){
            that.treeData = [];
            let treeList = res.result.treeList
            for(let a=0;a<treeList.length;a++){
              let temp = treeList[a];
              temp.leaf = temp.isLeaf;
              that.treeData.push(temp);
            }
          }
        });
      },
      handleCancel(){
        this.$emit('close');
        this.disableSubmit = false;
        this.visible = false;
      },
      edit(id){
          // this.form=
          this.form= Object.assign({}, id);
          this.visible=true
      },
      selectIcons(){
        this.iconChooseVisible = true
      },
      handleIconChoose (value) {
        console.log(value)
        this.form.icon = value
        this.iconChooseVisible = false
      },
      handleIconCancel () {
        this.iconChooseVisible = false
      },
      resetForm() {
          this.$nextTick(()=>{
                this.$refs.roleForm.resetFields();
          })
    },
    confirmA(){
        const that = this;
        // 触发表单验证
        this.$refs.roleForm.validate(valid => {
          if(valid){
              that.confirmLoading=true
              this.form.alwaysShow=false
              this.form.hidden=false
              this.form.route=true
              this.form.isLeaf=false
            let obj; 
              if(!this.form.id){
              obj=postAction('stickeronline/sys/permission/add',this.form);
              }else{
              obj=putAction('stickeronline/sys/permission/edit',this.form);
              }
              obj.then((res)=>{
              if(res.success){
                that.$message.success("操作成功！");
                that.$emit('close');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
        })
    }

  },

  watch: {},

  mounted(){
      this.loadTree()
  },

  created(){},

}

</script>
<style lang='scss' scoped>
</style>