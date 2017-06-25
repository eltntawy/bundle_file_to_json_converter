package com.property.to.json.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by mohamedrefaat on 6/24/17.
 */
public class BundleToJsonConverter {

    protected static final Logger log = LoggerFactory.getLogger(BundleToJsonConverter.class.getSimpleName());

    private static Node root = new Node("root", "root");

    public static Node build(Properties properties) {

        Set<String> propertiesNames = properties.stringPropertyNames();

        for (String key : propertiesNames) {


            log.debug("key: " + key);
            String value = properties.getProperty(key);
            String nodeName = key.split("\\.")[0];
            log.debug("nodeName: " + nodeName);

            if (root.hasDirect(nodeName)) {

                Node child = root.getChild(nodeName);
                insertNode(child, key.substring(nodeName.length() + 1), value);


            } else {

                Node node = new Node(nodeName, null);
                node.setParent(root);
                root.getChildList().add(node);

                fillChildNode(node, key.substring(nodeName.length() + 1), value);

            }
        }

        return root;
    }

    private static void insertNode(Node child, String key, String value) {

        String nodeName = key.split("\\.")[0];

        if (child.hasDirect(nodeName)) {
            Node childOfChild = child.getChild(nodeName);

            insertNode(childOfChild, key.substring(nodeName.length() + 1), value);

        } else {
            Node newNode;
            if (key.substring(nodeName.length()).length() == 0) {
                newNode = new Node(nodeName, value);
            } else {
                newNode = new Node(nodeName, null);
            }

            newNode.setParent(child);
            child.getChildList().add(newNode);

            if (key.substring(nodeName.length()).length() != 0) {
                insertNode(newNode, key.substring(nodeName.length() + 1), value);
            }
        }

    }


    public static Node fillChildNode(Node parent, String key, String value) {

        String nodeName = key.split("\\.")[0];
        Node node = new Node();
        node.setName(nodeName);
        node.setParent(parent);

        parent.getChildList().add(node);

        if (key.substring(nodeName.length()).length() == 0) {

            node.setValue(value);
            return node;
        }

        return fillChildNode(node, key.substring(nodeName.length() + 1), value);

    }

}
