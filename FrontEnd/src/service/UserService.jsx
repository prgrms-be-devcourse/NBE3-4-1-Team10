import { API_URL } from "../constant/auth";
import axiosInstance from "./axiosInstance";

/**
 *
 * @param {*} body
 *
 * {
 *   "email": "string",
 *   "password": "string",
 *   "hp": "string",
 *   "name": "string",
 *   "nickname": "string",
 *   "bankName": "string",
 *   "bankNumber": "string",
 *   "bankOwner": "string",
 *   "group": "GR0200",
 *   "role": "USR"
 * }
 *
 */

const signup = (body) => {
  return new Promise((resolve) => {
    axiosInstance.post(`${API_URL}/user/`, body).then((response) => {
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
