import type { ConfigFile } from '@rtk-query/codegen-openapi'

const config: ConfigFile = {
    schemaFile: 'http://localhost:8003/v3/api-docs',
    apiFile: './src/store/emptyApi.ts',
    apiImport: 'emptySplitApi',
    outputFile: './src/store/authApi.ts',
    exportName: 'authApi',
    hooks: true,
}

export default config;
