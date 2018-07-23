package cn.redick01.prog.lock;

import cn.redick01.prog.model.LockInfo;

/**
 * @Author liu_penghui
 * @Date 2018/7/23.
 */
public interface ILock {

    ILock setLockInfo(LockInfo lockInfo);

    /**
     * 加锁
     * @return
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();
}
