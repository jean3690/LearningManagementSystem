import request from "@/network/request"
const baseUrl = "/admin/lesson";

export const lessonPage = async (pageQuery) => {
    return request({
        url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        method: "get",
        params: pageQuery.lesson
    })
}

export const lessonAdd = async (lesson) => {
    return request({
        url: `${baseUrl}/add`,
        method: "post",
        data: lesson
    })
}

export const lessonUpdate = async (lesson) => {
    return request({
        url: `${baseUrl}/update`,
        method: "post",
        data: lesson
    })
}
export const lessonDelete = async (ids) => {
    return request({
        url: `${baseUrl}/delete`,
        method: "delete",
        data: ids
    })
}