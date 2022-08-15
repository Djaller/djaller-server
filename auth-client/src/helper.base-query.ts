import type {AxiosError, AxiosRequestConfig} from 'axios';
import axios from 'axios';
import type {BaseQueryFn} from '@reduxjs/toolkit/query';

type AxiosBaseQueryProps = {
    baseUrl: string;
}

type AxiosBaseQueryArgs = {
    url: string;
    method: AxiosRequestConfig['method'];
    body?: AxiosRequestConfig['data'];
    params?: AxiosRequestConfig['params'];
}

type Result = any;
type Error = any;

export const axiosBaseQuery = ({baseUrl}: AxiosBaseQueryProps = {baseUrl: ''}): BaseQueryFn<AxiosBaseQueryArgs, Result, Error> =>
    async ({
               url,
               method,
               body,
               params
           }) => {
        try {
            const result = await axios({
                url: baseUrl + url,
                method: method!,
                data: body,
                params: params,
                withCredentials: true
            })
            return {data: result.data}
        } catch (axiosError) {
            let err = axiosError as AxiosError
            return {
                error: {
                    status: err.response?.status,
                    data: err.response?.data || err.message,
                },
            }
        }
    }
