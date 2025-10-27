import request from "@/network/request"
const baseUrl = "/admin/courses";

export const coursesPage = async (pageQuery) => {
    return request({
        url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        method: "get",
        params: pageQuery.courses
    })
}
export const coursesDelete = async (ids) => {
    return request({
        url: `${baseUrl}/delete`,
        method: "delete",
        data: ids
    })
}

export const coursesAdd = async (course) => {
    return request({
        url: `${baseUrl}/add`,
        method: "post",
        data: course
    })
}

export const coursesUpdate = async (course) => {
    return request({
        url: `${baseUrl}/update`,
        method: "put",
        data: course
    })
}