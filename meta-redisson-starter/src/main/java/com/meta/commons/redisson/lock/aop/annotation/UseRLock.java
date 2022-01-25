package com.meta.commons.redisson.lock.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Xiong Mao
 * @date 2021/12/23 10:58
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UseRLock {
    /**
     * 最大超时时间 单位ms
     *
     * @return
     */
    long maxLockTime() default 5000;

    /**
     * lock的area名称
     *
     * @return
     */
    String area() default "default";

    /**
     * 默认取　package+class
     */
    String clazz() default "default";

    /**
     * 默认取方法名称
     *
     * @return
     */
    String method() default "default";

    LockPolicy policy() default LockPolicy.REJECT;

    public enum LockPolicy {
        WAIT,
        REJECT
    }
}
