import {createApi, retry} from '@reduxjs/toolkit/query/react'
import {axiosBaseQuery} from "../helper.base-query";

export const emptySplitApi = createApi({
    baseQuery: retry(axiosBaseQuery(), {maxRetries: 3}),
    endpoints: () => ({}),
})
