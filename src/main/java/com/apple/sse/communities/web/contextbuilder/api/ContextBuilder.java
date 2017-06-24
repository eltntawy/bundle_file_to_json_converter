package com.apple.sse.communities.web.contextbuilder.api;

import java.util.Locale;
import java.util.Map;

public interface ContextBuilder<T> {
    T build();
    ContextBuilder<T> withLocale(Locale locale);
    ContextBuilder<T> withParams(Map<String, Object> params);

    /*default Locale getDefaultLocale() {
        return Locale.US;
    }*/

    Locale getDefaultLocale();
}

