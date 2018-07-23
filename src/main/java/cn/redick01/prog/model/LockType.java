package cn.redick01.prog.model;

/**
 * @Author liu_penghui
 * @Date 2018/7/23.
 * 锁类型枚举
 */
public enum LockType {

    /**
     * 公平锁
     */
    Fair,
    /**
     * 读锁
     */
    Read,
    /**
     * 写锁
     */
    Write,
    /**
     * 可重入锁
     */
    Reentrant;

    LockType() {

    }
}
