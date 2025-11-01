import request from "@/network/request"
import { commonPath } from "./requestPath"
export const courseModulePage = async (pageQuery) => {
    return await request({
        method: "get",
        url: `${commonPath}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        params: pageQuery.courseModulesDto
    })
}

export const courseModuleAdd = async (courseModule) => {
    return await request({
        method: "post",
        url: `${commonPath}/add`,
        data: courseModule
    })
}

export const courseModuleUpdate = async (courseModule) => {
    return await request({
        method: "put",
        url: `${commonPath}/update`,
        data: courseModule
    })
}

export const courseModuleDelete = async (ids) => {
    return await request({
        method: "post",
        url: `${commonPath}/delete`,
        data: ids
    })
}