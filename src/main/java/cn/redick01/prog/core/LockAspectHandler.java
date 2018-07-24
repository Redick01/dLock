package cn.redick01.prog.core;

import cn.redick01.prog.annotation.Dlock;
import cn.redick01.prog.lock.ILock;
import cn.redick01.prog.lock.LockFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 * AOP处理锁切面
 */
@Aspect
@Component
public class LockAspectHandler {

    @Autowired
    LockFactory lockFactory;

    @Around(value = "@annotation(dlock)")
    public Object around(ProceedingJoinPoint joinPoint, Dlock dlock) throws Throwable {
        ILock iLock = lockFactory.getLock(joinPoint, dlock);
        boolean currentThreadLock = false;
        try {
            currentThreadLock = iLock.acquire();
            return joinPoint.proceed();
        } finally {
            if (currentThreadLock) {
                iLock.release();
            }
        }
    }
}
