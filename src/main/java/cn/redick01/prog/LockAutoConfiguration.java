package cn.redick01.prog;

import cn.redick01.prog.config.LockConfig;
import cn.redick01.prog.core.BusinessKeyHandler;
import cn.redick01.prog.core.LockAspectHandler;
import cn.redick01.prog.core.LockInfoHandler;
import cn.redick01.prog.lock.LockFactory;
import io.netty.channel.nio.NioEventLoopGroup;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ClassUtils;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 * 自动装配
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(LockConfig.class)
@Import({LockAspectHandler.class})
public class LockAutoConfiguration {

    @Autowired
    private LockConfig lockConfig;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    RedissonClient redisson() throws Exception {
        Config config = new Config();
        if (lockConfig.getClusterServer() != null) {
            config.useClusterServers().setPassword(lockConfig.getPassword())
                    .addNodeAddress(lockConfig.getClusterServer().getNodeAddress());
        } else {
            config.useSingleServer().setAddress(lockConfig.getAddress())
                    .setDatabase(lockConfig.getDatabase())
                    .setPassword(lockConfig.getPassword());
        }
        Codec codec = (Codec)ClassUtils.forName(lockConfig.getCodec(), ClassUtils.getDefaultClassLoader()).newInstance();
        config.setCodec(codec);
        config.setEventLoopGroup(new NioEventLoopGroup());
        return Redisson.create(config);
    }

    @Bean
    public LockFactory lockFactory() {
        return new LockFactory();
    }

    @Bean
    public BusinessKeyHandler businessKeyHandler() {
        return new BusinessKeyHandler();
    }

    @Bean
    public LockInfoHandler lockInfoHandler() {
        return new LockInfoHandler();
    }
}
