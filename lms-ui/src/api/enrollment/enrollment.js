import request from "@/network/request";
const baseUrl = "/admin/enroll";
export const enrollmentsPage = async (pageQuery) => {
  return await request({
    url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
    method: "get",
    params: pageQuery.enrollments,
  });
}
export const enrollmentsDelete = async (ids) => {
  return await request({
    url: `${baseUrl}/delete`,
    method: "post",
    data: ids,
  });
}
export const enrollmentsAdd = async (enrollment) => {
  return await request({
    url: `${baseUrl}/add`,
    method: "post",
    data: enrollment,
  });
}
export const enrollmentsUpdate = async (enrollment) => {
  return await request({
    url: `${baseUrl}/update`,
    method: "post",
    data: enrollment,
  });
}
