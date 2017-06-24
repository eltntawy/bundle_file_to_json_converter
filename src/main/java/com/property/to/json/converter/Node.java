package com.property.to.json.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by mohamedrefaat on 6/24/17.
 */
public class Node {

    private String name;
    private String value;
    private Node parent;
    private List<Node> childList = new ArrayList<Node>();


    public Node() {

    }

    public Node(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildList() {

        return childList;
    }


    public Node getChild(String nodeName) {
        for (Node node : childList) {

            if (nodeName.equals(node.getName())) {
                return node;
            }

        }

        return null;
    }

    public boolean hasChilds() {
        return !getChildList().isEmpty();
    }

    public boolean hasParent() {
        return parent != null;
    }

    public boolean hasChildArray() {

        boolean isArray = false ;
        for (Node node : childList) {
            String childNodeName = node.getName();
            if (childNodeName != null && childNodeName.matches("\\d+")) {
                isArray = true;
            }

        }

        return isArray;
    }

    public boolean hasDirect(String childName) {

        for (Node node : childList) {

            if (childName.equals(node.getName())) {
                return true;
            }

        }

        return false;
    }


    public JsonNode toJsonNode() {

        ObjectNode rootJsonNode = new ObjectMapper().createObjectNode();

        if(hasChilds()) {
            ObjectNode childJsonNode = getChildAsJsonNode(childList);
            rootJsonNode.set(name, childJsonNode);
        } else {
            rootJsonNode.put(name, name);
        }

        return rootJsonNode;
    }

    private ObjectNode getChildAsJsonNode(List<Node> childList) {

        ObjectNode objectNode = new ObjectMapper().createObjectNode();

        for(Node node : childList) {

            if(node.hasChilds()) {
                if(node.hasChildArray()) {
                    ArrayNode childArrayNode = getChildAsArrayNode(node.getChildList());
                    objectNode.set(node.getName(), childArrayNode);
                } else {
                    ObjectNode childJsonNode = getChildAsJsonNode(node.getChildList());
                    if(childJsonNode == null) {
                        objectNode.put(node.getName(), node.getValue());
                    } else {
                        objectNode.set(node.getName(), childJsonNode);
                    }
                }
            } else {
                objectNode.put(node.getName(),node.getValue());
            }

        }
        if(childList.size() == 0) {
            return null;
        }

        return objectNode;

    }


    private ArrayNode getChildAsArrayNode (List<Node> childListNode) {

        ArrayNode arrayNode = new ObjectMapper().createArrayNode();

        for(Node childNode : childListNode) {

            JsonNode jsonNode = getChildAsJsonNode(childNode.getChildList());
            if(jsonNode == null) {

                arrayNode.add(childNode.getValue());
            }else {
                arrayNode.add(jsonNode);
            }

        }

        return arrayNode;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name) &&
                Objects.equals(value, node.value) &&
                Objects.equals(parent, node.parent) &&
                Objects.equals(childList, node.childList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, parent, childList);
    }

    @Override
    public String toString() {
        return name;
    }



}
