package com.scrappy.database.services;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * It allows to specify the qualifier in a neat and secure way.
 *
 * @version 1.0-RC
 * @since 2018-02-20
 */

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface BookServiceQualifier {
    BookServiceType type();

    enum BookServiceType {
        POSTGRESQL
    }
}
