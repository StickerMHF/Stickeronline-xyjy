<template>
    <div style="margin: 56px 0 0 10px;">
        <div class="table-operator" style="">
            <a-button @click="addUser" type="primary" icon="plus">添加用户</a-button>
        </div>
    <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource">
            <template slot="sex" slot-scope="text, record, index">
                {{text==1?'男':'女'}}
            </template>
            <template slot="op" slot-scope="text, record, index">
                 <a-popconfirm
                    title="确定删除?"
                    ok-text="Yes"
                    cancel-text="No"
                    @confirm="confirm(record.id)"
                    @cancel="cancel"
                >
                    <a href="#">删除</a>
                </a-popconfirm>

            </template>
    </a-table>
    <a-pagination
      show-size-changer
      v-model="current"
      :total="total"
      :page-size.sync="pageSize"

    />

    <a-modal :width="800" v-model="visible" title="添加用户" footer="">
        <a-input-search placeholder="账户名称" style="width: 200px;margin-bottom:20px" @search="onSearch" />
        <a-table rowKey="id" :row-selection="{ selectedRowKeys: selectedRowKeys,rowKey:'id', onChange: onSelectChange }" bordered :columns="columns" :data-source="allUser">
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
  watch: {
    pageSize(val) {
      this.pageSize=val
    },
    current(val) {
      this.current=val
    },
  },
  data () {
    return {
        visible:false,
        selectedRowKeys:[],
        allUser:[],
        current:1,
        pageSize:10,
        total:0,
        dataSource:[],
        roleId:'',
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
        url: {
          list: "stickeronline/sys/user/list",
        },

    };
  },
  computed: {},

  methods: {
      onSelectChange(){

      },
      onSearch(value){
         getAction('stickeronline/sys/user/list?username='+value).then(res=>{
          this.allUser=res.result.content
      })
      },
      add(id){
          let params={
            roleId:this.roleId,
            userId:[id]
        }
          postAction('stickeronline/sys/user/addSysUserRole',params).then(res=>{
              if(res.success){
                  this.$message.success(res.result)
                  this.selectedRowKeys.push(id)
                  this.getData(this.roleId)
              }
          })
      },
      addUser(){
            this.visible=true
      },
      isInArray(arr,value){
        for(var i = 0; i < arr.length; i++){
            if(value === arr[i]){
                return true;
            }
        }
        return false;
        },
      cancel(){
          //do nothing
      },
      confirm(id){//删除
        let params={
            roleId:this.roleId,
            userId:id
        }
        deleteAction('stickeronline/sys/user/deleteUserRoleBatch',params).then(res=>{
              if(res.success){
                  this.$message.success("操作成功！")
                  this.getData(this.roleId)
              }else{
                  this.$message.warning(res.result)
              }
            })
      },
    getData(id){
        this.roleId=id
        getAction('stickeronline/sys/user/getUserByRole?roleId='+id+'&pageNo='+this.current+'&pageSize='+this.pageSize).then(res=>{
            this.dataSource=res.result.content
            this.total=res.result.totalElements
            this.selectedRowKeys=this.dataSource.map(item=>{
                    return item.id
                })
            })
        
    }
  },

  watch: {},

  mounted(){
      this.onSearch('')
  },

  created(){},

}

</script>
<style lang='scss' >
.userAvatar{
    width: 40px;
    height: 40px;
    border-radius: 50px;
    margin-right: 10px;
}

</style>