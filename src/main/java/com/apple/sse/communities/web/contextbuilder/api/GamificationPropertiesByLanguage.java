package com.apple.sse.communities.web.contextbuilder.api;

/**
 * Created by mohamedrefaat on 6/23/17.
 */


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class GamificationPropertiesByLanguage implements PageContextBuilder<JsonNode> {
    protected final Logger log = LoggerFactory.getLogger(getClass().getSimpleName());
    private Locale locale;

    private JsonNode masterParentNode = new ObjectMapper().createObjectNode();
    private String currentKey = "";

    @Override
    public JsonNode build() {
        String jsonStr = "";
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            InputStream file = null;
            String lang = LocaleContextHolder.getLocale().getLanguage();

            // TODO: Load the properties from Spring
            if (lang == "en") {
                file = classLoader.getResourceAsStream("properties/test.properties");
            } else {
                file = classLoader.getResourceAsStream("messages-" + lang + ".properties");
            }

            Properties properties = new Properties();
            properties.load(file);
            Properties filteredProperties = new Properties();

            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("common.awards") || key.startsWith("common.expertise")) {
                    String value = properties.getProperty(key);
                    filteredProperties.put(key, value);

                    log.info("key: " + key);
                    log.info("value: " + value);

                    currentKey = key;



                    log.info("currentNode: " + masterParentNode);
                    log.info("=====================================");
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }



    @Override
    public ContextBuilder<JsonNode> withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    @Override
    public ContextBuilder<JsonNode> withParams(Map<String, Object> params) {
        return this;
    }

    @Override
    public Locale getDefaultLocale() {
        return Locale.US;
    }
}