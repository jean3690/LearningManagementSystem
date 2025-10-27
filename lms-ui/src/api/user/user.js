import request from "@/network/request"
const baseUrl = "/admin/user";

export const userPage = async (pageQuery) => {
    return request({
        url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        method: "get",
        params: pageQuery.user
    })
}
export const userDelete = async (ids) => {
    return request({
        url: `${baseUrl}/delete`,
        method: "post",
        data: ids
    })
}
export const userUpdate = async (user) => {
    return request({
        url: `${baseUrl}/update`,
        method: "put",
        data: user
    })
}
export const userAdd = async (user) => {
    return request({
        url: `${baseUrl}/add`,
        method: "post",
        data: user
    })
}