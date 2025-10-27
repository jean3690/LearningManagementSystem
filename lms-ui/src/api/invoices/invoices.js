import request from "@/network/request";
const baseUrl = "/admin/invoices";

export const invoicesPage = async (pageQuery) => {
  return await request({
    url: `${baseUrl}/page/${pageQuery.pageNum}/${pageQuery.pageSize}`,
    method: "get",
    data: pageQuery.invoices,
  });
}
export const invoicesAdd = async (invoice) => {
  return await request({
    url: `${baseUrl}/add`,
    method: "post",
    data: invoice,
  });
}
export const invoicesUpdate = async (invoice) => {
  return await request({
    url: `${baseUrl}/update`,
    method: "put",
      data: invoice,
  });
}