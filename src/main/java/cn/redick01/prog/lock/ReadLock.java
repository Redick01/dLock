package cn.redick01.prog.lock;

import cn.redick01.prog.model.LockInfo;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
public class ReadLock implements ILock {

    private LockInfo lockInfo;

    private RedissonClient redissonClient;

    private RReadWriteLock rReadWriteLock;

    public ReadLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public ILock setLockInfo(LockInfo lockInfo) {
        this.lockInfo = lockInfo;
        return this;
    }

    @Override
    public boolean acquire() {
        try {
            rReadWriteLock = redissonClient.getReadWriteLock(lockInfo.getName());
            return rReadWriteLock.readLock().tryLock(lockInfo.getWaitTime(), lockInfo.getReleaseTime(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void release() {
        if (rReadWriteLock.readLock().isHeldByCurrentThread()) {
            rReadWriteLock.readLock().unlockAsync();
        }
    }
}
