import * as React from "react";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {SignInPage} from "./pages/sign-in.page";
import {RegisterPage} from "./pages/register.page";
import {AuthWrapper} from "./components/wrappers/auth.wrapper";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import {ForgotPassword} from "./pages/forgot-password";
import {Provider} from "react-redux";
import {store} from "./store";

const theme = createTheme();

function App() {
    return (
        <Provider store={store}>
            <ThemeProvider theme={theme}>
                <CssBaseline/>
                <BrowserRouter basename='/auth'>
                    <Routes>
                        <Route element={<AuthWrapper/>}>
                            <Route path='/forgot-password' element={<ForgotPassword/>}/>
                            <Route path='/sign-in' element={<SignInPage/>}/>
                            <Route path='/register' element={<RegisterPage/>}/>
                        </Route>
                        <Route
                            path="login"
                            element={<Navigate to="/sign-in" replace/>}
                        />
                        <Route
                            path="*"
                            element={<Navigate to="/sign-in" replace/>}
                        />
                    </Routes>
                </BrowserRouter>
            </ThemeProvider>
        </Provider>
    );
}

export default App;
