package com.djaller.server.auth.converter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

public class GrantedAuthorityGsonTypeAdapter extends TypeAdapter<GrantedAuthority> {
    @Override
    public void write(JsonWriter out, GrantedAuthority value) throws IOException {
        out.value(value.getAuthority());
    }

    @Override
    public GrantedAuthority read(JsonReader in) throws IOException {
        var authority = in.nextString();
        return new SimpleGrantedAuthority(authority);
    }
}
