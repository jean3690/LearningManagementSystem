import request from "@/network/request"
import { commonPath } from "./requestPath"
export const courseModulesPageQuery = (pageNum, pageSize) => {
    return request({
        method: "get",
        url: `${commonPath}/page/${pageNum}/${pageSize}`
    })
}