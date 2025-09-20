package org.lms.annotation;

import org.lms.Enum.OpreationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OpreationType[] value();
}
