
package io.frame.annotation;

import java.lang.annotation.*;

/**
 * 登录效验,所有controller的方法上都需要注入这个
 * @author fury
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
