import request from "@/network/request"
const baseUrl = "/admin/lessonProgress";
export const lessonProgressPage = async (pageQuery) => {
    return request({
        url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
        method: "get",
        params: pageQuery.lessonProgress
    })
}