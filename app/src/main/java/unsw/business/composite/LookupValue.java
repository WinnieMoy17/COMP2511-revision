package unsw.business.composite;

import java.util.Map;

import unsw.business.BusinessRuleValue;

public class LookupValue implements BusinessRuleValue {

  private String var;

  public LookupValue(String var) {
    this.var = var;
  }

  @Override
  public Object evaluate(Map<String, Object> value) {
    return value.get(var);
  }
}
