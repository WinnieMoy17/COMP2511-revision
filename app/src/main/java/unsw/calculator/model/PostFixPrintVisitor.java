package unsw.calculator.model;

import unsw.calculator.model.tree.BinaryOperatorNode;
import unsw.calculator.model.tree.NumericNode;

public class PostFixPrintVisitor implements Visitor {

  @Override
  public void visitBinaryOperatorNode(BinaryOperatorNode node) {
    node.acceptLeft(this);
    System.out.print(" ");
    node.acceptRight(this);
    System.out.print(" ");
    System.out.print(node.getLabel());
  }

  @Override
  public void visitNumericNode(NumericNode node) {
    System.out.print(node.getValue());
  }

}