import * as React from "react";
import {Box, Container} from "@mui/material";
import {Outlet} from "react-router-dom";

export function AuthWrapper() {
    return (
        <Container component='main' maxWidth='xs'>
            <Box marginTop={8} display='flex' flexDirection='column'>
                <Outlet/>
            </Box>
        </Container>
    )
}
