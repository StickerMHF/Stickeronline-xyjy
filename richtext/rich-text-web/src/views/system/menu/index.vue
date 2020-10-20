<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button
        @click="batchDel"
        v-if="selectedRowKeys.length > 0"
        ghost
        type="primary"
        icon="delete">批量删除
      </a-button>
    </div>

    <!-- table区域-begin -->
    <div>

      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>已选择&nbsp;<a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项&nbsp;&nbsp;
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
     <a-table
        :columns="columns"
        size="middle"
        :pagination="false"
        :dataSource="dataSource"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @expandedRowsChange="handleExpandedRowsChange">
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" @click="handleAddSub(record)">添加子菜单</a>
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

    </div>

  <menuModal ref="menuModal" @close="loadData"></menuModal>
  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import menuModal from './menuModal.vue'
  import {getAction,postAction,putAction} from '@/api/manage.js'
  const columns = [
    {
      title: '菜单名称',
      dataIndex: 'name',
      key: 'name'
    }, {
      title: '菜单类型',
      dataIndex: 'menuType',
      key: 'menuType',
      customRender: function(text) {
        if (text == 0) {
          return '菜单'
        } else if (text == 1) {
          return '菜单'
        } else if (text == 3) {
          return '子系统'
        } else {
          return text
        }
      }
    },{
      title: 'icon',
      dataIndex: 'icon',
      key: 'icon'
    },
    {
      title: '组件',
      dataIndex: 'component',
      key: 'component',
      scopedSlots: { customRender: 'component' }
    },
    {
      title: '路径',
      dataIndex: 'url',
      key: 'url',
      scopedSlots: { customRender: 'url' }
    },
    {
      title: '排序',
      dataIndex: 'sortNo',
      key: 'sortNo'
    },
    {
      title: '操作',
      dataIndex: 'action',
      scopedSlots: { customRender: 'action' },
      align: 'center',
      width: 150
    }
  ]

export default {
  components: {
    menuModal
  },
  mixins: [JeecgListMixin],
  data () {
    return {
      description: '这是菜单管理页面',
      // 表头
      columns: columns,
      loading: false,
      // 展开的行，受控属性
      expandedRowKeys: [],
      url: {
          list: 'stickeronline/sys/permission/list',
          delete: 'stickeronline/sys/permission/delete',
          deleteBatch: 'stickeronline/sys/permission/deleteBatch'
        }

    };
  },
  computed: {},

  methods: {
    loadData() {
        this.dataSource = []
        getAction('stickeronline/sys/permission/list').then((res) => {
          if (res.success) {
            console.log(res.result)
            this.dataSource = res.result
          }
        })
      },
    handleAdd(){
        this.$refs.menuModal.resetForm()
        this.$refs.menuModal.title = "添加子系统";
        this.$refs.menuModal.disableSubmit = false;
        this.$refs.menuModal.edit({'menuType':3});
    },
    handleAddSub(record) {
        this.$refs.menuModal.resetForm()      
        this.$refs.menuModal.title = "添加子菜单";
        this.$refs.menuModal.localMenuType = 1;
        this.$refs.menuModal.disableSubmit = false;
        this.$refs.menuModal.edit({'parentId':record.id,'menuType':1});
    },
    handleEdit(record){
        this.$refs.menuModal.resetForm()
      this.$refs.menuModal.disableSubmit = false;
      this.$refs.menuModal.title="编辑菜单";
      this.$refs.menuModal.edit(record);
    },
    handleDetail:function(record){
      this.$refs.menuModal.disableSubmit = true;
      this.$refs.menuModal.title="详情";
      this.$refs.menuModal.edit(record);
    },
    handleExpandedRowsChange(expandedRows) {
        this.expandedRowKeys = expandedRows
      },


  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>