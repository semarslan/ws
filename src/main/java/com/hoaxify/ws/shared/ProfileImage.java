package com.hoaxify.ws.shared;


import com.hoaxify.ws.user.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD }) //Bu annotationı nerde kullanacağımızı belirtiyoruz. Field dediğimiz de id, usename, password gibi alanlar yani
@Retention(RUNTIME) //annotationın runtimeda çözümlenmesi ile ilgili bir davranış
@Constraint(validatedBy = { ProfileImageValidator.class })
public @interface ProfileImage {
    String message() default "{hoaxify.constraint.ProfileImage.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
