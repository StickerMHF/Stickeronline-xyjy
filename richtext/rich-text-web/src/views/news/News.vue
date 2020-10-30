<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="12">
            <a-form-item label="ID">
              <a-input placeholder="请输入ID查询" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
           <a-form-item label="名称">
              <a-input placeholder="请输入名称查询" v-model="queryParam.username"></a-input>
            </a-form-item>
          </a-col>


          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="关键字">
                <a-input placeholder="请输入关键字查询" v-model="queryParam.email"></a-input>
              </a-form-item>
            </a-col>

            <a-col :md="6" :sm="8">
              <a-form-item label="发布人">
                <a-input placeholder="请输入发布人查询" v-model="queryParam.phone"></a-input>
              </a-form-item>
            </a-col>
           
          </template>

          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 操作按钮区域 -->
    <div class="table-operator" style="border-top: 5px">
      <a-button @click="handleAdd" type="primary" icon="plus">发布新闻</a-button>
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
        <template slot="sex" slot-scope="text, record, index">
          <div class="anty-img-wrap">
            <span v-if="record.sex==1">男</span>
            <span v-else>女</span>
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
    <newsModel ref="modalForm" @close="loadData()"></newsModel>


  </a-card>
</template>

<script>
  import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
  import {StickerListMixin} from '@/mixins/StickerListMixin'
  import newsModel from './NewsModel.vue'

  export default {
    name: "UserList",
    mixins: [StickerListMixin],
    components: {
      newsModel
    },
    data() {
      return {
        description: '用户管理',
        queryParam: {},
        url: {
          list: "stickeronline/news/list",
          delete:'stickeronline/news/delete',
          deleteBatch:'stickeronline/news/deleteBatch'
        },
        columns: [
          {ellipsis: true,title: '新闻标题',align: "center",dataIndex: 'title',width: 120},
          {ellipsis: true,title: '发布人',align: "center",width: 100,dataIndex: 'createBy',},
          {ellipsis: true,title: '头像',align: "center",width: 120,dataIndex: 'avatar',scopedSlots: {customRender: "avatarslot"}},
          {ellipsis: true,title: '发布时间',align: "center",width: 180,dataIndex: 'createTime'},
          {ellipsis: true,title: '浏览量',align: "center",width: 100,dataIndex: 'viewCount'},
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