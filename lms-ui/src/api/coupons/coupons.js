import request from "@/network/request"
import { commonPath } from "./requestPath"
export const couponsPageQuery = async(pageNum,pageSize) => {
    return await request({
        method: 'GET',
        url: `${commonPath}/page/${pageNum}/${pageSize}`
    })
}
export const searchCoupons = async(coupons) => {
    return await request({
        method: 'get',
        url: `${commonPath}/search`,
        data: coupons
    })
}
export const couponsAdd = async(coupons) => {
    return await request({
        method: 'POST',
        url: `${commonPath}/add`,
        data: coupons
    })
}
export const couponsDelete = async(ids) => {
    return await request({
        method: 'DELETE',
        url: `${commonPath}/delete`,
        data: ids
    })
}
export const couponsUpdate = async(coupons) => {
    return await request({
        method: 'PUT',
        url: `${commonPath}/update`,
        data: coupons
    })
}