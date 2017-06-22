package propertiestojson.object;

import propertiestojson.util.StringToJsonStringWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static propertiestojson.Constants.*;

public class ObjectJson extends AbstractJsonType {

    Map<String, AbstractJsonType> fields = new HashMap<>();


    public void addField(final String field, final AbstractJsonType object) {
        if (object instanceof StringJson) {
            StringJson objStrJson = (StringJson) object;


            String objStr = object.toString();

            //objStr = objStr.replaceFirst("\"","");
            String encoded = objStr.replaceAll("\"", "'");


            StringJson encodedStrJson = new StringJson(encoded);

            fields.put(field, encodedStrJson);


        } else {
            fields.put(field, object);
        }
    }

    public boolean containsField(String field) {
        return fields.containsKey(field);
    }

    public AbstractJsonType getJsonTypeByFieldName(String field) {
        return fields.get(field);
    }

    @Override
    public String toStringJson() {
        StringBuilder result = new StringBuilder().append(JSON_OBJECT_START);
        int index = 0;
        int lastIndex = fields.size() - 1;
        for (String fieldName : fields.keySet()) {
            AbstractJsonType object = fields.get(fieldName);
            String lastSign = index == lastIndex ? EMPTY_STRING : NEW_LINE_SIGN;
            result.append(StringToJsonStringWrapper.wrap(fieldName))
                    .append(":")
                    .append(object.toStringJson())
                    .append(lastSign);
            index++;
        }
        result.append(JSON_OBJECT_END);
        return result.toString();
    }

}
