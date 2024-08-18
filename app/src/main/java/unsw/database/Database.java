package unsw.database;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;

import unsw.database.Column.ColumnType;

public class Database {

    private Map<String, ColumnType> columns;
    private List<Map<String, Object>> data;
    private Map<String, Function<Map<String, Object>, Object>> derivedColumns;
    private Map<String, List<String>> derivedDependencies;

    public Database(List<Column> columns) {
        this.columns = new HashMap<>();
        for (Column column : columns) {
            this.columns.put(column.getName(), column.getType());
        }
        this.data = new ArrayList<>();
        this.derivedColumns = new HashMap<>();
        this.derivedDependencies = new HashMap<>();
    }

    // Query is an empty class that you can do whatever you want to (add
    // subclasses/functions/whatever)
    // the only requirement is that the name remains the same.
    public Query parseQuery(String query) {
        // wrapped in an array list to allow us to remove tokens from the "stream"
        // you don't have to change this function.
        return parseOrExpr(new ArrayList<>(Arrays.asList(query.split("\\s"))));
    }

    // Queries database using already compiled Query
    // If a record matches twice you can add it twice (i.e. you don't have to handle
    // distinctly)
    public List<Map<String, Object>> queryComplex(Query query) {
        return new ArrayList<Map<String, Object>>();
        // TODO: ^^
    }

    // Gets the column type for the specified column name
    public ColumnType getColumn(String name) {
        return columns.get(name);
    }

    // should return number of new records inserted
    public int ingest(String contents) {
        // split up into rows
        List<String> rows = new ArrayList<>(Arrays.asList(contents.split("\n")));

        // grab the first row for schema
        // NOTE: When splitting on certain characters in java you need to escape them
        // (this is due to split actually taking in a regex).
        // So if you need to split on `|` you'll want to do `\\|` instead as per below.
        // (you shouldn't need to split on anything else other than newlines as above)
        String[] header = rows.remove(0).split("\\|");

        // trim schema to remove surrounding whitespace
        for (int i = 0; i < header.length; i++)
            header[i] = header[i].trim();

        for (String row : rows) {
            String[] values = row.split("\\|");
            Map<String, Object> record = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                String columnName = header[i];
                ColumnType type = getColumn(columnName);
                Object value;
                if (type.equals(ColumnType.MARK)) {
                    value = Integer.parseInt(values[i].trim());
                } else {
                    value = values[i].trim();
                }
                record.put(columnName, value);
                System.out.println(record);
            }
            updateDerivedColumns(record);
            data.add(record);
        }
        return rows.size();
    }

    // Queries database for all records where columnName has a value that .equals()
    // value.
    public List<Map<String, Object>> querySimple(String columnName, Object value) {
        List<Map<String, Object>> results = new ArrayList<>();
        for (Map<String, Object> record : data) {
            System.out.println(record.get(columnName));
            if (record.get(columnName).equals(value)) {
                results.add(record);
            }
        }
        return results;
    }

    public void updateData(String queryColumnName, Object queryValue, String columnName, Object columnValue) {
        for (Map<String, Object> record : data) {
            if (record.get(queryColumnName).equals(queryValue)) {
                record.put(columnName, columnValue);
            }
        }
    }

    public void addDerivedColumn(String columnName, List<String> dependencies,
            Function<Map<String, Object>, Object> compute) {

        // Map<String, Object> apply = new HashMap<>();
        // Map<String, Object> derived = new HashMap<>();
        // for (Map<String, Object> record : data) {
        // for (String dep : dependencies) {
        // apply.put(dep, record.get(dep));
        // }
        // derived.put(columnName, compute.apply(apply));
        // }
        // data.add(derived);

        derivedColumns.put(columnName, compute);
        derivedDependencies.put(columnName, dependencies);
    }

    private Map<String, Object> buildDependenciesMap(Map<String, Object> record, List<String> dependencies) {
        Map<String, Object> dependencyMap = new HashMap<>();
        for (String dependency : dependencies) {
            dependencyMap.put(dependency, record.get(dependency));
        }
        return dependencyMap;
    }

    private void updateDerivedColumns(Map<String, Object> record) {
        for (String columnName : derivedColumns.keySet()) {
            List<String> dependencies = derivedDependencies.get(columnName);
            Map<String, Object> dependencyMap = buildDependenciesMap(record, dependencies);
            Object computedValue = derivedColumns.get(columnName).apply(dependencyMap);
            record.put(columnName, computedValue);
        }
    }

    /*
     * For the following functions you'll want to change them a very tiny amount,
     * you will probably
     * be changing the return types and making it so it constructs objects in this
     * said recursive manner.
     * 
     * To make it simple, the query language presumes all input is valid and doesn't
     * support `()` to decide precedence.
     * 
     * As a very rough explanation of how this works (it's an exam, you do *NOT*
     * need to understand the specifics just
     * focus on changing the return new Query()'s to what you need to construct the
     * query object).
     * 
     * If you are REALLY struggling look at the practice exam, how did you do the
     * query structure for business rules there?
     * How, can you apply that structure to this question in a similar fashion...
     */

    public Query parseAtom(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        String tok = tokens.remove(0);
        try {
            // Integer constant
            int result = Integer.parseInt(tok);
            return new Query();
            // TODO: ^^
        } catch (NumberFormatException e) {
            // (ignore)
        }

        // then it must be a String
        // we may have to combine multiple tokens into ones
        String agg = tok.substring(1);
        if (agg.charAt(agg.length() - 1) == '\'') {
            // A string constant.
            String result = agg;
            return new Query();
            // TODO: ^^
        }

        // this is where the text has spaces i.e. 'a b c', what we do is recombine the
        // tokens
        // until we find one with a ' terminator, this isn't a great strategy, but it's
        // simple!
        // this presumes we'll terminate, again we always presume valid input!
        while (true) {
            String next = tokens.remove(0);

            if (next.charAt(next.length() - 1) == '\'') {
                // A string constant.
                String result = agg + " " + next.substring(0, next.length() - 1);
                return new Query();
            } else {
                agg += " " + next;
            }
        }
    }

    public Query parseOperatorExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // we presume we always need at least one operator and since
        // columns can't have boolean values we always need a symbol

        // lhs is the column name
        String lhs = tokens.remove(0);
        // the symbol (i.e. = or >)
        String op = tokens.remove(0);
        // what to compare it to i.e. 'A' or 2
        Query rhs = parseAtom(tokens);

        return new Query();
        // TODO: ^^
    }

    public Query parseAndExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // lhs
        Query lhs = parseOperatorExpr(tokens);

        // read AND
        if (tokens.size() >= 1 && tokens.get(0).equals("AND") && lhs != null) {
            tokens.remove(0);
            // recurse i.e. a AND b AND c => a AND (b AND c)
            Query rhs = parseAndExpr(tokens);

            // you should do something with the results of above...
            // something like X x = new X(lhs, rhs);
            return new Query();
            // TODO:^
        } else {
            return lhs;
        }
    }

    public Query parseOrExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // lhs
        Query lhs = parseAndExpr(tokens);

        // read OR
        if (tokens.size() >= 1 && tokens.get(0).equals("OR") && lhs != null) {
            tokens.remove(0);
            // recurse i.e. a OR b OR c => a OR (b OR c)
            Query rhs = parseOrExpr(tokens);

            // you should do something with the results of above...
            // something like X x = new X(lhs, rhs);
            return new Query();
            // TODO:^
        } else {
            return lhs;
        }
    }
}
