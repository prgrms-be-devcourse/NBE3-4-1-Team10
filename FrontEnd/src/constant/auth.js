import { getCookie, removeCookie, setCookie } from "./cookie";

export const API_URL = "https://localhost:8080/api";
export const APP_Name = "cafe&";
export const IMG_PATH = "/img";
export const S3_BUCKET = process.env.PUBLIC__USER_S3;
export const BASE_IMG_URL = `https://s3.ap-northeast-2.amazonaws.com/${S3_BUCKET}/`;

export const DOMAIN = "localhost:3000";

/**
 * 로그인 쿠키 아이디
 * 약자 ID_JTW = 토큰 값
 * 약자 ID_SES = 로그인 아이디
 */
const PROJECT_ID = "Cafe&";
const TOKEN = `ID_${PROJECT_ID}_JWT`;
const USER_SESSION = `ID_${PROJECT_ID}_SES`;
const VISIT = `ID_${PROJECT_ID}_LOG_VISIT`;

/**
 *
 * @returns 로그인 유저 토큰
 */
export const getJwt = () => {
  return getCookie(TOKEN);
};

export const setJwt = (token) => {
  return setCookie(TOKEN, token, { path: "/", domain: DOMAIN });
};

/**
 *
 * @returns 로그인 유저 아이디
 */
export const getUserId = () => {
  return getCookie(USER_SESSION) || "";
};

export const setUserId = (user) => {
  return setCookie(USER_SESSION, user, { path: "/", domain: DOMAIN });
};

/**
 *
 * @returns 방문자 체크 쿠키
 */
export const getVisitId = () => {
  return getCookie(VISIT) || "";
};

export const setVisitId = (expires) => {
  return setCookie(VISIT, true, { path: "/", expires, domain: DOMAIN });
};

/**
 *
 * @returns 로그인 체크
 */
export const loginCheck = () => {
  return !!(getJwt() && getUserId());
};

/**
 *
 * @returns 로그아웃 쿠키 제거
 */
export const cookieRemove = () => {
  return new Promise((resolve) => {
    removeCookie(TOKEN, { path: "/", domain: DOMAIN });
    removeCookie(USER_SESSION, { path: "/", domain: DOMAIN });

    resolve(true);
  });
};

/**
 *
 * @returns 스토리지 전부 삭제
 */
export const removeStorage = () => {
  localStorage.clear();
  sessionStorage.clear();
};

/**
 *
 * @returns 로그아웃
 */
export const userLogout = () => {
  removeStorage();
  cookieRemove();
  window.location.reload();
};

/**
 *
 * @param {String} cookieString
 * @returns json Cookie split
 */
export const cookieStringToObject = (cookieString) => {
  if (!(cookieString instanceof Object)) return {};

  const cookieStringClone = cookieString?.split("; ");
  const result = {};

  for (let i = 0; i < cookieStringClone.length; i++) {
    const cur = cookieStringClone[i].split("=");
    result[cur[0]] = cur[1];
  }

  return result;
};
