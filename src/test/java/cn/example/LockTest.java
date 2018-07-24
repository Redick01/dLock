package cn.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainClass.class)
public class LockTest {

    @Autowired
    private TestService testService;

    /**
     * 同一进程内多线程获取锁测试
     * @throws Exception
     */
    @Test
    public void multithreadingTest() throws Exception {

        //创建6个线程
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String str = testService.getValue("sleep");
                    System.err.println("线程:[" + Thread.currentThread().getName() + "]拿到结果=》" + str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.submit(runnable);
        executorService.submit(runnable);
        System.in.read();
    }
    /**
     *线程休眠50秒
     * @throws Exception
     */
    @Test
    public void jvm1()throws Exception{
        String result=testService.getValue("sleep");
        Assert.assertEquals(result,"success");
    }

    /**
     *不休眠
     * @throws Exception
     */
    @Test
    public void jvm2()throws Exception{
        String result=testService.getValue("noSleep");
        Assert.assertEquals(result,"success");
    }
    /**
     *不休眠
     * @throws Exception
     */
    @Test
    public void jvm3()throws Exception{
        String result=testService.getValue("noSleep");
        Assert.assertEquals(result,"success");
    }
    //先后启动jvm1 和 jvm 2两个测试用例，会发现虽然 jvm2没休眠,因为getValue加锁了，
    // 所以只要jvm1拿到锁就基本同时完成

    /**
     * 测试业务key
     */
    @Test
    public void businessKeyJvm1()throws Exception{
        String result=testService.getValue("user1",1);
        Assert.assertEquals(result,"success");
    }
    /**
     * 测试业务key
     */
    @Test
    public void businessKeyJvm2()throws Exception{
        String result=testService.getValue("user1",1);
        Assert.assertEquals(result,"success");
    }
    /**
     * 测试业务key
     */
    @Test
    public void businessKeyJvm3()throws Exception{
        String result=testService.getValue("user1",2);
        Assert.assertEquals(result,"success");
    }
    /**
     * 测试业务key
     */
    @Test
    public void businessKeyJvm4()throws Exception{
        String result=testService.getValue(new User(3,"kl"));
        Assert.assertEquals(result,"success");
    }
}
