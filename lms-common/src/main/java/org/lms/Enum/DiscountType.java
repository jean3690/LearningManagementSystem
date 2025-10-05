package org.lms.Enum;

public enum DiscountType {
    PERCENTAGE("PERCENTAGE"),
    FIXED_AMOUNT("FIXED_AMOUNT");
    private String name;
    DiscountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
