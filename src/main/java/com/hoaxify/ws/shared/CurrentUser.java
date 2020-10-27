package com.hoaxify.ws.shared;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.PARAMETER })
@Retention(RUNTIME)
@AuthenticationPrincipal //bize doğrudan principal işlemini yapacak.
public @interface CurrentUser {
}
