package unsw.shopping;

import java.util.List;

public class Coles extends Supermarket {

  private static final int ITEM_CAP = 20;

  @Override
  public String getCardName() {
    return "flybuys";
  }

  @Override
  public void checkRestrictions(List<Item> items) {
    if (items.size() > ITEM_CAP) {
      System.out.println("Too many items.");
    }
  }

  @Override
  public void printReceipt(List<Item> items) {
    System.out.println("Today at Coles you purchased the following:");

    for (Item item : items) {
      System.out.println("- " + item.getName() + " : $" + item.getPrice());
    }
  }
}
