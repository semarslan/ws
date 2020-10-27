package com.hoaxify.ws.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD }) //Bu annotationı nerde kullanacağımızı belirtiyoruz. Field dediğimiz de id, usename, password gibi alanlar yani
@Retention(RUNTIME) //annotationın runtimeda çözümlenmesi ile ilgili bir davranış
@Constraint(validatedBy = { UniqueUsernameValidator.class }) //uniqueUsernam annotation'ının kullanıldığı yerlerde kullanılacka olan validation lojiğinin ilgili class'ı
public @interface UniqueUsername {
    String message() default "{hoaxify.constraint.username.UniqueUsername.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
