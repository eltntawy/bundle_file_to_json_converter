package com.bundle.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import propertiestojson.util.PropertiesToJsonParser;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class BundleConverterController {


    Logger logger = Logger.getLogger(BundleConverterController.class.getSimpleName());


    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    @ResponseBody
    public JsonNode getData() {


        String jsonStr = "";

        ClassLoader classLoader = getClass().getClassLoader();
        Properties properties = new Properties();

        try (InputStream file = classLoader.getResourceAsStream("properties/property.properties")){

            properties.load(file);

            Properties filteredProperty = new Properties();

            for (String key : properties.stringPropertyNames()) {

                if (key.startsWith("common.awards") || key.startsWith("common.expertise")) {
                    String value =  properties.getProperty(key);
                    filteredProperty.put(key,value);
                }

            }

            jsonStr = PropertiesToJsonParser.parseToJson(filteredProperty);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonStr);

            /*JsonNode commonNode = rootNode.get("common");
            JsonNode awardsNode = commonNode.get("awards");


            JsonNode expertiseNode = commonNode.get("expertise");
            ObjectNode awardsAndExpertiesNode = mapper.getNodeFactory().objectNode();

            awardsAndExpertiesNode.set("awards", awardsNode);
            awardsAndExpertiesNode.set("expertise", expertiseNode);
            */

            return rootNode;


        } catch (IOException ex) {

            logger.log(Level.SEVERE, ex.getMessage());

        } finally {

        }


        return null;

    }

}
