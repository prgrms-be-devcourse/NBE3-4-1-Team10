import { Cookies } from "react-cookie";

const API_URL = "http://localhost:8080/api";
const PROJECT_ID = "Cafe&";
const TOKEN = `ID_${PROJECT_ID}_JWT`;
const USER_SESSION = `ID_${PROJECT_ID}_SES`;
const DOMAIN = "localhost:3000";

const cookies = new Cookies();

const getJwt = () => {
  return getCookie(TOKEN);
};
const setJwt = (token) => {
  return setCookie(TOKEN, token, { path: "/", domain: DOMAIN });
};
const setUserId = (user) => {
  return setCookie(USER_SESSION, user, { path: "/", domain: DOMAIN });
};
const loginCheck = () => {
  return !!(getJwt() && getUserId());
};

const removeCookie = (name, option) => {
  cookies.remove(name, option);
};

const setCookie = (accessToken, refreshToken) => {
  cookies.set("accessToken", accessToken, {
    path: "/",
    maxAge: 60 * 60,
    secure: true,
    sameSite: "Strict",
  });

  cookies.set("refreshToken", refreshToken, {
    path: "/",
    maxAge: 60 * 60 * 24 * 7,
    secure: true,
    sameSite: "Strict",
  });
};

const getCookie = (name) => {
  return cookies.get(name);
};
const getUserId = () => {
  return getCookie(USER_SESSION) || "";
};
const cookieRemove = () => {
  return new Promise((resolve) => {
    removeCookie(TOKEN, { path: "/", domain: DOMAIN });
    removeCookie(USER_SESSION, { path: "/", domain: DOMAIN });
    resolve(true);
  });
};
const removeStorage = () => {
  localStorage.clear();
  sessionStorage.clear();
};
const userLogout = () => {
  removeStorage();
  cookieRemove();
  window.location.reload();
};

export {
  setJwt,
  getJwt,
  getUserId,
  setUserId,
  setCookie,
  getCookie,
  loginCheck,
  removeCookie,
  userLogout,
  removeStorage,
  cookieRemove,
  DOMAIN,
  API_URL,
  PROJECT_ID,
  USER_SESSION,
};
