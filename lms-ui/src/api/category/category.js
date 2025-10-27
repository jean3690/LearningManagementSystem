import request from "@/network/request";
import { commonPath } from "./requestPath";
export const categoryPage = async(pageQuery) => {
    return await request({
        method: "get",
        url: `${commonPath}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        data: pageQuery.category
    })
}
export const categoryAdd = async(category) => {
    return await request({
        method: "post",
        url: `${commonPath}/add`,
        data: category
    })
}
export const categoryUpdate = async(category) => {
    return await request({
        method: "put",
        url: `${commonPath}/update`,
        data: category
    })
}
export const categoryDelete = async(id) => {
    return await request({
        method: "delete",
        url: `${commonPath}/delete/${id}`
    })
}