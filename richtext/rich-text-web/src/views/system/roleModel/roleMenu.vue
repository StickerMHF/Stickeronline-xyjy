<template>
 <a-drawer
    :title="title"
    :maskClosable="true"
    :width="700"
    placement="right"
    :closable="true"
    @close="handleCancel"
    :visible="visible"
    style="overflow: auto;padding-bottom: 53px;"
  >
  <a-tree
        checkable
        multiple
        style="height:100%"
        @check="onCheck"
        :checkedKeys="checkedKeys"
        :treeData="departTree"
        :checkStrictly="true"
      />

      <div
        :style="{
          position: 'absolute',
          right: 0,
          bottom: 0,
          width: '100%',
          borderTop: '1px solid #e9e9e9',
          padding: '10px 16px',
          background: '#fff',
          textAlign: 'right',
          zIndex: 1,
        }"
      >
        <a-button :style="{ marginRight: '8px' }" @click="handleCancel">
          取消
        </a-button>
        <a-button type="primary" @click="saveLimit">
          修改并保存
        </a-button>
      </div>
  </a-drawer>

</template>

<script>
 import {getAction,postAction,putAction} from '@/api/manage.js'
export default {
  components: {
  },
  data () {
    return {
       title:'操作',
       visible:false,
       checkedKeys:[],
       departTree:[],
       roleId:''
    };
  },
  computed: {},

  methods: {
    onCheck(checkedKeys, info) {
      this.checkedKeys = checkedKeys.checked
    },
    handleCancel(){
     this.visible=false
    },
    open(id){
        this.visible=true
        getAction('stickeronline/sys/permission/list').then(res=>{
            this.departTree=res.result
        })
        this.roleId=id
        let params = {
        roleId: id
      }
        getAction('stickeronline/sys/role/queryTreeList',params).then(res=>{
            this.checkedKeys = res.result.ids
            // this.departTree=res.result.treeList
        })
    },
    saveLimit() {
      let params = {
        lastpermissionIds: '',
        roleId: this.roleId,
        permissionIds: this.checkedKeys.join(',')
      }
      postAction('stickeronline/sys/permission/saveRolePermission',params).then(res => {
        if (res.success) {
          this.$message.success(res.result.data)
          this.visible=false
        } else {
          this.$message.error('操作失败！')
        }
      })
    },
  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>