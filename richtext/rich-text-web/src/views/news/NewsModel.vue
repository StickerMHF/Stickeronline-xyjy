<template>
      <a-modal  :title="title" :width='1100' placement="right"  v-model="visible" @ok="handleOk">
        <a-form-model ref="addForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="title" label="标题" prop="title">
                <a-input  v-model="form.title" placeholder="请输入标题"/>
            </a-form-model-item>
             <a-form-model-item ref="name" label="描述" >
                <a-input  v-model="form.description" placeholder="请输入描述"/>
                </a-form-model-item>
           <a-form-model-item ref="img" label="缩略图" >
                <a-upload
                :action="UpFileUrl"
                list-type="picture-card"
                :file-list="fileList"
                @preview="handlePreview"
                @change="handleChange">
                    <div v-if="fileList.length < 8">
                        <a-icon type="plus" />
                        <div class="ant-upload-text">
                        Upload
                        </div>
                    </div>
                </a-upload>
                <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel2">
                    <img alt="example" style="width: 100%" :src="previewImage" />
                </a-modal>
                        {{commend}}
            </a-form-model-item>
            <a-form-model-item label="内容" prop="contents">
                <wangEditor v-model="form.contents" :isClear="true" @change="change"></wangEditor>
            </a-form-model-item>
        </a-form-model>
    </a-modal>
</template>

<script>
import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
import { mapActions, mapGetters } from 'vuex'
import wangEditor from '@/components/sticker/wangEditor/index';//验证码
import { axios ,getFileUrl} from '@/utils/request'
export default {
  components: {
      wangEditor
  },
  data () {
    return {
        title:'',
        visible:false,
        UpFileUrl:getFileUrl(),
        uploadLoading:false,
        fileList:[],
        previewVisible:false,
        previewImage: '',
        commend:'注：最多展示8张图片',
        labelCol: { span: 2 },
        wrapperCol: { span: 20 },
        form: {
            contents:""
        },
        rules:{
            title:[{ required: true, message: '请输入新闻标题', trigger: 'blur' }],
             contents:[{ required: true, message: '请输入新闻内容', trigger: 'blur' }]
        }

    };
  },
  computed: {},

  methods: {
       ...mapGetters(["nickname", "avatar","userInfo"]),
      add(){
        this.visible=true
        this.form={}

      },
      edit(record){
            this.visible=true
            debugger
            this.form=record
      },
      handleOk(){
        let that =this
        this.$refs.addForm.validate(valid => {
            if (valid) {
                debugger
                that.form.updateBy=that.nickname();
                if(that.form.id){
                    putAction('stickeronline/news/edit',that.form).then(res=>{
                        if(res.success){
                            that.$message.success(res.result)
                            that.$emit("close")
                            that.form={}
                            that.visible=false
                        }else{
                            that.$message.warning(res.result)
                        }
                    })
                }else{
                    that.form.type=0;
                that.form.istop=0;
                that.form.viewCount=0;
                that.form.thumb=JSON.stringify(that.form.thumb);
                that.form.createBy=that.nickname();
                    postAction('stickeronline/news/add',that.form).then(res=>{
                        if(res.success){
                            that.$message.success(res.result)
                            that.$emit("close")
                            that.form={}
                            that.visible=false
                        }else{
                            that.$message.warning(res.result)
                        }
                    })
                }
            }
        })
      },
      //富文本编辑框
      change(value){
          this.form.contents=value
      },
      //图片上传回调
        handleChange({ fileList }) {
          this.form.thumb=[]
          for(let i=0;i<fileList.length;i++){
              if(fileList[i].response){
                  this.form.thumb.push(fileList[i].response.result[0].url)
              }
          }
        this.fileList = fileList;
      },
      //图片预览
      async handlePreview(file) {
          debugger
        this.previewImage = file.url||file.response.result[0].url ;
        this.previewVisible = true;
        },
        handleCancel2(){
          this.previewVisible=false
      }
  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>