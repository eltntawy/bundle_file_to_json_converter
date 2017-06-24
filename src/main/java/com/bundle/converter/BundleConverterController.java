package com.bundle.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.property.to.json.converter.BundleToJsonConverter;
import com.property.to.json.converter.Node;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;


@RestController
public class BundleConverterController {


    Logger logger = Logger.getLogger(BundleConverterController.class.getSimpleName());


    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode getData() {


        ClassLoader classLoader = BundleToJsonConverter.class.getClassLoader();

        try (InputStream file = classLoader.getResourceAsStream("properties/property.properties")) {


            Properties properties = new Properties();
            properties.load(file);
            Properties filteredProperties = new Properties();

            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("common.awards") || key.startsWith("common.expertise")) {
                    String value = properties.getProperty(key);
                    filteredProperties.put(key, value);
                }
            }


            Node rootNode = BundleToJsonConverter.build(filteredProperties);

            return rootNode.toJsonNode();

        } catch (IOException ex) {
            ex.printStackTrace();
        };

        return null;


    }

}
