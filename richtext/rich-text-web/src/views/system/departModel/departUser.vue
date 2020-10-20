<template>
  <div>
    <a-divider orientation="left">添加用户</a-divider>
    <a-button type="primary" style="margin-bottom:20px" @click="addUser">添加用户</a-button>
    <a-table :columns="columns" :data-source="data" bordered>
        <template slot="sex" slot-scope="text, record, index">
            {{text==1?'男':'女'}}
        </template>
        <template slot="op" slot-scope="text, record, index">
            <a>删除</a>
        </template>
    </a-table>
    <a-modal :width="800" v-model="visible" title="添加用户" footer="">
        <a-input-search placeholder="账户名称" style="width: 200px;margin-bottom:20px" @search="onSearch" />
        <a-table rowKey="id" :row-selection="{ selectedRowKeys: selectedRowKeys,rowKey:'id', onChange: onSelectChange }" bordered :columns="columns" :data-source="alldata">
            <template slot="sex" slot-scope="text, record, index">
                {{text==1?'男':'女'}}
            </template>
            <template slot="op" slot-scope="text, record, index">
                <a v-if="!isInArray(selectedRowKeys,text)" @click="add(text)">添加</a>
                <a v-else @click="del(text)">删除</a>
            </template>
        </a-table>
    </a-modal>
  </div>
</template>

<script>
 import {getAction,postAction,putAction,deleteAction} from '@/api/manage.js'
export default {
  components: {},
  props:{
      departId:{
          type:String,
          default:''
      }
  },
  data () {
    return {
        visible:false,
        columns:[{
            title: '用户账号',
            align: "center",
            width: 100,
            dataIndex: 'username',
          },{
            title: '用户名称',
            align: "center",
            width: 100,
            dataIndex: 'realname'
          },{
            title: '性别',
            align: "center",
            width: 100,
            dataIndex: 'sex',
            scopedSlots: {customRender: "sex"}
          },{
            title: '操作',
            align: "center",
            width: 100,
            key:'id',
            dataIndex: 'id',
            scopedSlots: {customRender: "op"}
          }],
        data:[],
        alldata:[],
        selectedRowKeys:[],

    };
  },
  computed: {},

  methods: {
      onSelectChange(selectedRowKeys){
          console.log('selectedRowKeys changed: ', selectedRowKeys);
        this.selectedRowKeys = selectedRowKeys;                 
      },
      onSearch(value){
         getAction('stickeronline/sys/user/list?username='+value).then(res=>{
          this.alldata=res.result.content
      })
      },
      getData(id){
          if(id){
              getAction('stickeronline/sys/user/departUserList?depId='+this.departId).then(res=>{
                this.data=res.result.content
                this.selectedRowKeys=this.data.map(item=>{
                    return item.id
                })
            })
          }else{
                this.data=[]
          }
      },
      addUser(){
          if(this.departId==""){
              this.$message.warning("请先选择将用户添加的部门")
          }else{
            this.visible=true
          }
      },
      add(userId){
          let params={
              depId:this.departId,
              userIdList:[userId]
          }
          postAction('stickeronline/sys/user/editSysDepartWithUser',params).then(res=>{
              if(res.success){
                  this.$message.success(res.result)
                  this.selectedRowKeys.push(userId)
                  this.getData(this.departId)
              }else{
                  this.$message.warning(res.result)
              }
          })
      },
      del(userId){
          let params={
              depId:this.departId,
              userId:userId
          }
          deleteAction('stickeronline/sys/user/deleteUserInDepartBatch',params).then(res=>{
              if(res.success){
                  this.$message.success(res.result)
                    for (var i = 0; i < this.selectedRowKeys.length; i++) {
                        if(this.selectedRowKeys[i]==userId){
                            this.selectedRowKeys.splice(i, 1);
                        }
                    }
                    this.getData(this.departId)
              }else{
                  this.$message.warning(res.result)
              }
          })
      },
      isInArray(arr,value){
        for(var i = 0; i < arr.length; i++){
            if(value === arr[i]){
                return true;
            }
        }
        return false;
    }
  },

  watch: {
      'departId'(val) {
        this.getData(val)
      }
  },

  mounted(){
      getAction('stickeronline/sys/user/list').then(res=>{
          this.alldata=res.result.content
      })
  },

  created(){},

}

</script>
<style lang='scss' scoped>
</style>