import request from '@/utils/request'

// 文章评论

// 添加评论
export function add(comment){
    return request({
        url: '/comment/add',
        method: 'post',
        data: comment
    })
}

// 获取首评论
export function getComment(bid){
    return request({
        url: '/comment/get',
        method: 'get',
        params: {
            bid
        }
    })
}

// 获取回复评论
export function getReply(pid){
    return request({
        url: '/comment/getReply',
        method: 'get',
        params: {
            pid
        }
    })
}

// 删除评论
export function deleteComment(id){
    return request({
        url: '/comment/delete',
        method: 'get',
        params: {
            id
        }
    })
}
