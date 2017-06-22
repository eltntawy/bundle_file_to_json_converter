package propertiestojson.traverse;

import propertiestojson.JsonObjectFieldsValidator;
import propertiestojson.object.AbstractJsonType;
import propertiestojson.object.ArrayJson;
import propertiestojson.object.ObjectJson;
import propertiestojson.traverse.transfer.DataForTraverse;

import java.util.Map;


public abstract class TraverseAlgorithm {

    protected Map<String, String> properties;
    protected String propertiesKey;
    protected ObjectJson currentObjectJson;


    protected ArrayJson getArrayJsonWhenIsValid(String field) {
        AbstractJsonType jsonType = currentObjectJson.getJsonTypeByFieldName(field);
        JsonObjectFieldsValidator.checkEalierWasArrayJson(propertiesKey,field, jsonType);
        return (ArrayJson) jsonType;
    }

    public abstract ObjectJson traverse(String field);

    public final ObjectJson traverseOnObjectAndInitByField(DataForTraverse dataForTraverse){
        properties = dataForTraverse.getProperties();
        propertiesKey = dataForTraverse.getPropertiesKey();
        currentObjectJson = dataForTraverse.getCurrentObjectJson();
        return traverse(dataForTraverse.getField());
    }
}
