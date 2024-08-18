package unsw.business;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import unsw.business.composite.AndOperator;
import unsw.business.composite.ConstantValue;
import unsw.business.composite.GreaterThanOperator;
import unsw.business.composite.LookupValue;
import unsw.business.composite.NotBlankOperator;
import unsw.business.composite.OrOperator;

public class BusinessRuleMain {
    /**
     * Loads a resource file given a certain path that is relative to resources/
     * for example `/dungeons/maze.json`. Will add a `/` prefix to path if it's not
     * specified.
     * 
     * @precondiction path exists as a file
     * @param path Relative to resources/ will add an implicit `/` prefix if not
     *             given.
     * @return The textual content of the given file.
     * @throws IOException If some other IO exception.
     */
    public static String loadResourceFile(String path) throws IOException {
        if (!path.startsWith("/"))
            path = "/" + path;
        return new String(BusinessRuleMain.class.getResourceAsStream(path).readAllBytes());
    }

    public static BusinessRule generateRule(String inputBusinessRule) {
        JSONObject obj = new JSONObject(inputBusinessRule);
        return generateRuleFromJSON(obj);
    }

    public static BusinessRule generateRuleFromJSON(JSONObject json) {
        String op = json.getString("Operator");
        if (op.equals("AND")) {
            JSONArray args = json.getJSONArray("Args");
            return new AndOperator(generateRuleFromJSON(args.getJSONObject(0)),
                    generateRuleFromJSON(args.getJSONObject(1)));
        } else if (op.equals("OR")) {
            JSONArray args = json.getJSONArray("Args");
            return new OrOperator(generateRuleFromJSON(args.getJSONObject(0)),
                    generateRuleFromJSON(args.getJSONObject(1)));
        } else if (op.equals("GREATER THAN")) {
            JSONArray args = json.getJSONArray("Args");
            return new GreaterThanOperator(generateBusinessRuleValue(args.getJSONObject(0)),
                    generateBusinessRuleValue(args.getJSONObject(1)));
        } else if (op.equals("NOT BLANK")) {
            JSONObject arg = json.getJSONObject("Arg");
            return new NotBlankOperator(generateBusinessRuleValue(arg));
        }
        return null;
    }

    public static BusinessRuleValue generateBusinessRuleValue(JSONObject value) {
        String op = value.getString("Operator");
        if (op.equals("LOOKUP")) {
            String arg = value.getString("Arg");
            return new LookupValue(arg);
        } else if (op.equals("CONSTANT")) {
            Integer arg = value.getInt("Arg");
            return new ConstantValue(arg);
        }
        return null;
    }
}
