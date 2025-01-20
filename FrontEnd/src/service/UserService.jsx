import { API_URL } from "../constant/auth";
import axiosInstance from "./axiosInstance";

const signUp = (body) => {
  return new Promise((resolve) => {
    axiosInstance.post(`${API_URL}/user/signup`, body).then((response) => {
      const res = response?.data?.value;
      if (res) {
        resolve(res);
      } else {
        resolve({});
      }
    });
  });
};

const signIn = (body) => {
  console.log(body);
  return new Promise((resolve) => {
    axiosInstance.post(`${API_URL}/user/login`, body).then((response) => {
      console.log(response);
      if (response) {
        console.log(response);
        resolve(response);
      } else {
        resolve({});
      }
    });
  });
};

const UserService = {
  signUp,
  signIn,
};

export { UserService };
