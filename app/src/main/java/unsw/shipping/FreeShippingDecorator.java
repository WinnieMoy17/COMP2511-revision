package unsw.shipping;

public class FreeShippingDecorator extends Decorator {

    private double price;
    private double weight;

    public FreeShippingDecorator(Product product, double price, double weight) {
        super(product);
        this.price = price;
        this.weight = weight;
    }

    @Override
    public double getShippingCost() {
        System.out.println(product.getPrice());
        if ((product.getPrice() >= price) && (product.getWeight() <= weight)) {
            return 0;
        }
        return product.getShippingCost();
    }

}
