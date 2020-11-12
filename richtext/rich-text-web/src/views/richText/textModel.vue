<template>
      <a-modal  :title="title" :width='1100'  v-model="visible" @ok="handleOk">
        <a-form-model ref="addForm" :model="form" :rules="rules" :label-col="labelCol" :wrapper-col="wrapperCol" >
            <a-form-model-item ref="name" label="标题" prop="name">
                <a-input  v-model="form.name" placeholder="请输入标题"/>
            </a-form-model-item>
            <a-form-model-item ref="img" label="配图" prop="img">
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
            <a-form-model-item label="内容">
                <wangEditor v-model="form.content" :isClear="true" @change="change"></wangEditor>
            </a-form-model-item>
        </a-form-model>
    </a-modal>
</template>

<script>
import {getAction,postAction,deleteAction,putAction} from '@/api/manage';
import wangEditor from '@/components/sticker/wangEditor/index';//验证码
export default {
  components: {
      wangEditor
  },
  data () {
    return {
        title:'',
        visible:false,
        UpFileUrl:this.$getBaseUrl()+"/stickeronline/file/fileUpload",
        labelCol: { span: 2 },
        wrapperCol: { span: 20 },
        form: {
            content:""
        },
        rules:{
            name:[{ required: true, message: '请输入记录标题', trigger: 'blur' },]
        },
        fileList:[],
        previewVisible:false,
        previewImage: '',
        commend:'注：最多展示8张图片',

    };
  },
  computed: {},

  methods: {
      add(){
        this.visible=true
        this.form={}

      },
      edit(record){
            this.visible=true
            this.form=record
            for(let i=0;i<value.img.length;i++){
              this.fileList.push({
                uid: i,
                name: 'image.png',
                status: 'done',
                url: value.img[i],
              })
          }
      },
      handleOk(){
        let that =this
        this.$refs.addForm.validate(valid => {
            if (valid) {
                if(this.form.id){
                    putAction('rich/text/edit',this.form).then(res=>{
                        if(res.success){
                            this.$message.success(res.result)
                            this.$emit("close")
                            this.form={}
                            this.visible=false
                        }else{
                            this.$message.warning(res.result)
                        }
                    })
                }else{
                    postAction('rich/text/add',this.form).then(res=>{
                        if(res.success){
                            this.$message.success(res.result)
                            this.$emit("close")
                            this.form={}
                            this.visible=false
                        }else{
                            this.$message.warning(res.result)
                        }
                    })
                }
            }
        })
      },
      //图片上传回调
        handleChange({ fileList }) {
          this.form.img=[]
          for(let i=0;i<fileList.length;i++){
              if(fileList[i].response){
                  this.form.img.push(fileList[i].response.result[0].url)
              }
          }
        this.fileList = fileList;
      },
      //图片预览
      async handlePreview(file) {
        this.previewImage = file.url||file.response.result[0].url ;
        this.previewVisible = true;
        },
        handleCancel2(){
          this.previewVisible=false
      },
      //富文本编辑框
      change(value){
          this.form.content=value
      }
  },

  watch: {},

  mounted(){},

  created(){},

}

</script>
<style lang='scss' scoped>
</style>