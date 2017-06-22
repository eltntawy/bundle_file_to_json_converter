package propertiestojson.traverse;

import propertiestojson.JsonObjectFieldsValidator;
import propertiestojson.PropertyArrayHelper;
import propertiestojson.object.ArrayJson;
import propertiestojson.object.NumberJson;
import propertiestojson.object.ObjectJson;
import propertiestojson.object.StringJson;
import propertiestojson.util.exception.ParsePropertiesException;

import static propertiestojson.Constants.SIMPLE_ARRAY_DELIMETER;
import static propertiestojson.JsonObjectFieldsValidator.checkIsListOnlyForPrimitive;
import static propertiestojson.JsonObjectsTraverseResolver.isArrayField;
import static propertiestojson.util.NumberUtil.getNumber;
import static propertiestojson.util.NumberUtil.isNumber;

public class PrimitiveTypeTraverseAlgorithm extends TraverseAlgorithm {


    @Override
    public ObjectJson traverse(String field) {
        addPrimitiveFieldWhenIsValid(field);
        return null;
    }

    private void addPrimitiveFieldWhenIsValid(String field) {
        try {
            JsonObjectFieldsValidator.checkEarlierWasJsonString(currentObjectJson, field, propertiesKey);
            addPrimitiveFieldToCurrentJsonObject(field);
        } catch (ParsePropertiesException ex) {
            addPrimitiveFieldTitleToCurrentJsonObject(field);
        }
    }

    private void addPrimitiveFieldToCurrentJsonObject(String field) {
        String propertyValue = properties.get(propertiesKey);
        if (isSimpleArray(propertyValue)){
            currentObjectJson.addField(field, new ArrayJson(propertyValue.split(SIMPLE_ARRAY_DELIMETER)));
        } else if (isArrayField(field)){
            addFieldToArray(field, propertyValue);
        } else if (isNumber(propertyValue)) {
            currentObjectJson.addField(field, new NumberJson(getNumber(propertyValue)));
        } else {
            currentObjectJson.addField(field, new StringJson(propertyValue));
        }
    }

    private void addPrimitiveFieldTitleToCurrentJsonObject(String field) {
        String propertyValue = properties.get(propertiesKey);
        if (isSimpleArray(propertyValue)){
            currentObjectJson.addField(field+ "_title", new ArrayJson(propertyValue.split(SIMPLE_ARRAY_DELIMETER)));
        } else if (isArrayField(field)){
            addFieldToArray(field+ "_title", propertyValue);
        } else if (isNumber(propertyValue)) {
            currentObjectJson.addField(field+ "_title", new NumberJson(getNumber(propertyValue)));
        } else {
            currentObjectJson.addField(field+ "_title", new StringJson(propertyValue));
        }
    }

    protected void addFieldToArray(String field, String propertyValue) {
        PropertyArrayHelper propertyArrayHelper = new PropertyArrayHelper(field);
        field = propertyArrayHelper.getArrayfieldName();
        if (arrayWithGivenFieldNameExist(field)){
            fetchArrayAndAddElement(field, propertyValue, propertyArrayHelper);
        } else {
            createArrayAndAddElement(field, propertyValue, propertyArrayHelper);
        }
    }

    private void createArrayAndAddElement(String field, String propertyValue, PropertyArrayHelper propertyArrayHelper) {
        ArrayJson arrayJsonObject = new ArrayJson();
        arrayJsonObject.addElement(propertyArrayHelper.getIndexArray(), new StringJson(propertyValue));
        currentObjectJson.addField(field, arrayJsonObject);
    }

    private void fetchArrayAndAddElement(String field, String propertyValue, PropertyArrayHelper propertyArrayHelper) {
        ArrayJson arrayJson = getArrayJsonWhenIsValid(field);
        checkIsListOnlyForPrimitive( propertiesKey, field, arrayJson,propertyArrayHelper.getIndexArray());
        arrayJson.addElement(propertyArrayHelper.getIndexArray(),new StringJson(propertyValue));
    }

    private boolean arrayWithGivenFieldNameExist(String field) {
        return currentObjectJson.containsField(field);
    }

    private boolean isSimpleArray(String propValue) {
        return false;
        //return propValue.contains(SIMPLE_ARRAY_DELIMETER);
    }

}
