import { emptySplitApi as api } from "./emptyApi";
const injectedRtkApi = api.injectEndpoints({
  endpoints: (build) => ({
    getAppClientByClientId: build.query<
      GetAppClientByClientIdApiResponse,
      GetAppClientByClientIdApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/app-clients/${queryArg.clientId}`,
      }),
    }),
    updateAppClient: build.mutation<
      UpdateAppClientApiResponse,
      UpdateAppClientApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/app-clients/${queryArg.clientId}`,
        method: "PUT",
        body: queryArg.appClient,
      }),
    }),
    updateSecretAppClient: build.mutation<
      UpdateSecretAppClientApiResponse,
      UpdateSecretAppClientApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/app-clients/secret/${queryArg.clientId}`,
        method: "PUT",
        body: queryArg.appClientSecretUpdate,
      }),
    }),
    register: build.mutation<RegisterApiResponse, RegisterApiArg>({
      query: (queryArg) => ({
        url: `/auth/api/register`,
        method: "POST",
        body: queryArg.registerData,
      }),
    }),
    listProviderClient: build.query<
      ListProviderClientApiResponse,
      ListProviderClientApiArg
    >({
      query: () => ({ url: `/auth/api/provider-clients` }),
    }),
    saveProviderClient: build.mutation<
      SaveProviderClientApiResponse,
      SaveProviderClientApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/provider-clients`,
        method: "POST",
        body: queryArg.providerClient,
      }),
    }),
    resetPassword: build.mutation<
      ResetPasswordApiResponse,
      ResetPasswordApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/passwords/reset`,
        method: "POST",
        body: queryArg.resetPassword,
      }),
    }),
    forgotPassword: build.mutation<
      ForgotPasswordApiResponse,
      ForgotPasswordApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/passwords/forgot`,
        method: "POST",
        body: queryArg.forgotPassword,
      }),
    }),
    login: build.mutation<LoginApiResponse, LoginApiArg>({
      query: (queryArg) => ({
        url: `/auth/api/login`,
        method: "POST",
        body: queryArg.loginData,
      }),
    }),
    listAppClient: build.query<ListAppClientApiResponse, ListAppClientApiArg>({
      query: () => ({ url: `/auth/api/app-clients` }),
    }),
    saveAppClient: build.mutation<
      SaveAppClientApiResponse,
      SaveAppClientApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/app-clients`,
        method: "POST",
        body: queryArg.appClient,
      }),
    }),
    listAllProviderClient: build.query<
      ListAllProviderClientApiResponse,
      ListAllProviderClientApiArg
    >({
      query: () => ({ url: `/auth/api/provider-clients/and-systems` }),
    }),
    logout: build.query<LogoutApiResponse, LogoutApiArg>({
      query: () => ({ url: `/auth/api/logout` }),
    }),
    deleteProviderClient: build.mutation<
      DeleteProviderClientApiResponse,
      DeleteProviderClientApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/provider-clients/by-registration/${queryArg.registrationId}`,
        method: "DELETE",
      }),
    }),
    deleteProviderClient1: build.mutation<
      DeleteProviderClient1ApiResponse,
      DeleteProviderClient1ApiArg
    >({
      query: (queryArg) => ({
        url: `/auth/api/app-clients/by-client-id/${queryArg.clientId}`,
        method: "DELETE",
      }),
    }),
  }),
  overrideExisting: false,
});
export { injectedRtkApi as authApi };
export type GetAppClientByClientIdApiResponse = /** status 200 OK */ AppClient;
export type GetAppClientByClientIdApiArg = {
  clientId: string;
};
export type UpdateAppClientApiResponse = /** status 200 OK */ AppClient;
export type UpdateAppClientApiArg = {
  clientId: string;
  appClient: AppClient;
};
export type UpdateSecretAppClientApiResponse = /** status 200 OK */ AppClient;
export type UpdateSecretAppClientApiArg = {
  clientId: string;
  appClientSecretUpdate: AppClientSecretUpdate;
};
export type RegisterApiResponse = /** status 200 OK */ Account;
export type RegisterApiArg = {
  registerData: RegisterData;
};
export type ListProviderClientApiResponse =
  /** status 200 OK */ SimpleProviderClientModel[];
export type ListProviderClientApiArg = void;
export type SaveProviderClientApiResponse = /** status 200 OK */ ProviderClient;
export type SaveProviderClientApiArg = {
  providerClient: ProviderClient;
};
export type ResetPasswordApiResponse = unknown;
export type ResetPasswordApiArg = {
  /** {@link ResetPassword ResetPassword} */
  resetPassword: ResetPassword;
};
export type ForgotPasswordApiResponse = unknown;
export type ForgotPasswordApiArg = {
  /** {@link ForgotPassword ForgotPassword} */
  forgotPassword: ForgotPassword;
};
export type LoginApiResponse = /** status 200 OK */ RedirectionData;
export type LoginApiArg = {
  loginData: LoginData;
};
export type ListAppClientApiResponse = /** status 200 OK */ AppClient[];
export type ListAppClientApiArg = void;
export type SaveAppClientApiResponse = /** status 200 OK */ AppClient;
export type SaveAppClientApiArg = {
  appClient: AppClient;
};
export type ListAllProviderClientApiResponse =
  /** status 200 OK */ SimpleProviderClientModel[];
