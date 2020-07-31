package cn.gingost;

import cn.gingost.base.BaseQuery;
import cn.gingost.system.entity.Dept;
import cn.gingost.system.repository.DeptRepository;
import cn.gingost.utils.QueryHelp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:lezzy
 * @Date:2020/7/29 17:49
 */

@SpringBootTest
public class test {
    @Autowired
    private DeptRepository deptRepository;
    @Test
    public void t1(){
        BCryptPasswordEncoder bc=new BCryptPasswordEncoder();
        String encode = bc.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void t2(){
        BaseQuery baseQuery=new BaseQuery();
        baseQuery.setIds(new ArrayList<Long>(){{
            add(9L);
            add(10L);
        }});
        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        System.out.println(all);
    }
}
