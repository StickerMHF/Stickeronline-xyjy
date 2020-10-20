<template>
  <div style="height: calc( 100% - 20px);display: flex;">
    <!-- <a-row style="height: calc( 100% - 20px)"> -->
      <div style="width:450px;min-width:450px">
        <a-card style="height:100%">
          <span style="display: flex;justify-content: space-between;margin-bottom:10px">
            <a-button style="display:block" title="添加子部门"    @click="handleAdd(true,checkedKeys)" type="primary">添加子部门</a-button>
            <a-button style="display:block" title="添加一级部门"  @click="handleAdd(false)" type="primary">添加一级部门</a-button>
            <a-button style="display:block" title="删除多条数据" @click="batchDel" type="default">批量删除</a-button>
          </span>
          <!-- 当前选择 -->
          <a-alert type="info" :showIcon="true" >
            <div slot="message">
              当前选择：
              <a v-if="this.currSelected.title">{{ getCurrSelectedTitle() }}</a>
              <a v-if="this.currSelected.title" style="margin-left: 10px" @click="onClearSelected">取消选择</a>
            </div>
          </a-alert>
          <!-- 搜索框 -->
          <a-input-search @search="onSearch" style="width:100%;margin-top: 10px" placeholder="请输入部门名称"/>
          <!-- 树 -->
          <a-dropdown :trigger="[this.dropTrigger]" @visibleChange="dropStatus">
               <span style="user-select: none">
            <a-tree
              checkable
              multiple
              @select="onSelect"
              @check="onCheck"
              @rightClick="rightHandle"
              :selectedKeys="selectedKeys"
              :checkedKeys="checkedKeys"
              :treeData="departTree"
              :checkStrictly="true"
              :expandedKeys="iExpandedKeys"
              :autoExpandParent="autoExpandParent"
              @expand="onExpand"/>
                </span>
                <!--新增右键点击事件,和增加添加和删除功能-->
                <a-menu slot="overlay">
                  <!-- <a-menu-item @click="handleAdd(3)" key="1">添加</a-menu-item>
                  <a-menu-item @click="handleDelete" key="2">删除</a-menu-item> -->
                  <a-menu-item @click="closeDrop" key="3">取消</a-menu-item>
                </a-menu>
              </a-dropdown>
        </a-card>
      </div>
      <div style="width:100%;height: 100%;">
        <a-card>
          <departForm ref="departForm" :treeData="departTree" @close="loadData()" ></departForm>
          <departUser ref="departUser" :departId="departId" @close="loadData()" ></departUser>
        </a-card>
      </div>
    <!-- </a-row> -->
  <departModel ref="modalForm" :treeData="departTree" @close="loadData()" ></departModel>
  </div>
</template>

<script>
 import {getAction,postAction,putAction,deleteAction} from '@/api/manage.js'
 import departModel from './departModel.vue'
 import departForm from './departForm.vue'
 import departUser from './departUser.vue'

export default {
  components: {
    departModel,
    departForm,
    departUser
  },
  data () {
    return {
      currSelected: {},
      iExpandedKeys: [],
      autoExpandParent: true,
      departTree: [],
      checkedKeys: [],
      selectedKeys: [],
      dropTrigger: '',
      url: {
          deleteBatch: 'stickeronline/sysdepart/sysDepart/deleteBatch',  
          search:'stickeronline/sysdepart/sysDepart/searchBy',
          list:'stickeronline/sysdepart/sysDepart/queryTreeList'
      },
      departId:''
    };
  },
  computed: {
  },
  methods: {
    handleAdd(value,id) {
        if(value&&this.checkedKeys.length!=1){
          this.$message.warning("请勾选一级作为上级部门")
        }else{
          this.$refs.modalForm.add(value,id);
          this.$refs.modalForm.title = "新增";
        }
    },
    batchDel() {
            if (this.checkedKeys.length <= 0) {
                this.$message.warning('请选择一条记录！');
                return;
            } else {
                var ids = "";
                for (var a = 0; a < this.checkedKeys.length; a++) {
                    ids += this.checkedKeys[a] + ",";
                }
                var that = this;
                this.$confirm({
                    title: "确认删除",
                    content: "是否删除选中数据?",
                    onOk: function() {
                        deleteAction(that.url.deleteBatch, { ids: ids }).then((res) => {
                            if (res.success) {
                                that.$message.success(res.result);
                                that.loadData();
                            } else {
                                that.$message.warning(res.result);
                            }
                        });
                    }
                });
            }
        },
    onSearch(value) {
        let that = this
        if (value) {
          getAction('stickeronline/sysdepart/sysDepart/searchBy?departName='+value).then((res) => {
            if (res.success) {
              that.departTree = []
              for (let i = 0; i < res.result.length; i++) {
                let temp = res.result[i]
                that.departTree.push(temp)
              }
            } else {
              that.$message.warning(res.message)
            }
          })
        } else {
          that.loadData()
        }

      },
    rightHandle(node) {
      this.dropTrigger = 'contextmenu'
      console.log(node.node.eventKey)
      this.rightClickSelectedKey = node.node.eventKey
    },
    onCheck(checkedKeys, info) {
      console.log('onCheck', checkedKeys, info)
      this.checkedKeys = checkedKeys.checked
    },
    onSelect(selectedKeys, e) {
        let record = e.node.dataRef
        this.currSelected = Object.assign({}, record)
        this.selectedKeys = [record.key]
        this.$refs.departForm.edit(this.currSelected)
        this.departId=this.currSelected.id
    },
    // 右键店家下拉关闭下拉框
    closeDrop() {
      this.dropTrigger = ''
    },
    onExpand(expandedKeys) {
      console.log('onExpand', expandedKeys)
      this.iExpandedKeys = expandedKeys
      this.autoExpandParent = false
    },
    dropStatus(visible) {
      if (visible == false) {
        this.dropTrigger = ''
      }
    },
    loadData(){
      getAction(this.url.list).then(res=>{
        this.departTree=res.result
        this.onClearSelected()
      })
    },
    getCurrSelectedTitle() {
      return !this.currSelected.title ? '' : this.currSelected.title
    },
    onClearSelected() {
      // this.hiding = true
      this.checkedKeys = {}
      this.currSelected = {}
      this.departId=''
      this.selectedKeys = []
    },
  },

  watch: {},

  mounted(){
    this.loadData()
  },

  created(){},

}

</script>
<style lang='scss' scoped>
</style>