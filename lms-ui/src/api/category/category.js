import request from "@/network/request";
import { commonPath } from "./requestPath";
export const CategorypageQuery = async (pageNum,pageSize) => {
    return await request.get(`${commonPath}/page/${pageNum}/${pageSize}`)
}
/**
 * 根据ID查询分类信息
 * @param {string|number} id - 分类ID
 * @returns {Promise} 返回一个Promise对象，包含分类信息的响应数据
 */
export const queryCategoryById = async (id) => {
    // 使用request.get方法发送GET请求获取指定ID的分类信息
    // 请求路径为基础路径commonPath与传入的id拼接而成
    return await request.get(`${commonPath}/${id}`)
}
/**
 * 添加分类的异步函数
 * @param {Object} data - 要添加的分类数据
 * @returns {Promise} - 返回一个Promise对象，包含添加分类的请求结果
 */
export const addCategory = async (data) => {
    // 使用request.post方法发送POST请求到指定路径
    // commonPath是公共路径，'/add'是添加分类的子路径
    // data作为请求体发送
    return await request.post(`${commonPath}/add`,data)
}
/**
 * 更新分类信息的异步函数
 * @param {Object} data - 包含更新分类所需的数据对象
 * @returns {Promise} - 返回一个Promise对象，包含请求的响应结果
 */
export const updateCategory = async (data) => {
    // 使用request发送PUT请求到指定路径，并传入数据
    return await request.put(`${commonPath}/update`,data)
}
/**
 * 删除分类的异步函数
 * @param {string|number} id - 要删除的分类ID
 * @returns {Promise} 返回一个Promise对象，包含删除操作的响应结果
 */
export const deleteCategory = async (id) => {
    // 使用request发送DELETE请求到指定路径
    return await request.delete(`${commonPath}/${id}`)
}