import { API_URL } from "../constant/auth";
import axiosInstance from "./axiosInstance";

const signup = (body) => {
  return new Promise((resolve) => {
    axiosInstance.post(`${API_URL}/user/signup`, body).then((response) => {
      console.log(response);
      const res = response?.data?.value;
      if (res) {
        resolve(res);
      } else {
        resolve({});
      }
    });
  });
};

const UserService = {
  signup,
};

export { UserService };
