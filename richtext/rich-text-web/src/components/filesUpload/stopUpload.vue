<template>
    <div>
        <a-button type="primary" @click="openModel">点击上传</a-button>
        
        <a-modal width="80%" v-model="visible" title="大文件上传" @cancel="cancel" @ok="handleOk">
            <uploader
      browse_button="browse_button"
      :url="$getBaseUrl()+'/stickeronline/big/file/uploadWithBlock/'"
      chunk_size="2MB"
      :filters="{prevent_duplicates:true}"
      :FilesAdded="filesAdded"
      :Error="error"
      :UploadComplete="uploadComplete"
      :BeforeUpload="beforeUpload"
      @inputUploader="inputUploader"
    />
    <a-button type="primary" id="browse_button">选择多个文件</a-button>
    <br/>
    <a-table :columns="columns" :data-source="tableData" style="width: 100%; margin: 10px 10px;">
      <span slot="status" slot-scope="text, record">
        <span v-if="text === -1">正在计算MD5</span>
          <span v-if="text === 1">MD5计算完成，准备上传</span>
          <span v-if="text=== 4" style="color: brown">上传失败</span>
          <span v-if="text === 5" style="color: chartreuse">已上传</span>
          <a-progress v-if="text === 2" :text-inside="true"  :percentage="record.percent"></a-progress>
      </span>
      <span slot="operater" slot-scope="text, record">
        <a-button type="danger" @click="deleteFile(record.id)">删除</a-button>
      </span>
    
    </a-table>
    <br/>
    <a-button :disabled="uploading"  type="danger" @click="uploadStart()">开始上传</a-button>
    <a-button :disabled="!uploading" type="warring" @click="uploadStop()">暂停上传</a-button>
        </a-modal>
    </div>
</template>

<script>
import FileMd5 from './file-md5.js'
import Uploader from './Uploader'
  export default {
    name: "filesUpload",
    components: {
    Uploader
    },
    data () {
      return {
        visible:false,

        up: {},
        files:[],
        tableData: [],
        uploading: false,
        columns:[
          {
            title: '文件名称',
            dataIndex: 'name',
            key: 'name',
          },
          {
            title: '文件大小',
            dataIndex: 'size',
            key: 'size',
          },
          {
            title: '状态',
            dataIndex: 'status',
            key: 'status',
            scopedSlots: { customRender: 'status' },
          },
          {
            title: '操作',
            dataIndex: 'operater',
            key: 'operater',
            scopedSlots: { customRender: 'operater' },
          },
        ]
      }
    },
    computed: {
      
    },
    methods: {
        openModel(){
            this.visible=true
        },
        handleOk(){
            this.visible=false
        },
        cancel(){
            this.visible=false
        },
         inputUploader(up) {
        this.up = up;
        this.files = up.files;
      },
      filesAdded(up, files) {
        files.forEach((f) => {
          f.status = -1;
          FileMd5(f.getNative(), (e, md5) => {
            f["md5"] = md5;
            f.status = 1;
          });
        });
      },
      deleteFile(id) {
        let file = this.up.getFile(id);
        this.up.removeFile(file);
      },
      beforeUpload(up, file) {
        up.setOption("multipart_params", {"size":file.size,"md5":file.md5});
      },
      uploadStart() {
        this.uploading = true;
        this.up.start();
      },
      uploadStop() {
        this.uploading = false;
        this.up.stop();
      },
      error() {
        this.uploading = false;
      },
      uploadComplete() {
        this.uploading = false;
      }
    },
    watch: {
      files: {
        handler() {
          this.tableData = [];
          this.files.forEach((e) => {
            this.tableData.push({
              name: e.name,
              size: e.size,
              status: e.status,
              id: e.id,
              percent: e.percent,
              key: e.id,
            });
          });
        },
        deep: true
      }
    },
  }
</script>

<style lang="scss">

</style>