<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
    
    <!-- 操作按钮区域 -->
    <div class="table-operator" style="border-top: 5px">
      <a-button @click="handleAdd" type="primary" icon="plus">优秀校友</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay" @click="handleMenuClick">
          <a-menu-item key="1">
            <a-icon type="delete" @click="batchDel"/>删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">批量操作<a-icon type="down"/></a-button>
      </a-dropdown>
    </div>
    <!-- table区域-end -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>已选择&nbsp;<a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项&nbsp;&nbsp;
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>
      <a-table
        ref="table"
        bordered
        size="middle"
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
        <template slot="avatarslot" slot-scope="text, record, index">
            <a-avatar :src="record.avatar" icon="user"/>
        </template>
        <template slot="status" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <span v-if="record.status==1">已审核</span>
            <span v-else-if="record.status==-1">审核未通过</span>
            <span v-else>待审核</span>
          </div>
        </template>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
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
    
    <!-- 用户Model -->
    <goodMemberModel ref="modalForm" @close="loadData()"></goodMemberModel>


  </a-card>
</template>

<script>
  import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
  import {StickerListMixin} from '@/mixins/StickerListMixin'
  import goodMemberModel from './GoodMemberModel.vue'

  export default {
    name: "UserList",
    mixins: [StickerListMixin],
    components: {
      goodMemberModel
    },
    data() {
      return {
        description: '优秀校友',
        queryParam: {},
        url: {
          list: "stickeronline/member/list",
          delete:'stickeronline/member/delete',
          deleteBatch:'stickeronline/member/deleteBatch'
        },
        columns: [
          {ellipsis: true,title: '姓名',align: "center",dataIndex: 'name',width: 120},
          {ellipsis: true,title: '性别',align: "center",width: 100,dataIndex: 'sex'},
          {ellipsis: true,title: '联系方式',align: "center",width: 180,dataIndex: 'contact'},
          {ellipsis: true,title: '校友简介',align: "center",width: 180,dataIndex: 'desc'},
          {ellipsis: true,title: '发布人',align: "center",width: 100,dataIndex: 'createBy',},
          {ellipsis: true,title: '发布时间',align: "center",width: 180,dataIndex: 'createTime'},
          {ellipsis: true,title: '操作',dataIndex: 'action',scopedSlots: {customRender: 'action'},align: "center",width: 170}
        ],
      }
    },
    computed: {
    },
    methods: {
      handleMenuClick(e) {
        if (e.key == 1) {
          this.batchDel();
        } else if (e.key == 2) {
          this.batchFrozen(2);
        } else if (e.key == 3) {
          this.batchFrozen(1);
        }
      },
    }

  }
</script>
<style scoped>

</style>