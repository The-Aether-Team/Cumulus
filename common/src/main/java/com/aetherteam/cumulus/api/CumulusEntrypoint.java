package com.aetherteam.cumulus.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used on Neoforge Platform to indicate that the given class
 * implementation is a valid instance of {@link MenuInitializer}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CumulusEntrypoint {
}
