package unsw.business.composite;

import java.util.Map;

import unsw.business.BusinessRuleValue;

public class ConstantValue implements BusinessRuleValue {

  private Integer val;

  public ConstantValue(Integer val) {
    this.val = val;
  }

  @Override
  public Object evaluate(Map<String, Object> value) {
    return val;
  }

}
