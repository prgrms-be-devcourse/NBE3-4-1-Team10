import axios from "axios";
import { API_URL, getJwt } from "../constant/auth";

const TOKEN = getJwt();

const axiosInstance = axios.create({
  baseURL: API_URL,
  timeout: 1000 * 30,
  headers: {
    Pragma: "no-cache",
    CacheControl: "no-cache",
    Expires: "0",
    Authorization: TOKEN,
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const TOKEN = getJwt();
    const configClone = config;
    configClone.headers.Authorization = TOKEN;
    configClone.headers.usertype = "user";
    return configClone;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response) {
      if (error.response && error.response.status === 401) {
        tokenRefresh().then((res) => {
          if (res) {
            window.location.reload();
          }
        });
      }
      return Promise.resolve(error.response.data);
    }

    return Promise.resolve(error);
  }
);

export const tokenRefresh = async () => {
  // try {
  //   const cognitoUser = await Auth.currentAuthenticatedUser();
  //   const currentSession = await Auth.currentSession();
  //   return new Promise((resolve) => {
  //     cognitoUser.refreshSession(currentSession.refreshToken, (err, session) => {
  //       if (session) {
  //         const { idToken } = session;
  //         setJwt(idToken.jwtToken);
  //         resolve(idToken.jwtToken);
  //       }
  //     });
  //   });
  // } catch (e) {
  //   if (e === 'The user is not authenticated') {
  //     cookieRemove();
  //     Auth.signOut();
  //   }
  // }
};

export default axiosInstance;
