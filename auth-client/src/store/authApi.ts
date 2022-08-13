import { emptySplitApi as api } from "./emptyApi";
const injectedRtkApi = api.injectEndpoints({
  endpoints: (build) => ({
    login: build.mutation<LoginApiResponse, LoginApiArg>({
      query: (queryArg) => ({
        url: `/api/login`,
        method: "POST",
        body: queryArg.loginData,
      }),
    }),
    logout: build.query<LogoutApiResponse, LogoutApiArg>({
      query: () => ({ url: `/api/logout` }),
    }),
  }),
  overrideExisting: false,
});
export { injectedRtkApi as authApi };
export type LoginApiResponse = /** status 200 OK */ object;
export type LoginApiArg = {
  loginData: LoginData;
};
export type LogoutApiResponse = unknown;
export type LogoutApiArg = void;
export type LoginData = {
  username: string;
  password: string;
  rememberMe?: boolean;
};
export const { useLoginMutation, useLogoutQuery } = injectedRtkApi;
