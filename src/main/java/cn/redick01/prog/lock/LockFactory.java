package cn.redick01.prog.lock;

import cn.redick01.prog.annotation.Dlock;
import cn.redick01.prog.core.LockInfoHandler;
import cn.redick01.prog.model.LockInfo;
import cn.redick01.prog.model.LockType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
public class LockFactory {

    Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private LockInfoHandler lockInfoHandler;

    private static final Map<LockType, ILock> lockMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        lockMap.put(LockType.Reentrant, new ReentrantLock(redissonClient));
        lockMap.put(LockType.Fair, new FairLock(redissonClient));
        lockMap.put(LockType.Read, new ReadLock(redissonClient));
        lockMap.put(LockType.Write, new WriteLock(redissonClient));
        logger.info("The lock Initialization Successful!!!");
    }

    public ILock getLock(ProceedingJoinPoint joinPoint, Dlock dlock) {
        LockInfo lockInfo = lockInfoHandler.get(joinPoint, dlock);
        return lockMap.get(lockInfo.getLockType()).setLockInfo(lockInfo);
    }
}
