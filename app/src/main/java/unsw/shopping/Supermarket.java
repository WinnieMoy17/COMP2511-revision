package unsw.shopping;

import java.util.List;

public abstract class Supermarket {
  private String supermarket;
  private int amountPurchased;

  public abstract String getCardName();

  public abstract void checkRestrictions(List<Item> items);

  public abstract void printReceipt(List<Item> items);

  public void checkout(List<Item> items, String paymentMethod, int paymentAmount, boolean receipt) {
    // Welcome the user
    String cardName = getCardName();
    System.out.println("Welcome! Please scan your first item. If you have a " + cardName
        + " card, please scan it at any time.");

    scanItems(items);

    // Take the user's payment
    if (paymentAmount < amountPurchased) {
      System.out.println("Not enough $$$.");
      return;
    }

    if (paymentMethod.equals("cash")) {
      System.out.println("Paid $" + paymentAmount + " with $" + (paymentAmount - amountPurchased) + " change.");
    } else {
      paymentAmount = amountPurchased;
      System.out.println("Paid $" + paymentAmount + ".");
    }

    // Print the receipt
    if (receipt) {
      printReceipt(items);
    }
  }

  public void scanItems(List<Item> items) {
    // Supermarkets have restrictions on the number of items allowed
    checkRestrictions(items);

    if (items.size() == 0) {
      System.out.println("You do not have any items to purchase.");
      return;
    }

    for (Item item : items) {
      amountPurchased += item.getPrice();
    }
  }
}
