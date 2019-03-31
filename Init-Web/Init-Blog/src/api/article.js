import request from '@/utils/request'

// 文章信息 API

// ----------- 文章

// 添加文章
export function add(article){
    return request({
        url: '/article/add',
        method: 'post',
        data: article
    })
}

// 修改文章
export function update(article){
    return request({
        url: '/article/update',
        method: 'post',
        data: article
    })
}

// 获取单个文章
export function get(id){
    return request({
        url: '/article/get',
        method: 'post',
        params: {
            id
        }
    })
}

// 删除文章
export function remove(id){
    return request({
        url: '/article/remove',
        method: 'post',
        params: {
            id
        }
    })
}

// 修改文章内容
export function updateContent(list){
    return request({
        url: '/article/updateContent',
        method: 'post',
        data: list
    })
}
// 文章浏览量
export function updateViews(id){
    return request({
        url: '/article/updateViews',
        method: 'get',
        params: {
            id
        }
    })
}

// JSoup爬取img
export function getImg(str){
    return request({
        url: '/article/testImg',
        method: 'post',
        headers: { 'Content-Type': 'tex/html' },
        data: str
    })
}

// ------------ 分类文章

// 获取文章分类
export function listType(uid){
    return request({
        url: '/article/type/list',
        method: 'post',
        params: {
            uid
        }
    })
}
// 添加文章分类
export function addType(uid, name){
    return request({
        url: '/article/type/add',
        method: 'post',
        params: {
            uid,
            name
        }
    })
}

// 分类文章数量
export function countByTid(tid){
    return request({
        url: '/article/countByTid',
        method: 'get',
        params: {
            tid
        }
    })
}

// 分类文章
export function listByTid(tid){
    return request({
        url: '/article/listByTid',
        method: 'get',
        params: {
            tid
        }
    })
}

// ---------------- 标签 

// 添加标签
export function addTag(tag){
    return request({
        url: '/article/tag/add',
        method: 'post',
        data: tag
    })
}

// 修改
export function updateTag(tag){
    return request({
        url: '/article/tag/update',
        method: 'post',
        headers: { 'Content-Type': 'application/json' },
        data: tag
    })
}

// 文章ID 标签
export function getTag(id){
    return request({
        url: '/article/tag/getByName',
        method: 'post',
        params: {
            id
        }
    })
}

// 获取前20的标签
export function listTag(){
    return request({
        url: '/article/tag/listName',
        method: 'get'
    })
}


// 文章点赞
export function updateLike(bid){
    return request({
        url: '/article/updateLike',
        method: 'get',
        params: {
            bid
        }
    })
}



