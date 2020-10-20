<template>
  <a-card :bordered="false" style="height: calc( 100% - 20px)">
  <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="12">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>
    <!-- 操作按钮区域 -->
    <div class="table-operator" style="border-top: 5px">
      <a-button @click="handleAdd" type="primary" icon="plus">添加记录</a-button>
      <a-button v-if="selectedRowKeys.length > 0" style="margin-left: 8px" @click="batchDel"><a-icon type="delete"/>批量删除</a-button>
    </div>

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
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>，
          <a @click="handleDelete(record)">删除</a>
        </span>
      </a-table>
    </div>
    
    <textModel ref="modalForm" @close="loadData()"></textModel>
  </a-card>
</template>

<script>
import {StickerListMixin} from '@/mixins/StickerListMixin'
import textModel from './textModel'
import { deleteAction } from '@/api/manage.js';
export default {
  components: {
    textModel
  },
  mixins: [StickerListMixin],
  data () {
    return {
        queryParam:{
            name:''
        },
        url: {
          list: "rich/text/list",
          deleteBatch:'rich/text/del'
        },
        columns: [
          {ellipsis: true,title: '标题',align: "center",width: 80,dataIndex: 'name' },
          {ellipsis: true,title: '内容',align: "center", width: 180,dataIndex: 'content'},
          {ellipsis: true,title: '创建时间',align: "center", width: 80,dataIndex: 'createTime'},
          {ellipsis: true,title: '操作',dataIndex: 'action',scopedSlots: {customRender: 'action'},align: "center",width: 100}
        ],
    };
  },
  computed: {},

  methods: {
    handleDelete(record){
      let that=this
      this.$confirm({
          title: "确认删除",
          content: "是否删除选中数据?",
          onOk: function() {
              deleteAction(that.url.deleteBatch, { ids: record.id }).then((res) => {
                if (res.success) {
                that.$message.success(res.result)
                that.loadData()
                } else {
                    that.$message.warning(res.result);
                }
            });
          }
      });
    }
  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>