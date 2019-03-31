// 创建/编辑模板
<template>
    <div class="article">
        <el-form ref="articleForm" :model="articleForm" :rules="articleRules">
            <div>
                <el-row :gutter="30">
                    <el-col :sm="3" :xs="0"><br/></el-col>
                    <el-col :sm="15" :xs="24" >
                        <!-- 标题 -->
                        <transition name="el-zoom-in-top">
                            <h3 v-show="articleForm.title == ''">标题</h3>
                        </transition>
                        <transition name="el-zoom-in-center">
                            <h3 v-show="articleForm.title != ''">&nbsp;</h3>
                        </transition>
                        
                        <el-form-item prop="title">
                            <el-input placeholder="请输入您的文章标题." 
                                style="font-size: 26px" v-model="articleForm.title" class="el-input__inners" ></el-input>
                        </el-form-item>
                        <!-- 摘要 -->
                        <h4>摘要</h4>
                        <el-form-item prop="contentShort">
                            <el-input placeholder="文章摘要" v-model="articleForm.contentShort" size="small" class="el-input__inners"  ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :sm="5" :xs="24" >
                        <!-- 封面 http://127.0.0.1:8018/initblog/aliyunoss/updatess' :
                                        http://127.0.0.1:8018/initblog/multipartFileTS/add 
                                是先保存在单例模式的暂存库中, 之后获取文章URL时, 再进行上传 -->
                        <h3>封面</h3>
                        <el-upload   
                            :action="uploadAction"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-remove="handleRemove"
                            :on-exceed="handleExceed"
                            :before-remove="beforeRemove"
                            :on-success="uploadSuccess"
                            :limit="1"
                            ref="mult"
                            :file-list="fileList2"
                            >
                            <i class="el-icon-plus" ></i>
                        </el-upload>
                            <el-dialog :visible.sync="dialogVisible" >
                                <img width="100%" :src="dialogImageUrl" alt="">
                            </el-dialog>
                    </el-col>

            </el-row>

            <el-row >
                <el-col :sm="3" :xs="0"><br/></el-col>
                <el-col :sm="18" :xs="24" >
                    <!-- 内容 -->
                    <el-form-item prop="articleForm.content" label=""> 
                        <!-- 子组件调用父方法 
                            @func 文章内容获取
                            :folder 文章文件夹
                        -->
                        <articleEditor @func="showEdit" :folder="folderUrl" :content="articleForm.contentMd" ></articleEditor>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :sm="3" :xs="0"><br/></el-col>
                <el-col :sm="9" :xs="12" >
                    <div>
                        <!-- 分类 -->
                        <h3>分类: </h3>
                        <el-select v-model="articleForm.tid" placeholder="请选择分类">
                            <el-option v-for="(type, i) in articleType" :key="i" :label="type.name" :value="type.id"></el-option>
                        </el-select>
                        <el-button @click="ArticleDialogVisible = true">添加分类</el-button>
                    </div>
                </el-col>
                <el-col :sm="9" :xs="12">
                    <div>
                        <!-- 标签 -->
                        <h3>标签</h3>
                        <!-- 记得加上内置标签
                        <el-tag>
                        </el-tag> -->
                        <el-form-item
                            v-for="(tag, index) in articleForm.tags"
                            :key="tag.key"
                            :prop="'tags.'+ index +'.value'"
                            :rules="{
                                required: true, message: '标签不能为空', trigger: 'blur'
                            }">
                            
                            <el-input placeholder="文章标签" v-model="tag.value"  size="mini" style="width:100px;"></el-input>
                            <el-button @click.prevent="deleteTag(tag)" class="el-icon-close" size="mini"></el-button>
                            <el-button @click="addTag" size="mini" icon="el-icon-plus" ></el-button>
                        </el-form-item>
                    </div>
                </el-col>
            </el-row>

            <el-row :gutter="20" style="margin-bottom: 50px;">
                    <el-col :sm="3" :xs="0"><br/></el-col>
                    <el-col :sm="9" :xs="12">
                        <h3>文章状态:</h3>  
                        <el-select v-model="articleForm.status" >
                            <el-option label="发布" value="1">发布</el-option>
                            <el-option label="草稿" value="2">草稿</el-option>
                            <el-option label="私密" value="0">私密</el-option>
                        </el-select>
                    </el-col>
                    <el-col :sm="9" :xs="12" >
                        <div v-if="!isEdit" style="margin-top: 50px">
                            <el-form-item>
                                <el-button type="primary" @click="submitForm" style="width: 100px" >
                                <i class="el-icon-upload2"></i>发布</el-button>
                                <el-button >重置</el-button>
                            </el-form-item>
                        </div>
                        <div v-else>
                            <el-form-item>
                                <el-button type="primary" @click="submitForm">修改</el-button>
                            </el-form-item>
                        </div>
                    </el-col>
                </el-row>

                <!-- 添加分类 -->
                <el-dialog title="文章分类" :visible.sync="ArticleDialogVisible">
                    <el-form>
                        <el-form-item label="分类名称" >
                            <el-input auto-complete="off" v-model="typeName" ></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="ArticleDialogVisible = false">取 消</el-button>
                        <el-button @click="addArticleType" type="primary">确 定</el-button>
                    </div>
                </el-dialog>
                
            </div>
        </el-form>    
    </div>
