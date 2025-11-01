import request from "@/network/request";
const baseUrl = "/admin/orders";
export const ordersPage = async (pageQuery) => {
  return request({
    url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
    method: "get",
    params: pageQuery.ordersDto,
  });
}
export const ordersDelete = async (ids) => {
    return request({
        url: `${baseUrl}/delete/${ids}`,
        method: "post",
        data: ids
    })
}
export const ordersUpdate = async (order) => {
    return request({
        url: `${baseUrl}/update`,
        method: "put",
        data: order
    })
}
export const ordersAdd = async (order) => {
    return request({
        url: `${baseUrl}/add`,
        method: "post",
        data: order
    })
}