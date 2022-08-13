import * as React from "react";
import {useRef} from "react";
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
import {Lock} from "react-feather";
import {useFormik} from "formik";
import {useLoginMutation} from "../store/authApi";

interface LoginData {
    username: string;
    password: string;
    rememberMe: string[];
}

export function SignInPage() {
    const formRef = useRef<HTMLFormElement>();
    const [login] = useLoginMutation();
    const formik = useFormik<LoginData>({
        initialValues: {
            username: '',
            password: '',
            rememberMe: [],
        },
        onSubmit: async ({username, password, rememberMe}) => {
            await login({
                loginData: {
                    username, password,
                    rememberMe: rememberMe?.length > 0,
                }
            }).then(console.log).catch(console.error);
        }
    });

    return (
        <>
            <Avatar sx={{m: 1, bgcolor: 'secondary.main'}}>
                <Lock/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign in
            </Typography>
            <Box ref={formRef} component="form" onSubmit={formik.handleSubmit} noValidate sx={{mt: 1}}>
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
                        <MuiLink component={Link} to="/forgot-password" variant="body2">
                            Forgot password?
                        </MuiLink>
                    </Grid>
                    <Grid item>
                        <MuiLink component={Link} to="/register" variant="body2">
                            {"Don't have an account? Sign Up"}
                        </MuiLink>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
}
