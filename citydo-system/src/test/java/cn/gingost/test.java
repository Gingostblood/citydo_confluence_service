package cn.gingost;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author:lezzy
 * @Date:2020/7/29 17:49
 */

@SpringBootTest
public class test {
    @Test
    public void t1(){
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        String encode = bc.encode("123456");
        System.out.println(encode);
    }
}
