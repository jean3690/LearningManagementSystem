import request from "@/network/request"
import { commonPath } from "./requestPath"
export const couponsPage = async (pageQuery) => { 
  return await request({
      method: "get",
      url: `${commonPath}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
      params: pageQuery.couponsDto
  })
}
export const couponsAdd = async (coupons) => {
  return await request({
      method: "post",
      url: `${commonPath}/add`,
      data: coupons
  })
}
export const couponsUpdate = async (coupons) => {
  return await request({
      method: "put",
      url: `${commonPath}/update`,
      data: coupons
  })
}
export const couponsDelete = async (ids) => {
  return await request({
      method: "post",
      url: `${commonPath}/delete`,
      data: ids
  })
}