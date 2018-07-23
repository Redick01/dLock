package cn.redick01.prog.model;

/**
 * @Author liu_penghui
 * @Date 2018/7/23.
 * 锁的信息
 */
public class LockInfo {
    /**
     * 锁类型
     */
    private LockType lockType;
    /**
     * name
     */
    private String name;
    /**
     * 等待时间
     */
    private long waitTime;
    /**
     * 释放锁时间
     */
    private long releaseTime;

    public LockInfo(LockType lockType, String name, long waitTime, long releaseTime) {
        this.lockType = lockType;
        this.name = name;
        this.waitTime = waitTime;
        this.releaseTime = releaseTime;
    }

    public LockType getLockType() {
        return lockType;
    }

    public void setLockType(LockType lockType) {
        this.lockType = lockType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
}
