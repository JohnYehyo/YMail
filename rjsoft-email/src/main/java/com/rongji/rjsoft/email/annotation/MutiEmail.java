package com.rongji.rjsoft.email.annotation;

import com.rongji.rjsoft.email.handler.IsMutiEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2022-06-30 16:02:04
 */
@Documented
@Constraint(validatedBy = {IsMutiEmailValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface MutiEmail {

    String message() default "{请输入正确的邮箱地址}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return an additional regular expression the annotated element must match. The default
     * is any string ('.*')
     */
    String regexp() default ".*";

    /**
     * @return used in combination with {@link #regexp()} in order to specify a regular
     * expression option
     */
    Pattern.Flag[] flags() default {};

}
