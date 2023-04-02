package dom;

import java.util.HashMap;
import java.util.Map;

public class Attributes {

    private HashMap<String, String> map = new HashMap<>();

    public void set(String key, String value) { map.put(key, value); }
    public String get(String key) { return map.get(key); }
    public int size() { return map.size(); }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();

        for(var attribute : map.entrySet()) {
            stringBuilder.append(attribute.getKey());
            stringBuilder.append("=\"");
            stringBuilder.append(attribute.getValue());
            stringBuilder.append("\" ");
        }

        return stringBuilder.toString().trim();
    }
}
