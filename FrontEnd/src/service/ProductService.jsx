import axiosInstance from "../constant/axiosInstance";
import { API_URL } from "../constant/project";

// 상품 리스트
const getProductLists = async () => {
  try {
    const response = await axiosInstance.get(`${API_URL}/product`);
    if (response?.data) {
      return response.data.data;
    }
    return {};
  } catch (error) {
    throw new Error("상품 리스트를 가져오는 중 오류가 발생했습니다.");
  }
};

// 상품 추가
const postProductLists = async () => {
  const body = {
    type: "커피콩",
    name: "Columbia Nariñó",
    imageUrl: "https://i.imgur.com/HKOFQYa.jpeg",
    content: "커피콩 Columbia Nariñó의 예시 설명글입니다.",
    price: 5000,
  };

  try {
    const response = await axiosInstance.post(`${API_URL}/product`, body);
    const res = response?.data?.value;
    if (res) {
      return res;
    }
    return {};
  } catch (error) {
    throw new Error("상품을 추가하는 중 오류가 발생했습니다.");
  }
};

const ProductService = {
  getProductLists,
  postProductLists,
};

export { ProductService };
