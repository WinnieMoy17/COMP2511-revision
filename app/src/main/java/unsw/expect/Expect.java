package unsw.expect;

public class Expect<E> {

    private E inner;
    private E comparing;
    private String comparison;

    protected Expect() {
    }

    public Expect(E obj) {
        this.inner = obj;
    }

    public Expect<E> toEqual(E other) {
        this.comparing = other;
        this.comparison = "EQUAL";
        return this;
    }

    public <T extends Comparable<E>> Expect<E> lessThan(T other) {
        this.comparing = other;
        this.comparison = "LESS THAN";
        return this;
    }

    public <T extends Comparable<E>> Expect<E> greaterThanOrEqualTo(T other) {
        return null;
    }

    public Expect<E> not() {
        return null;
    }

    public Expect<E> skip() {
        return null;
    }

    public void evaluate() {
        switch (comparison) {
            case "EQUAL":
                if (!inner.equals(comparing)) {
                    throw new ExpectationFailedException("Expected " + inner + " to equal " + comparing);
                }
                break;
            default:

        }
    }

    protected E getInner() {
        return inner;
    }

}
