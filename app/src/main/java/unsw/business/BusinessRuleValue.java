package unsw.business;

import java.util.Map;

public interface BusinessRuleValue {
  public Object evaluate(Map<String, Object> value);
}
