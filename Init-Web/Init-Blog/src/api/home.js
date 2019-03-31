import request from '@/utils/request'

export function grabById(id){
    return request({
        url: '/users/grabById',
        method: 'post',
        params: {
            id
        }
    })
}

// 获取用户信息
export function getUser(id){
    return request({
        url: '/users/',
        method: 'get',
        params: {
            id
        }
    })
}

// 获取IT资讯文章
export function getItArticle(){
    return request({
        url: '/users/getItRedis',
        method: 'get'
    })
}

// 主页模糊查询
export function listLikeName(name){
    return request({
        url: '/users/listLikeName',
        method: 'get',
        params: {
            name
        }
    })
}

