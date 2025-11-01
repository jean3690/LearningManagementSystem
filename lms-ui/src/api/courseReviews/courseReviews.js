import request from "@/network/request";
const baseUrl = "/admin/courseReviews";
export const courseReviewsPage = async (pageQuery) => {
  return request({
    url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
    method: "get",
    data: pageQuery.courseReviews,
  });
}
export const courseReviewsDelete = async (ids) => {
    return await request({
      url: `${baseUrl}/delete`,
      method: "post",
      data: ids,
    })
}