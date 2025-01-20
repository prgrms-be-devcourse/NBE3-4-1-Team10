import { API_URL } from "../constant/auth";
import axiosInstance from "./axiosInstance";

const getProductLists = (body) => {
  return new Promise((resolve) => {
    axiosInstance.get(`${API_URL}/product`, body).then((response) => {
      const res = response?.data?.value;
      if (res) {
        resolve(res);
      } else {
        resolve({});
      }
    });
  });
};

const ProductService = {
  getProductLists,
};

export { ProductService };