</template>

<script>
import { callbackify } from 'util'
import { minor } from 'semver'

// 后台操作API
import { listType, addType, get, getTag, update, updateTag } from '@/api/article'
import { add, addTag } from '@/api/article'
import { deleteFile, multipartFileAdd, updateFile, multipartFileDelete } from  '@/api/upload'

// 文章编辑器
import articleEditor from './articleEditor'

export default {
    name: 'ArticleDetail',
    data(){
        // 标题验证
        var checkTitle = (rule, value, callback) => {
            if(value === ''){
                callback(new Error("请输入标题"));
            } else {
                callback();
            }
        };
        // 摘要验证
        var checkContentShort = (rule, value, callback) => {
            if(value === ''){
                callback(new Error("请输入摘要"));
            } else {
                callback();
            }
        }

        return {
            // 上线设置
            uploadAction: 'http://39.107.82.119:8018/initblog/multipartFileTS/add',
            // uploadAction: 'http://127.0.0.1:8018/initblog/multipartFileTS/add',
            // 文章文件夹
            folderUrl: '',
            fileList2: [],
            // 添加分类组件及值
            typeName: '',
            ArticleDialogVisible: false,
            // 所有分类
            articleType: [],
            // 文章表单
            articleForm: {
                id: '',
                uid: '',
                title: '',
                contentShort: '',
                content: '请输入您的文章内容.',
                contentMd: '## 请输入您的文章内容.',
                cover: '',
                tid: '',
                status: '',
                tags: [{
                    
                }]
            },
            // 封面
            dialogImageUrl: '',
            dialogVisible: false,

            // 表单验证注册
            articleRules: {
                title: [
                    { validator: checkTitle, trigger: 'blur', request: true}
                ],
                contentShort: [
                    { validator: checkContentShort, trigger: 'blur' }
                ]
            }

        }
    },
    methods: {
        // 删除上传文件
        handleRemove(file, fileList){
            // console.log(file)
        },
        // 点击文件列表中已上传的文件时
        handlePictureCardPreview(file){
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        // 文件超出个数限制时
        handleExceed(files, fileList){
            this.$message.warning("只能上传一个封面");
        },
        // 删除之前
        beforeRemove(file, fileList) {
            return this.$confirm(`确认移除 ${ file.name }?`, '提示', {
                confirmButtonTest: '确认',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 删除文件 判断是否编辑
                if(this.isEdit){
                    // 根据图片URL删除
                    deleteFile(file.url).then(response => {
                        // 清空文件列表
                        this.fileList2 = []
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                    })
                }else{
                    var map = new FormData();
                    map.append('file', file.raw)
                    // 由于创建时, 封面在暂存区, 所以这样删除
                    multipartFileDelete(map).then(resp => {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                    })
                }
                
            })
        },
        // 上传成功
        uploadSuccess(response, file, fileList){
            // 判断是否编辑状态执行不同操作
            if(this.isEdit){
                var map = new FormData();
                map.append('file', file.raw)
                map.append('url', this.articleForm.id)
                updateFile(map).then(resp => {
                    // 上传图片的url
                    this.articleForm.cover = resp.data.data
                })                
            }else{
                this.articleForm.cover = response.data
            }
        },
        // 获取文章分类
        setArticleType(){
            listType(this.$store.getters.user).then((result) => {
                if(result.data.code === 20000){
                    this.articleType = result.data.data
                }
            })
        },
        // 添加文章分类
        addArticleType(){
            addType(this.$store.getters.user,this.typeName).then((result) => {
                if(result.data.code == 20000){
                    // 成功刷新
                    this.setArticleType()
                    this.ArticleDialogVisible = false
                    this.typeName = ''
                    this.$message({
                        showClose: true,
                        message: '创建成功 !',
                        type: 'error'
                    })
                }else{
                    this.$message({
                        showClose: true,
                        message: '创建错误, 请稍后重试 !',
                        type: 'error'
                    })
                }
            })
        },
        // 添加标签
        addTag() {
            // console.log(this)
            // 过滤为空的标签
            this.articleForm.tags = this.articleForm.tags.filter(function(item, index, arr){
                return item.value != ''
            })
            // 添加
            this.articleForm.tags.push({
                value: '',
                key: Date.now()
            }); 
        },
        // 删除标签
        deleteTag(item) {
            var index = this.articleForm.tags.indexOf(item)
            if(index !== -1){
                this.articleForm.tags.splice(index, 1)
            }
        },
        // 父子组件传值
        showEdit(value, html){
            this.articleForm.contentMd = value;
            this.articleForm.content = html;
            // this.articleForm.content_md = md
            // console.log(this.articleForm)
        },
        // 创建文章
        createArticle(tags){   
            // 图片的原因, 我们只能先去除文章内容
            let articleFromAdd = JSON.parse(JSON.stringify(this.articleForm));
            articleFromAdd.content = "Content"
            articleFromAdd.contentMd = "ContentMd"
            articleFromAdd.cover = 'Cover'
            console.log(articleFromAdd)
            add(articleFromAdd).then(response => {
                var data = response.data
                    if(data.code === 20000){
                        console.log("保存文章中")
                        // 获取文章文件夹
                        this.folderUrl = data.data.id
                        // 获取标签
                            var tag = {
                                bid: data.data.id,
                                name: tags,
                                status: this.articleForm.status
                            }
                            // 添加标签
                            addTag(tag).then(result => {
                                if(result.data.code == 20000){
                                    console.log("标签保存")
                                }
                            }) 
                    }else{
                        this.$message({
                            showClose: true,
                            message: '文章发布错误, 请稍后重试 !',
                            type: 'error'
                        })
                }
            })
        },
        // 修改文章
        updateArticle(article, tags){
            article.tag = tags
            update(article).then(resp => {
                var data = resp.data;
                if(data.code == 20000){
                    // 返回上一页
                    this.$router.go(-1)
                    this.$message({
                        showClose: true,
                        message: '文章修改成功!',
                        type: 'success'
                    })
                }else{
                    // 文章异常
                    this.$router.go(-1)
                    this.$message({
                        showClose: true,
                        message: '文章修改异常, 请联系管理!',
                        type: 'success'
                    })
                }
            })
        },
        // 获取文章
        getArticle(){
            // 设置封面上传地址
            // this.$refs.mult.action = "http://127.0.0.1:8018/initblog/aliyunoss/updatess"
            if(this.isEdit){
                // 上线地址
                this.uploadAction = "http://39.107.82.119:8018/initblog/aliyunoss/updatess"
                // this.uploadAction = "http://127.0.0.1:8018/initblog/aliyunoss/updatess"
            }
            get(this.$route.params.id).then(resp => {
                // 判断文章是否存在
                if(resp.data.data == null){
                    this.$router.push('/error/article');
                    return;
                }
                // 设置获取的数据
                this.articleForm = resp.data.data
                // 设置封面图片
                this.fileList2 = [{ name: "defaultCover.jpg", url: this.articleForm.cover }]
                // 由于文章内容太大, 则 then 等待执行在执行 tag 赋值
            }).then(() => {
                getTag(this.$route.params.id).then(resps => {
                    var temporary = [];
                    // 给对象设置属性及值
                    this.$set(this.articleForm, 'tags' , [])
                    resps.data.data.forEach(function(item, index){
                        temporary.push({ "value": item, "key": index })
                    })
                    this.articleForm.tags = temporary
                    // 对象浅拷贝
                    // this.articleForm = Object.assign({}, this.articleForm)
                })
            })
        },
         // 文章 发布/草稿/私密
        submitForm() {
            this.$refs.articleForm.validate((valid) => {
                if(valid){
                    // 后台请求
                    // 标签必须有一个
                    var tags = this.articleForm.tags[0].value
                    this.articleForm.tags.forEach(function(item, index){
                        if(index != 0){
                            tags += "," + item.value
                        }
                    })
                    // 标签之后再传递
                    this.articleForm.uid = this.$store.getters.user
                    
                    // 创建文章
                    if(!this.isEdit){
                        this.createArticle(tags)
                    }else{
                        this.updateArticle(this.articleForm, tags)
                    }                    
                }else{
                    this.$message({
                        showClose: true,
                        message: '请填写必要项',
                        type: 'error'
                    })
                    return false;
                }
            })
        }

    },
    // 父组件向子组件传值
    props: {
        // 设置值类型/默认值
        isEdit: {
            type: Boolean,
            default: false
        }
    },
    // 注册组件
    components: {
        articleEditor
    },
    // 初始化
    mounted() {
        // 分类
        this.setArticleType()

        // 编辑状态加载 标签
        if(this.isEdit){
            this.getArticle()
        }
    }   
}
</script>

<style lang="scss">
// 重写文本框样式
.el-input__inners{
    .el-input__inner{
        border:0px;
        border-bottom: 1px solid #dcdfe6;
        border-radius: 0px;
        padding:0px;
    }
}

</style>