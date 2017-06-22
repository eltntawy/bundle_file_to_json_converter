package com.bundle.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import propertiestojson.util.PropertiesToJsonParser;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class BundleConverterController {


    Logger logger = Logger.getLogger(BundleConverterController.class.getSimpleName());


    @RequestMapping(value = "/getData", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonNode getData() {


        String jsonStr ="";
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream file = classLoader.getResourceAsStream("properties/property.properties");
            Properties properties = new Properties();

            properties.load(file);


            jsonStr = PropertiesToJsonParser.parseToJson(properties);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(jsonStr);

            JsonNode commonNode = rootNode.get("common");
            JsonNode awardsNode = commonNode.get("awards");


            JsonNode expertiseNode = commonNode.get("expertise");
            ObjectNode awardsAndExpertiesNode = mapper.getNodeFactory().objectNode();

            awardsAndExpertiesNode.set("awards",awardsNode);
            awardsAndExpertiesNode.set("expertise",expertiseNode);


            return awardsAndExpertiesNode;


        } catch (IOException ex) {

            logger.log(Level.SEVERE,ex.getMessage());

        }


        return null;

    }

}
