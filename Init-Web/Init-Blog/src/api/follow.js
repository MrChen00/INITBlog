import request from '@/utils/request'

// 关注

export function addFollow(uid, aid){
    return request({
        url: '/follow/add',
        method: 'get',
        params: {
            uid,
            aid
        }
    })
}

// 取消关注
export function deleteFollow(uid, aid){
    return request({
        url: '/follow/delete',
        method: 'get',
        params: {
            uid,
            aid
        }
    })
}

// 关注数量
export function countFollow(uid){
    return request({
        url: '/follow/countFollow',
        method: 'get',
        params: {
            uid
        }
    })
}

// 粉丝数量
export function countLike(uid){
    return request({
        url: '/follow/countLike',
        method: 'get',
        params: {
            uid
        }
    })
}

// 是否已关注
export function checkFollow(uid, aid){
    return request({
        url: '/follow/checkFollow',
        method: 'get',
        params: {
            uid,
            aid
        }
    })
}

// 所有关注
export function listFollow(id){
    return request({
        url: '/follow/listFollow',
        method: 'get',
        params:{
            id
        }
    })
}

// 所有粉丝
export function listLike(id){
    return request({
        url: '/follow/listLike',
        method: 'get',
        params:{
            id
        }
    })
}


