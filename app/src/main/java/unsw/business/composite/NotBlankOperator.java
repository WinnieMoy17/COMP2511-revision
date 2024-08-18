package unsw.business.composite;

import java.util.Map;

import unsw.business.BusinessRule;
import unsw.business.BusinessRuleValue;

public class NotBlankOperator implements BusinessRule {

  private BusinessRuleValue val;

  public NotBlankOperator(BusinessRuleValue val) {
    this.val = val;
  }

  @Override
  public boolean evaluate(Map<String, Object> values) {
    Object v = val.evaluate(values);

    if (v == null) {
      return false;
    }
    String valAsString = (String) v;
    return !valAsString.isBlank();
  }

}
