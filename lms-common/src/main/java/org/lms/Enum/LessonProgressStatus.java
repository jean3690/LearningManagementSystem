package org.lms.Enum;

public enum LessonProgressStatus {
    NOT_STARTED("NOT_STARTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");
    private String name;

    LessonProgressStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
