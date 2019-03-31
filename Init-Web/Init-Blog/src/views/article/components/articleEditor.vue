// 文章编辑器
<template>
    <div>
        <mavon-editor :ishljs="true"
            v-model="content" 
            @imgAdd="$imgAdd" 
            @imgDel="$imgDel"
            ref="mavonRef"
            @change="showEdit" style="height: 500px"/>
            <!-- 文章保存的方法  @save="showEdit" -->
    </div>
</template>

<script>

import { callbackify } from 'util'
import { minor } from 'semver'
import axios from 'axios'

import { uploadFile, deleteFile, multipartFileAdd } from '@/api/upload'
import { getImg, updateContent } from '@/api/article'
 
export default {
    name: 'articleEditor',
    data(){
        return {
             img_file: [],
             urls: [],
             content: '',
             mavonRef: '',
             value:'',
             html:''
        }
    },
    props: ['content', 'folder'],
    methods: {
         // 添加图片
        $imgAdd(pos, $file) {
            this.img_file.push({
                pos: pos,
                file: $file
            })

            // 缓存图片信息
            // var map = new FormData()
            // map.append('file', $file)
            
            // // 文件路径
            // multipartFileAdd(map).then((resp) => {
            //     console.log(resp)
            // })

            // 上传
            // uploadFile(map).then((urls) => {
            //     var data = urls.data;
            //     if(data.code == 20000){
            //         // 保存图片信息
            //         this.img_file.push({pos: pos, file: data.data})
            //         console.log(this.img_file)
            //         this.$refs.mavonRef.$img2Url(pos, data.data)
            //     }else{
            //         console.log("上传失败");
            //     }  
            // })
            
            // 这里使用文件上传
            // this.$refs.mavonRef.$img2Url(pos, "https://init-blog-oss.oss-cn-beijing.aliyuncs.com/init-blog/image/user/hportrait.jpg")
            // console.log(this.$refs.mavonRef)
            
        },
        // 删除图片
        $imgDel(pos){
            // 删除
            // 过滤 当条件为假时, 去除掉这条数据
            // this.img_file.forEach(function(item, index){
            //     if(item.pos === pos[1]){
            //         deleteFile(item.file).then(response => {
            //             console.log(response)
            //             this.img_file.splice(item, 1)
            //         })
            //     }
            // })

            this.img_file = this.img_file.filter(function(item, index){
                // pos[1]才是索引
                // console.log(pos[1])
                return item.pos != pos[1];
            })
        },

        showEdit(value, html){
            // console.log(value)
            // 调用父组件方法
            // console.log(value)
            // console.log(html)
            // getImg(html).then((resp) => {
            //     console.log(resp)
            // })
            this.value = value
            this.html = html
            this.$emit('func', value, html)
        }
    },
    watch: {
        folder: function(val, newVal){
           if(val != ''){
                // 判断是否有图片上传
                if(JSON.stringify(this.img_file)!='{}'){
                    // 多文件上传
                    // 数据对象
                    var map = new FormData();
                    this.img_file.forEach(function(item, index){
                        // 追加数据
                        map.append(item.pos, item.file);
                    })
                    // map.append('folder', '/image/article/' + val + '/')
                    // 上传文章内的图片
                    axios({
                            // 上线配置
                            url: 'http://39.107.82.119:8018/initblog/aliyunoss/uploadOne',
                            // url: 'http://127.0.0.1:8018/initblog/aliyunoss/uploadOne',
                            method: 'post',
                            data: map,
                            headers: { 'Content-Type': 'multipart/form-data' }
                    }).then((urls) => {
                        var data = urls.data;
                        if(data.code == 20000){
                            console.log("图片上传")
                            // 注意循环这里, 严格使用 item => 这样的语法, 不然会获取不到 vm 实例(this)
                            // 请使用es6语法，把function(item){} 改为 （item）=>{} this就会指向vue了，
                            // https://ask.csdn.net/questions/693345?utm_source=debugrun&utm_medium=referral
                            data.data.forEach(item => {
                                this.$refs.mavonRef.$img2Url(item.key, item.url)
                            })
                            var list = [
                                val, this.value, this.html
                            ]
                            // 修改上传文件之后的内容
                            updateContent(list).then(resp => {
                                console.log("修改文章内容")
                                this.$message({
                                    showClose: true,
                                    message: '文章发布成功!',
                                    type: 'success'
                                })
                                // 发布成功
                                this.$router.push("/user/article")
                            })
                        }else{
                            console.log("上传失败");
                        }  
                    })          
                }else{
                    console.log("没有图片直接保存")
                    this.$message({
                            showClose: true,
                            message: '文章发布成功!',
                            type: 'success'
                    })
                    // 发布成功
                    this.$router.push("/user/article")
                }
           }
           
        }
    }
}
</script>

<style lang="scss" scope>

</style>

