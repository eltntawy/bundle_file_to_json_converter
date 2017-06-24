package com.bundle.converter;

import com.property.to.json.converter.BundleToJsonConverter;
import com.property.to.json.converter.Node;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mohamedrefaat on 6/23/17.
 */


public class TestNewParserAlgorithm {

    @Test
    public void testAlgorithm() {

        ClassLoader classLoader = BundleToJsonConverter.class.getClassLoader();

        try (InputStream file = classLoader.getResourceAsStream("properties/test.properties")) {


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

            Assert.assertNotNull(rootNode);
            Assert.assertNotNull(rootNode.toJsonNode());

        } catch (IOException ex) {
            ex.printStackTrace();
        };

    }
}
