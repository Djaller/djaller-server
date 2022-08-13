import * as React from "react";
import {
    Avatar,
    Box,
    Button,
    Checkbox,
    FormControlLabel,
    Grid,
    Link as MuiLink,
    TextField,
    Typography
} from "@mui/material";
import {Link} from "react-router-dom";
import {useFormik} from "formik";
import {UserPlus} from "react-feather";

interface RegisterData {
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    confirmPassword: string;
    rememberMe: string;
}

export function RegisterPage() {
    const formik = useFormik<RegisterData>({
        initialValues: {
            email: '',
            firstName: '',
            lastName: '',
            password: '',
            confirmPassword: '',
            rememberMe: '',
        },
        onSubmit: (values, helpers) => {
            console.log({values});
        }
    });

    return (
        <>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <UserPlus/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Register
            </Typography>
            <Box component="form" onSubmit={formik.handleSubmit} noValidate sx={{mt: 1}}>
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
                <TextField
                    onChange={formik.handleChange}
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                />
                <FormControlLabel
                    control={(
                        <Checkbox
                            onChange={formik.handleChange}
                            value="remember"
                            id='rememberMe'
                            name='rememberMe'
                            color="primary"/>
                    )}
                    label="Remember me"
                />
                <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{mt: 3, mb: 2}}
                >
                    Sign In
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
