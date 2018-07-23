package cn.redick01.prog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author liu_penghui
 * @Date 2018/7/23.
 * 锁配置，redisson配置
 */
@ConfigurationProperties(prefix = LockConfig.PREFIX)
public class LockConfig {

    //redisson
    /**
     * 配置文件前缀
     */
    public static final String PREFIX = "spring.lock";
    /**
     * redis地址
     */
    private String address;
    /**
     * redis密码
     */
    private String password;
    /**
     * 数据源
     */
    private int database = 1;

    private ClusterServer clusterServer;

    private String codec = "org.redisson.codec.JsonJacksonCodec";

    //Lock
    private long waitTime = 60;

    private long releaseTime = 60;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public ClusterServer getClusterServer() {
        return clusterServer;
    }

    public void setClusterServer(ClusterServer clusterServer) {
        this.clusterServer = clusterServer;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
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

    public static class ClusterServer {

        private String [] nodeAddress;

        public String[] getNodeAddress() {
            return nodeAddress;
        }

        public void setNodeAddress(String[] nodeAddress) {
            this.nodeAddress = nodeAddress;
        }
    }
}
