import * as React from "react";
import {useEffect, useState} from "react";
import {Avatar, Box, Button, Typography} from "@mui/material";
import {AlertCircle} from "react-feather";
import {Link, useSearchParams} from "react-router-dom";

interface ErrorPageState {
    error?: string | null;
    message?: string | null;
    status?: string | null;
    timestamp?: string | null;
}

export function ErrorPage() {
    const [searchParams] = useSearchParams();
    const [{error, message, status}, setState] = useState<ErrorPageState>({error: 'Error'});

    useEffect(() =>
        setState({
            error: searchParams.get('error'),
            message: searchParams.get('message'),
            status: searchParams.get('status'),
            timestamp: searchParams.get('timestamp'),
        }), [searchParams]);

    return (
        <>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <AlertCircle/>
            </Avatar>
            <Typography component="h1" variant="h5">
                {status ? status + ' | ' : ''}{error}
            </Typography>

            <Box mt={1}>

                <Box mb={2}>
                    Error in the OAuth configuration.
                    Please just restart the login process.
                    Contact the admin if comes again
                </Box>

                <Box>
                    Reason
                </Box>
                <Box component='pre' overflow='auto' bgcolor='lightgoldenrodyellow'>
                    {message}
                </Box>

                <Button
                    component={Link}
                    to="/login"
                    fullWidth
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                    To Sign in
                </Button>
            </Box>
        </>
    );
}
