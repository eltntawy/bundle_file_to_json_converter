package propertiestojson;

import propertiestojson.object.ObjectJson;
import propertiestojson.traverse.ArrayJsonTypeTraverseAlgorithm;
import propertiestojson.traverse.ObjectJsonTypeTraverseAlgorithm;
import propertiestojson.traverse.PrimitiveTypeTraverseAlgorithm;
import propertiestojson.traverse.TraverseAlgorithm;
import propertiestojson.traverse.transfer.DataForTraverse;
import propertiestojson.util.NumberUtil;

import java.util.HashMap;
import java.util.Map;

import static propertiestojson.Constants.ARRAY_END_SIGN;
import static propertiestojson.Constants.ARRAY_START_SIGN;


public class JsonObjectsTraverseResolver {

    private Map<String, String> properties;
    private String propertiesKey;
    private String[] fields;
    private ObjectJson currentObjectJson;

    public JsonObjectsTraverseResolver(Map<String, String> properties, String propertiesKey, String[] fields, ObjectJson coreObjectJson) {
        this.properties = properties;
        this.propertiesKey = propertiesKey;
        this.fields = fields;
        this.currentObjectJson = coreObjectJson;
    }

    private static Map<AlgorithmType, TraverseAlgorithm> algorithms = new HashMap<>();
    static {
        algorithms.put(AlgorithmType.OBJECT, new ObjectJsonTypeTraverseAlgorithm());
        algorithms.put(AlgorithmType.PRIMITIVE, new PrimitiveTypeTraverseAlgorithm());
        algorithms.put(AlgorithmType.ARRAY, new ArrayJsonTypeTraverseAlgorithm());
    }

    public void initializeFieldsInJson() {
        for (int index = 0; index < fields.length; index++) {
            String field = fields[index];
            DataForTraverse dataForTraverse = new DataForTraverse(properties, propertiesKey, currentObjectJson, field);
            currentObjectJson = algorithms.get(resolveAlgorithm(index, field)).traverseOnObjectAndInitByField(dataForTraverse);
        }
    }

    private AlgorithmType resolveAlgorithm(int index, String field){
        if (isPrimitiveField(index)){
           return  AlgorithmType.PRIMITIVE;
        }
        if (isArrayField(field)){
            return AlgorithmType.ARRAY;
        }
        return AlgorithmType.OBJECT;
    }

    public static boolean isArrayField(String field) {
        if(NumberUtil.isNumber(field)) {
            return true;
        } else {
            return false;
        }
        //return field.contains(ARRAY_START_SIGN) && field.contains(ARRAY_END_SIGN);
    }

    private boolean isPrimitiveField(int index) {
        int lastIndex = fields.length - 1;
        return index == lastIndex;
    }

}
