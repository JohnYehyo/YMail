package com.rongji.rjsoft.email.handler;

import com.rongji.rjsoft.email.annotation.MutiEmail;
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

/**
 * @description:
 * @author: JohnYehyo
 * @create: 2022-06-30 16:05:50
 */
public class IsMutiEmailValidator extends AbstractEmailValidator<MutiEmail> {

    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private java.util.regex.Pattern pattern;

    @Override
    public void initialize(MutiEmail emailAnnotation) {
        super.initialize(emailAnnotation);

        Pattern.Flag[] flags = emailAnnotation.flags();
        int intFlag = 0;
        for (Pattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        // we only apply the regexp if there is one to apply
        if (!".*".equals(emailAnnotation.regexp()) || emailAnnotation.flags().length > 0) {
            try {
                pattern = java.util.regex.Pattern.compile(emailAnnotation.regexp(), intFlag);
            } catch (PatternSyntaxException e) {
                throw LOG.getInvalidRegularExpressionException(e);
            }
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (null == value || "" == value) {
            return true;
        }
        Matcher m;
        boolean result = true;
        for (String str : value.toString().split(";")) {
            m = pattern.matcher(str);
            if(!m.matches()){
                result  = false;
                break;
            }
        }
        return result;
    }
}
