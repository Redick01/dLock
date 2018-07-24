package cn.example;

import cn.redick01.prog.annotation.Dlock;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liu_penghui
 * @Date 2018/7/24.
 */
public class Test {
    @Dlock
    public static void main(String[] args) {
        List<String> list = new ArrayList();
        String a = "a";
        String b = "b";
        String c = "c";
        list.add(a);
        list.add(b);
        list.add(c);
        System.out.println(StringUtils.collectionToDelimitedString(list, "", "-", ""));
    }
}
