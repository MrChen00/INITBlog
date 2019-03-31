import request from '@/utils/request'

// 添加
export function addCollect(uid, bid){
    return request({
        url: '/collect/add',
        method: 'get',
        params: {
            uid,
            bid
        }
    })
}

// 删除
export function deletes(uid, bid){
    return request({
        url: '/collect/delete',
        method: 'get',
        params: {
            uid,
            bid
        }
    })
}

// 文章收藏数量
export function count(bid){
    return request({
        url: '/collect/count',
        method: 'get',
        params: {
            bid
        }
    })
}

// 获取收藏文章
export function getArticle(uid){
    return request({
        url: '/collect/getArticle',
        method: 'get',
        params: {
            uid
        }
    })
}