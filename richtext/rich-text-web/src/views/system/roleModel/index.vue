<template>
  <a-card :bordered='false' style="height: calc( 100% - 20px)">
    <a-row>
      <a-col :span="selectedRowKeys.length>0?12:24">
      <!-- 查询区域 -->
        <div class="table-page-search-wrapper">
          <!-- 搜索区域 -->
          <a-form layout="inline">
            <a-row :gutter="24">
              <a-col :md="8" :sm="8">
                <a-form-item label="角色名称" :labelCol="{span: 5}" :wrapperCol="{span: 10, offset: 1}">
                  <a-input placeholder="输入角色名称" v-model="queryParam.roleName"></a-input>
                </a-form-item>
              </a-col>
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
            <a-col :md="12" :sm="24">
               <a-button type="primary" @click="searchQuery" icon="search" style="margin-left: 21px">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </a-col>
          </span>
            </a-row>
          </a-form>
        </div>
    <!-- 操作按钮区域 -->
        <div class="table-operator" style="">
            <a-button @click="handleAdd" type="primary" icon="plus">角色录入</a-button>
            <a-button v-if="selectedRowKeys.length > 0" @click="selectedRowKeys=[]" icon="minus">取消选择</a-button>
        </div>
          <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{selectedRowKeys: selectedRowKeys,type:'radio', onChange: onSelectChange}"
          @change="handleTableChange">
          <span slot="action" slot-scope="text, record">
            <a @click="handleEdit(record)">编辑</a>
            <a-divider type="vertical" />
            <a-dropdown>
              <a class="ant-dropdown-link">
                更多 <a-icon type="down" />
              </a>
              <a-menu slot="overlay">
                <a-menu-item>
                  <a @click="onSelectChange([record.id],null)">用户</a>
                </a-menu-item>
                <a-menu-item>
                  <a @click="handlePerssion(record.id)">授权</a>
                </a-menu-item>
                <a-menu-item>
                  <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                    <a>删除</a>
                  </a-popconfirm>
                </a-menu-item>
              </a-menu>
            </a-dropdown>
          </span>
      </a-table>
        </a-col>
        <a-col :span="selectedRowKeys.length>0?11:0">
         <roleUser ref="roleUser"></roleUser>
        </a-col>
      </a-row>
        
    <roleModel ref="modalForm"  @close="loadData()"></roleModel>
    <roleMenu ref="roleMenu"></roleMenu>
  </a-card>
</template>

<script>
import roleModel from './roleModel'
import roleUser from './roleUser'
import roleMenu from './roleMenu'
import {StickerListMixin} from '@/mixins/StickerListMixin'
export default {
  mixins: [StickerListMixin],
  components: {
      roleModel,
      roleUser,
      roleMenu
  },
  data () {
    return {
        columns:[
          {
            title: '角色名称',
            align:"center",
            dataIndex: 'roleName'
          },
          {
            title: '角色编码',
            align:"center",
            dataIndex: 'roleCode'
          },
          {
            title: '备注',
            align:"center",
            dataIndex: 'description'
          },
          {
            title: '创建时间',
            dataIndex: 'createTime',
            align:"center",
            sorter: true
          },
          {
            title: '更新时间',
            dataIndex: 'updateTime',
            align:"center",
            sorter: true
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "stickeronline/sys/role/list",
          delete: "stickeronline/sys/role/delete",
          deleteBatch: "stickeronline/sys/role/deleteBatch",
        },
        selectedRowKeys:[]
    };
  },
  computed: {},

  methods: {
    handlePerssion(id){
        this.$refs.roleMenu.open(id)
    },
      handleMenuClick(e) {
          this.batchDel();
      },
      roleUser(roleId){
          this.$refs.roleUser.open()
      },
      onSelectChange(a,b){
        this.selectedRowKeys=a
        this.$refs.roleUser.getData(a[0])
      },

  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>