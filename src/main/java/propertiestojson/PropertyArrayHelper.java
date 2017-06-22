package propertiestojson;

import static propertiestojson.Constants.ARRAY_END_SIGN;
import static propertiestojson.Constants.ARRAY_START_SIGN;

public class PropertyArrayHelper {

    private int indexArray;
    private String arrayfieldName="array";

    public PropertyArrayHelper(String field){
        //int indexOfStartArraySign = field.indexOf(ARRAY_START_SIGN)+1;
        //int indexofEndArraySign = field.indexOf(ARRAY_END_SIGN);
        //indexArray = Integer.valueOf(field.substring(indexOfStartArraySign, indexofEndArraySign));
        indexArray = Integer.valueOf(field);

        //arrayfieldName = field.substring(0, indexOfStartArraySign-1);
    }

    public int getIndexArray() {
        return indexArray;
    }

    public String getArrayfieldName() {
        return arrayfieldName;
    }
}
