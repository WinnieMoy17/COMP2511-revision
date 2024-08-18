package unsw.business.composite;

import java.util.Map;

import unsw.business.BusinessRule;
import unsw.business.BusinessRuleException;
import unsw.business.BusinessRuleValue;

public class GreaterThanOperator implements BusinessRule {

  private BusinessRuleValue value1;
  private BusinessRuleValue value2;

  public GreaterThanOperator(BusinessRuleValue value1, BusinessRuleValue value2) {
    this.value1 = value1;
    this.value2 = value2;
  }

  @Override
  public boolean evaluate(Map<String, Object> values) {
    Object v1 = value1.evaluate(values);
    Object v2 = value2.evaluate(values);

    if (!(v1 instanceof Number) || !(v2 instanceof Number)) {
      throw new BusinessRuleException("Both values must be numeric");
    }

    return (Integer) v1 > (Integer) v2;
  }
}
