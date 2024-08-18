package unsw.calculator.model;

import java.util.Stack;

import unsw.calculator.model.tree.BinaryOperatorNode;
import unsw.calculator.model.tree.NumericNode;

public class EvaluatorVisitor implements Visitor {
  private Stack<Integer> stack = new Stack<>();

  @Override
  public void visitBinaryOperatorNode(BinaryOperatorNode node) {
    node.acceptLeft(this);
    node.acceptRight(this);

    int right = stack.pop();
    int left = stack.pop();

    stack.push(node.compute(left, right));
  }

  @Override
  public void visitNumericNode(NumericNode node) {
    stack.push(node.getValue());
  }

  public Integer getValue() {
    if (stack.size() != 1) {
      throw new IllegalStateException("Stack should contain exactly one result.");
    }
    return stack.pop();
  }

}