import * as React from "react";
import {Avatar, Box, Button, Grid, Link as MuiLink, TextField, Typography} from "@mui/material";
import {Link} from "react-router-dom";
import {useFormik} from "formik";
import {RefreshCcw} from "react-feather";

interface ResetPasswordData {
    username: '',
}

export function ForgotPassword() {
    const formik = useFormik<ResetPasswordData>({
        initialValues: {
            username: '',
        },
        onSubmit: (values, helpers) => {
            console.log({values});
        }
    });

    return (
        <>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <RefreshCcw/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Forgot password
            </Typography>
            <Box component="form" onSubmit={formik.handleSubmit}
                 noValidate sx={{mt: 1}}>
                <TextField
                    onChange={formik.handleChange}
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
                    autoComplete="username"
                    autoFocus
                />

                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                    Send link
                </Button>
                <Grid container>
                    <Grid item xs>
                        <MuiLink component={Link} to="/login" variant="body2">
                            Sign in
                        </MuiLink>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
}
