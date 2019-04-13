import request from '@/utils/request'

export function query(queryName, type, page, size){
    return request({
        url: '/search/query',
        method: 'post',
        params: {
            queryName,
            type,
            page,
            size
        }
    })
}