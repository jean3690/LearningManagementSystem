package org.lms.Enum;

public enum UserType {
    //个人用户
    INDIVIDUAL("INDIVIDUAL"),
    //企业用户
    BUSINESS("BUSINESS");

    @Override
    public String toString() {
        return name;
    }

    private String name;

    UserType() {
    }

    UserType(String name) {
        this.name = name;
    }
}
