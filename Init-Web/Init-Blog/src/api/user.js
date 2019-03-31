import request from '@/utils/request'

// 用户操作

// 添加用户基本信息
export function addProfile(id){
    return request({
        url: '/user/profile/add',
        method: 'post',
        params: {
            id
        }
    })
}

// 修改
export function updateProfile(proFile){
    return request({
        url: '/user/profile/update',
        method: 'post',
        data: proFile
    })
}

// 获取
export function getProfile(uid){
    return request({
        url: '/user/profile/get',
        method: 'post',
        params: {
            uid
        }
    })
}

// 修改信息
export function updateBasic(user){
    return request({
        url: '/user/updateBasic',
        method: 'post',
        data: user
    })
}

// 上传头像
export function uploadTox(file){
    return request({
        url: '/user/uploadHportrait',
        method: 'post',
        data: file,
        headers: { 'Content-Type': 'multipart/form-data' }
    })
}

