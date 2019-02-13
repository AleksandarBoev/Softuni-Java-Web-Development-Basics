package app.domain.enums;

public enum ProductType {
    FOOD, DOMESTIC, HEALTH, COSMETIC;

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1, this.name().length()).toLowerCase();
    }
}
