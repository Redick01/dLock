package cn.redick01.prog.annotation;

import java.lang.annotation.*;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DlockKey {
    String value() default "";
}
