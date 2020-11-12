<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
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
          <!-- <a @click="handleEdit(record)">编辑</a> -->
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <!-- <a-menu-item>
                <a href="javascript:;" @click="handleDetail(record)">详情</a>
              </a-menu-item> -->
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
    <!-- <teachersModel ref="modalForm" @close="loadData()"></teachersModel> -->


  </a-card>
</template>

<script>
  import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
  import {StickerListMixin} from '@/mixins/StickerListMixin'
//   import teachersModel from './TeachersModel.vue'

  export default {
    name: "UserList",
    mixins: [StickerListMixin],
    components: {
    //   teachersModel
    },
    data() {
      return {
        description: '师资力量',
        queryParam: {},
        url: {
          list: "stickeronline/teachers/list",
          delete:'stickeronline/teachers/delete',
          deleteBatch:'stickeronline/teachers/deleteBatch'
        },
        columns: [
          {ellipsis: true,title: '姓名',align: "center",dataIndex: 'name',width: 120},
          {ellipsis: true,title: '性别',align: "center",width: 100,dataIndex: 'sex'},
          {ellipsis: true,title: '联系方式',align: "center",width: 180,dataIndex: 'contact'},
          {ellipsis: true,title: '职称',align: "center",width: 180,dataIndex: 'rank'},
          {ellipsis: true,title: '所属学院',align: "center",width: 180,dataIndex: 'college'},
          {ellipsis: true,title: '毕业院校',align: "center",width: 180,dataIndex: 'byyx'},
          {ellipsis: true,title: '邮箱',align: "center",width: 180,dataIndex: 'email'},
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