package unsw.shopping;

import java.util.List;

public class Woolies extends Supermarket {

  private static final int ITEM_CAP = 55;

  @Override
  public String getCardName() {
    return "Everyday Rewards";
  }

  @Override
  public void checkRestrictions(List<Item> items) {
    if (items.size() >= ITEM_CAP) {
      System.out.println("Sorry, that's more than we can handle in a single order!");
    }
  }

  @Override
  public void printReceipt(List<Item> items) {
    System.out.print("Your purchase: ");

    for (int i = 0; i < items.size() - 1; i++) {
      System.out.print(items.get(i).getName() + ", ($" + items.get(i).getPrice() + "), ");
    }
    System.out.println(
        items.get(items.size() - 1).getName() + " ($" + items.get(items.size() - 1).getPrice() + ").");
  }

}