export type ListAllProviderClientApiArg = void;
export type LogoutApiResponse = unknown;
export type LogoutApiArg = void;
export type DeleteProviderClientApiResponse = unknown;
export type DeleteProviderClientApiArg = {
  registrationId: string;
};
export type DeleteProviderClient1ApiResponse = unknown;
export type DeleteProviderClient1ApiArg = {
  clientId: string;
};
export type ClientSettings = {
  requireProofKey?: boolean;
  requireAuthorizationConsent?: boolean;
  jwkSetUrl?: string;
  algorithm?:
    | "RS256"
    | "RS384"
    | "RS512"
    | "ES256"
    | "ES384"
    | "ES512"
    | "PS256"
    | "PS384"
    | "PS512";
};
export type TokenSettings = {
  accessTokenTimeToLive?: {
    seconds?: number;
    zero?: boolean;
    nano?: number;
    negative?: boolean;
    positive?: boolean;
    units?: {
      durationEstimated?: boolean;
      timeBased?: boolean;
      dateBased?: boolean;
    }[];
  };
  accessTokenFormat?: "SELF_CONTAINED" | "REFERENCE";
  reuseRefreshTokens?: boolean;
  refreshTokenTimeToLive?: {
    seconds?: number;
    zero?: boolean;
    nano?: number;
    negative?: boolean;
    positive?: boolean;
    units?: {
      durationEstimated?: boolean;
      timeBased?: boolean;
      dateBased?: boolean;
    }[];
  };
  idTokenSignatureAlgorithm?:
    | "RS256"
    | "RS384"
    | "RS512"
    | "ES256"
    | "ES384"
    | "ES512"
    | "PS256"
    | "PS384"
    | "PS512";
};
export type AppClient = {
  id?: string;
  clientId: string;
  clientIdIssuedAt?: string;
  clientSecretExpiresAt?: string;
  clientName: string;
  clientAuthenticationMethods?: (
    | "CLIENT_SECRET_BASIC"
    | "CLIENT_SECRET_POST"
    | "CLIENT_SECRET_JWT"
    | "PRIVATE_KEY_JWT"
    | "NONE"
  )[];
  authorizationGrantTypes?: (
    | "REFRESH_TOKEN"
    | "CLIENT_CREDENTIALS"
    | "PASSWORD"
    | "JWT_BEARER"
    | "AUTHORIZATION_CODE"
  )[];
  redirectUris?: string[];
  scopes?: string[];
  clientSettings?: ClientSettings;
  tokenSettings?: TokenSettings;
};
export type AppClientSecretUpdate = {
  secret: string;
};
export type Account = {
  id?: string;
  createdAt?: string;
  updatedAt?: string;
  email?: string;
  emailVerified?: boolean;
  firstName?: string;
  lastName?: string;
  phoneNumber?: string;
  phoneNumberVerified?: boolean;
  status?: "ACTIVE" | "LOCKED";
};
export type RegisterData = {
  email: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  password: string;
  rememberMe?: boolean;
};
export type SimpleProviderClientModel = {
  id?: string;
  registrationId?: string;
  clientId?: string;
  clientName?: string;
};
export type DetailClientRegistration = {
  authorizationUri?: string;
  tokenUri?: string;
  userInfoUri?: string;
  userInfoAttributeName?: string;
  userInfoAuthenticationMethod?: "HEADER" | "FORM" | "QUERY";
  jwkSetUri?: string;
  issuerUri?: string;
  configurationMetadata?: {
    [key: string]: object;
  };
};
export type ProviderClient = {
  id?: string;
  registrationId?: string;
  clientId?: string;
  clientName?: string;
  clientSecret?: string;
  clientAuthenticationMethod?:
    | "CLIENT_SECRET_BASIC"
    | "CLIENT_SECRET_POST"
    | "CLIENT_SECRET_JWT"
    | "PRIVATE_KEY_JWT"
    | "NONE";
  authorizationGrantType?:
    | "AUTHORIZATION_CODE"
    | "REFRESH_TOKEN"
    | "CLIENT_CREDENTIALS"
    | "PASSWORD"
    | "JWT_BEARER";
  redirectUri?: string[];
  scopes?: string[];
  detail?: DetailClientRegistration;
  systemClient?: boolean;
};
export type ResetPassword = {
  password: string;
  confirm: string;
  codeVerifier: string;
  accountId: string;
};
export type ForgotPassword = {
  username: string;
};
export type RedirectionData = {
  redirectUrl: string;
};
export type LoginData = {
  username: string;
  password: string;
  rememberMe?: boolean;
};
export const {
  useGetAppClientByClientIdQuery,
  useUpdateAppClientMutation,
  useUpdateSecretAppClientMutation,
  useRegisterMutation,
  useListProviderClientQuery,
  useSaveProviderClientMutation,
  useResetPasswordMutation,
  useForgotPasswordMutation,
  useLoginMutation,
  useListAppClientQuery,
  useSaveAppClientMutation,
  useListAllProviderClientQuery,
  useLogoutQuery,
  useDeleteProviderClientMutation,
  useDeleteProviderClient1Mutation,
} = injectedRtkApi;
