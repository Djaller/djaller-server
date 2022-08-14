import * as React from "react";
import {Avatar, Box, Button, TextField, Typography} from "@mui/material";
import {useFormik} from "formik";
import {RefreshCcw} from "react-feather";
import {useResetPasswordMutation} from "../store/authApi";
import {useSearchParams} from "react-router-dom";

interface ChangePasswordData {
    password: string;
    confirm: string;
}

export function ChangePassword() {
    const [reset] = useResetPasswordMutation();
    const [searchParams] = useSearchParams();

    const formik = useFormik<ChangePasswordData>({
        initialValues: {
            password: '',
            confirm: '',
        },
        onSubmit: async (values) => {
            reset({
                resetPassword: {
                    password: values.password,
                    confirm: values.confirm,
                    codeVerifier: searchParams.get('code')!,
                    accountId: searchParams.get('accountId')!,
                }
            });
        }
    });

    return (
        <>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <RefreshCcw/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Update your password
            </Typography>

            <Box component="form" onSubmit={formik.handleSubmit}
                 noValidate sx={{mt: 1}}>
                <TextField
                    onChange={formik.handleChange}
                    margin="normal"
                    required
                    fullWidth
                    id="password"
                    label="Password"
                    name="password"
                    autoComplete="password"
                    autoFocus
                />
                <TextField
                    onChange={formik.handleChange}
                    margin="normal"
                    required
                    fullWidth
                    id="confirm"
                    label="confirm"
                    name="confirm"
                    autoComplete="confirm-password"
                />

                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                    Update your password
                </Button>
            </Box>
        </>
    );
}
