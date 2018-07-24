package cn.example;

import cn.redick01.prog.annotation.Dlock;
import cn.redick01.prog.annotation.DlockKey;
import org.springframework.stereotype.Service;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
@Service
public class TestService {

    @Dlock(waitTime = Long.MAX_VALUE,releaseTime = 1)
    public String getValue(String param) throws Exception {
        if ("sleep".equals(param)) {//线程休眠或者断点阻塞，达到一直占用锁的测试效果
            Thread.sleep(1000*3);
        }
        return "success";
    }

    @Dlock(keys = {"#userId"})
    public String getValue(String userId, @DlockKey int id)throws Exception{
        Thread.sleep(60*1000);
        return "success";
    }

    @Dlock(keys = {"#user.name","#user.id"})
    public String getValue(User user)throws Exception{
        Thread.sleep(60*1000);
        return "success";
    }
}
