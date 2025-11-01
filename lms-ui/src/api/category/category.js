import request from "@/network/request";
import { commonPath } from "./requestPath";
export const categoryPage = async (pageQuery) => {
    const { pageNum, pageSize, categoriesDto } = pageQuery;

    return await request({
        method: "get",
        url: `${commonPath}/page/${pageNum}/${pageSize}`,
        params: categoriesDto  // ✅ axios 会自动将其转为查询参数
    });
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
export const categoryDelete = async(ids) => {
    return await request({
        method: "delete",
        url: `${commonPath}/delete/${ids}`
    })
}