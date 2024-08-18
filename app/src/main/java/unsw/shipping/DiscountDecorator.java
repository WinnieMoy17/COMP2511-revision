package unsw.shipping;

public class DiscountDecorator extends Decorator {

    private double discount;

    public DiscountDecorator(Product product, double discount) {
        super(product);
        this.discount = discount;
    }

    @Override
    public double getPrice() {
        return product.getPrice() - (product.getPrice() * (discount / 100));
    }
}
