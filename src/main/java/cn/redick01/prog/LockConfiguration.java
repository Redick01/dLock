package cn.redick01.prog;

import cn.redick01.prog.config.LockConfig;
import cn.redick01.prog.core.BusinessKeyHandler;
import cn.redick01.prog.core.LockAspectHandler;
import cn.redick01.prog.core.LockInfoHandler;
import cn.redick01.prog.lock.LockFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
@Configuration
@Import({LockAspectHandler.class})
public class LockConfiguration {

    @Bean
    public LockInfoHandler lockInfoHandler() {
        return new LockInfoHandler();
    }

    @Bean
    public BusinessKeyHandler businessKeyHandler() {
        return new BusinessKeyHandler();
    }

    @Bean
    public LockFactory lockFactory() {
        return new LockFactory();
    }

    @Bean
    public LockConfig lockConfig() {
        return new LockConfig();
    }
}
