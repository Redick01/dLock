package cn.redick01.prog.annotation;

import cn.redick01.prog.model.LockType;

import java.lang.annotation.*;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 * 锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dlock {

    /**
     * 锁名称
     * @return
     */
    String name() default "";

    /**
     * 锁类型，默认为可重入锁
     * @return
     */
    LockType lockType() default LockType.Reentrant;

    /**
     * 尝试上锁等待的时间
     * @return
     */
    long waitTime() default Long.MIN_VALUE;

    /**
     * 获取锁后自动释放时间
     * @return
     */
    long releaseTime() default Long.MIN_VALUE;

    /**
     * 自定义业务key
     * @return
     */
    String [] keys() default {};
}
