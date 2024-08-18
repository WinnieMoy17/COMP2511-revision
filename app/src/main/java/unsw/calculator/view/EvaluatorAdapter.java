package unsw.calculator.view;

import unsw.calculator.model.EvaluatorVisitor;
import unsw.calculator.model.Parser;
import unsw.calculator.model.tree.TreeNode;

public class EvaluatorAdapter implements Evaluator {
  @Override
  public int evaluate(String expression) {
    Parser parser = new Parser(expression);
    TreeNode node = parser.parse();
    EvaluatorVisitor evaluator = new EvaluatorVisitor();
    node.accept(evaluator);
    return evaluator.getValue();
  }
}