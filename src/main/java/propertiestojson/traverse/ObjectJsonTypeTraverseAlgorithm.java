package propertiestojson.traverse;

import propertiestojson.JsonObjectFieldsValidator;
import propertiestojson.object.AbstractJsonType;
import propertiestojson.object.ObjectJson;
import propertiestojson.util.exception.ParsePropertiesException;

public class ObjectJsonTypeTraverseAlgorithm extends TraverseAlgorithm {


    @Override
    public ObjectJson traverse(String field) {
        fetchJsonObjectOrCreate(field);
        return currentObjectJson;
    }

    private void fetchJsonObjectOrCreate(String field) {
        if (currentObjectJson.containsField(field)) {
            try {
                fetchJsonObjectWhenIsNotPrimitive(field);
            } catch (ParsePropertiesException ex) {
                createNewJsonObjectAndAssignToCurrent(field);
            }
        } else {
            createNewJsonObjectAndAssignToCurrent(field);
        }
    }

    private void createNewJsonObjectAndAssignToCurrent(String field) {
        ObjectJson nextObjectJson = new ObjectJson();
        currentObjectJson.addField(field, nextObjectJson);
        currentObjectJson = nextObjectJson;
    }

     private void fetchJsonObjectWhenIsNotPrimitive(String field) {
        AbstractJsonType jsonType = currentObjectJson.getJsonTypeByFieldName(field);
        JsonObjectFieldsValidator.checkEarlierWasJsonObject(propertiesKey,field, jsonType);
         currentObjectJson = (ObjectJson) jsonType;
    }
}
