package cn.redick01.prog.core;

import cn.redick01.prog.annotation.Dlock;
import cn.redick01.prog.config.LockConfig;
import cn.redick01.prog.model.LockInfo;
import cn.redick01.prog.model.LockType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 * 锁信息初始化
 */
public class LockInfoHandler {

    public static final String LOCK_NAME_PREFIX = "lock";

    public static final String LOCK_NAME_SEPARATOR = ".";

    @Autowired
    private LockConfig lockConfig;

    @Autowired
    private BusinessKeyHandler businessKeyHandler;

    public LockInfo get(ProceedingJoinPoint joinPoint, Dlock dlock) {

        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        LockType lockType = dlock.lockType();
        String businessName = businessKeyHandler.getKeyName(joinPoint, dlock);//获取业务key
        String lockName = LOCK_NAME_PREFIX + LOCK_NAME_SEPARATOR + getName(dlock.name(), methodSignature) + businessName;

        long waitTime = getWaitTime(dlock);
        long releaseTime = getReleaseTime(dlock);
        return new LockInfo(lockType, lockName, waitTime, releaseTime);
    }

    public String getName(String annotationName, MethodSignature signature) {
        if (annotationName.isEmpty()) {
            return String.format("%s.%s", signature.getDeclaringTypeName(), signature.getMethod().getName());
        } else {
            return annotationName;
        }
    }

    public long getWaitTime(Dlock dlock) {
        return dlock.waitTime() == Long.MIN_VALUE ? lockConfig.getWaitTime() : dlock.waitTime();
    }

    public long getReleaseTime(Dlock dlock) {
        return dlock.releaseTime() == Long.MIN_VALUE ? lockConfig.getReleaseTime() : dlock.releaseTime();
    }
}
