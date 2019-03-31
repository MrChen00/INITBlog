import request from '@/utils/request'

// 阿里云OSS 文件上传操作

// 文件删除
export function deleteFile(fileUrl){
    return request({
        url: '/aliyunoss/delete',
        method: 'post',
        params: {
            fileUrl: fileUrl
        }
    })
}

// 文件修改
export function updateFile(files){
    return request({
        url: '/aliyunoss/updateUrl',
        method: 'post',
        data: files
    })
}

// 文件上传
export function uploadFile(file){
    return request({
        url: '/aliyunoss/upload',
        method: 'post',
        data: file,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

// 文件暂存
export function multipartFileAdd(file){
    return request({
        url: '/multipartFileTS/add',
        method: 'post',
        data: file,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

// 文件暂存删除
export function multipartFileDelete(file){
    return request({
        url: '/multipartFileTS/delete',
        method: 'post',
        data: file,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}


