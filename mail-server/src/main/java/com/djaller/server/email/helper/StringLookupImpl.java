package com.djaller.server.email.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookup;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class StringLookupImpl implements StringLookup {

    private final Map<?, ?> config;

    @Override
    public String lookup(String key) {
        var dotIdx = key.indexOf(".");

        if (dotIdx < 0) {
            return config.get(key).toString();
        }

        var subKey = key.substring(0, dotIdx);
        var subConfig = config.get(subKey);
        if (subConfig instanceof Map<?, ?> map) {
            return new StringLookupImpl(map).lookup(key.substring(1 + dotIdx));
        } else if (subConfig instanceof String || subConfig instanceof Number || subConfig instanceof Boolean) {
            return subConfig.toString();
        }

        log.warn("SubConfig - {} is {}", key, subConfig);
        return subConfig != null ? subConfig.toString() : null;
    }

    public static String processTemplate(String text, Map<?, ?> config) {
        final var subst = new StringSubstitutor(new StringLookupImpl(config));
        return subst.replace(text);
    }
}
