package eng.la.model.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // can use in method only.
public @interface AuditedAttribute {
	/**
	 * Tipo attributo, default value String.class
	 * */
    Class<?> classType() default String.class;
}
