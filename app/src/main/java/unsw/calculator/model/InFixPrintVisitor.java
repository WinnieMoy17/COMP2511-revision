package unsw.calculator.model;

import unsw.calculator.model.tree.BinaryOperatorNode;
import unsw.calculator.model.tree.NumericNode;

public class InFixPrintVisitor implements Visitor {

  @Override
  public void visitBinaryOperatorNode(BinaryOperatorNode node) {
    System.out.print("(");
    node.acceptLeft(this);
    System.out.print(" " + node.getLabel() + " ");
    node.acceptRight(this);
    System.out.print(")");
  }

  @Override
  public void visitNumericNode(NumericNode node) {
    System.out.print(node.getValue());
  }
}