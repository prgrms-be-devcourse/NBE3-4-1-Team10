import { API_URL } from "../constant/auth";
import axiosInstance from "./axiosInstance";

const getProductLists = () => {
  return new Promise((resolve) => {
    axiosInstance.get(`${API_URL}/product`).then((response) => {
      console.log(response.data);
      if (response) {
        resolve(response?.data.data);
      } else {
        resolve({});
      }
    });
  });
};

const postProductLists = () => {
  const body = {
    type: "커피콩",
    name: "Columbia Nariñó",
    imageUrl: "https://i.imgur.com/HKOFQYa.jpeg",
    content: "커피콩 Columbia Nariñó의 예시 설명글입니다.",
    price: 5000,
  };
  return new Promise((resolve) => {
    axiosInstance.post(`${API_URL}/product`, body).then((response) => {
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
  postProductLists,
};

export { ProductService };
