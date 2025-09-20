package org.lms.Enum;

public enum AccountType {
    FREE("FREE"),
    BASIC("BASIC"),
    PREMIUM("PREMIUM"),
    ENTERPRISE("ENTERPRISE");
    private String name;

    AccountType(String name) {
        this.name = name;
    }

    AccountType() {
    }

    @Override
    public String toString() {
        return name;
    }
}
