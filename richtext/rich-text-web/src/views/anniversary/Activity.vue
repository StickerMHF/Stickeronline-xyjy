<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
   
    <!-- 操作按钮区域 -->
    <div class="table-operator" style="border-top: 5px">
      <a-button @click="handleAdd" type="primary" icon="plus">发布活动</a-button>
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
    <activityModel ref="modalForm" @close="loadData()"></activityModel>


  </a-card>
</template>

<script>
  import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
  import {StickerListMixin} from '@/mixins/StickerListMixin'
  import activityModel from './ActivityModel.vue'

  export default {
    name: "UserList",
    mixins: [StickerListMixin],
    components: {
      activityModel
    },
    data() {
      return {
        description: '校庆活动',
        queryParam: {},
        url: {
          list: "stickeronline/alumnusActivity/list",
          delete:'stickeronline/alumnusActivity/delete',
          deleteBatch:'stickeronline/alumnusActivity/deleteBatch'
        },
        columns: [
          {ellipsis: true,title: '活动主题',align: "center",dataIndex: 'title'},
          {ellipsis: true,title: '活动开始时间',align: "center",dataIndex: 'startTime'},
          {ellipsis: true,title: '活动结束时间',align: "center",dataIndex: 'endTime'},
          {ellipsis: true,title: '活动地址',align: "center",dataIndex: 'address'},
          {ellipsis: true,title: '报名截止日期',align: "center",dataIndex: 'deadline'},
          {ellipsis: true,title: '发布人',align: "center",dataIndex: 'createBy',},
          {ellipsis: true,title: '发布时间',align: "center",dataIndex: 'createTime'},
          {ellipsis: true,title: '操作',dataIndex: 'action',scopedSlots: {customRender: 'action'},align: "center",width: 170}
        ],
      }
    },
    computed: {
    },
    methods: {
         loadData(arg) {
            if (!this.url.list) {
                this.$message.error("请设置url.list属性!")
                return
            }
            //加载数据 若传入参数1则加载第一页的内容
            if (arg === 1) {
                this.ipagination.current = 1;
            }
            var params = this.getQueryParams(); //查询条件
            this.loading = true;
            params.type=1;
            getAction(this.url.list, params).then((res) => {
                if (res.success) {
                        this.dataSource = res.result.content;
                        this.ipagination.total = res.result.totalElements;
                }
                this.loading = false;
            })
        },
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