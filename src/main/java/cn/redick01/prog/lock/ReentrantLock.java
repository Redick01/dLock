package cn.redick01.prog.lock;

import cn.redick01.prog.model.LockInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
public class ReentrantLock implements ILock {

    private RedissonClient redissonClient;

    private RLock rLock;

    private LockInfo lockInfo;

    public ReentrantLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public ILock setLockInfo(LockInfo lockInfo) {
        this.lockInfo = lockInfo;
        return this;
    }

    public LockInfo getLockInfo() {
        return lockInfo;
    }

    @Override
    public boolean acquire() {
        try {
            rLock = redissonClient.getLock(lockInfo.getName());
            return rLock.tryLock(lockInfo.getWaitTime(), lockInfo.getReleaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void release() {
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlockAsync();
        }
    }
}
