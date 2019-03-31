import request from '@/utils/request'

// 获取消息
export function getMessage(id){
    return request({
        url: '/message/getMessage',
        method: 'get',
        params:{
            id
        }
    })
}
// 删除消息
export function deleteMessage(uid, index){
    return request({
        url: '/message/deleteMessage',
        method: 'get',
        params:{
            uid,
            index
        }
    })
}

export function getCountMessage(id){
    return request({
        url: '/message/getCountMessage',
        method: 'get',
        params:{
            id
        }
    })
}

export function updateCountMessage(id){
    return request({
        url: '/message/updateCountMessage',
        method: 'get',
        params:{
            id
        }
    })
}

export function updateStatus(id, index){
    return request({
        url: '/message/updateStatus',
        method: 'get',
        params:{
            id,
            index
        }
    })
}
