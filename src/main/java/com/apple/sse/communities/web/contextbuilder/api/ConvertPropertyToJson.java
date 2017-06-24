package com.apple.sse.communities.web.contextbuilder.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by mohamedrefaat on 6/24/17.
 */
public class ConvertPropertyToJson {
    protected static final Logger log = LoggerFactory.getLogger(ConvertPropertyToJson.class.getSimpleName());


    private Node root = new Node("root","root");

    public Node build() {


        try {
            ClassLoader classLoader = ConvertPropertyToJson.class.getClassLoader();

            InputStream file = classLoader.getResourceAsStream("properties/property.properties");

            Properties properties = new Properties();
            properties.load(file);
            Properties filteredProperties = new Properties();

            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("common.awards") || key.startsWith("common.expertise")) {
                    String value = properties.getProperty(key);
                    filteredProperties.put(key, value);
                }
            }

            Set<String> propertiesNames = filteredProperties.stringPropertyNames();

            for (String key : propertiesNames) {

                log.debug("key: "+key);
                String value = filteredProperties.getProperty(key);
                String nodeName = key.split("\\.")[0];
                log.debug("nodeName: "+nodeName);

                if(root.hasDirect(nodeName)) {

                    Node child = root.getChild(nodeName);
                    insertNode(child,key.substring(nodeName.length() + 1),value);


                } else {

                    Node node = new Node(nodeName,null);
                    node.setParent(root);
                    root.getChildList().add(node);

                    Node node1 = fillChildNode(node, key.substring(nodeName.length() + 1), value);
                    log.debug("node1: "+node1);
                }
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return root;
    }

    private void insertNode(Node child, String key, String value) {

        String nodeName = key.split("\\.")[0];

        if (child.hasDirect(nodeName)) {
            Node childOfChild = child.getChild(nodeName);

            insertNode(childOfChild, key.substring(nodeName.length() + 1), value);

        } else {
            Node newNode = new Node(nodeName,value);
            newNode.setParent(child);
            child.getChildList().add(newNode);

            if(key.substring(nodeName.length()).length() != 0){
                insertNode(newNode,key.substring(nodeName.length() + 1),value);
            }
        }

    }


    public Node fillChildNode(Node parent, String key,String value) {

        String nodeName = key.split("\\.")[0];
        Node node = new Node();
        node.setName(nodeName);
        node.setParent(parent);

        parent.getChildList().add(node);

        if (key.substring(nodeName.length()).length() == 0) {

            node.setValue(value);
            return node;
        }

        return fillChildNode(node, key.substring(nodeName.length() + 1),value);

    }


    public static void main(String args[]) {

       Node root = new ConvertPropertyToJson().build();
       System.out.println(root.toJsonNode());

    }


}
