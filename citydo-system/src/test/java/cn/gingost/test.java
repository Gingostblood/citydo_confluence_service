package cn.gingost;

import cn.gingost.base.BaseQuery;
import cn.gingost.security.domain.JwtProperties;
import cn.gingost.system.entity.Dept;
import cn.gingost.system.entity.Menu;
import cn.gingost.system.entity.User;
import cn.gingost.system.repository.DeptRepository;
import cn.gingost.system.repository.MenuRepository;
import cn.gingost.system.repository.UserRepository;
import cn.gingost.utils.QueryHelp;
import cn.gingost.utils.RedisUtils;
import cn.hutool.core.collection.CollectionUtil;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultJwtParser;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:lezzy
 * @Date:2020/7/29 17:49
 */

@SpringBootTest
public class test {
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtProperties jwtProperties;

    private JwtParser jwtParser;

    @Test
    public void t1() {
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        String encode = bc.encode("123456");
        System.out.println(encode);
    }

    @Test
    public void t2() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setIds(new ArrayList<Long>() {{
            add(9L);
            add(10L);
        }});
        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        System.out.println(all);
    }

    @Test
    public void t3() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setNickName("zh");
        List<User> all = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        System.out.println(all.toString());
//        baseQuery.setNickName("宜");
//        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
//        System.out.println("=====================");
//        System.out.println(all);
    }

    @Test
    public void t4() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setNickName("宜");
        List<Dept> all = deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        System.out.println("=====================");
        System.out.println(all);
    }

    @Test
    public void t5() {
        BaseQuery baseQuery = new BaseQuery();
        baseQuery.setNickName("新闻");
        List<Menu> all = menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, baseQuery, criteriaBuilder));
        System.out.println(all.size());
    }

    @Test
    public void t6() {
//        public void afterPropertiesSet() {
//            byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
//            Key key = Keys.hmacShaKeyFor(keyBytes);
//            jwtParser = Jwts.parser()
//                    .setSigningKey(key);
//            jwtBuilder = Jwts.builder()
//                    .signWith(key, SignatureAlgorithm.HS512);
//        }
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJhYjQwYTAyNjY2ZWE0MDkyOTI0YzdkM2RmNTZkNjhmNCIsImF1dGgiOiJkZXB0OmVkaXQsdXNlcjpsaXN0LHVzZXI6YWRkLGRlcHQ6YWRkLGFkbWluLHJvbGU6YWRkLHVzZXI6ZGVsLGRlcHQ6bGlzdCx1c2VyOmVkaXQscm9sZTpkZWwscm9sZTpsaXN0LHJvbGU6ZWRpdCxkZXB0OmRlbCIsInN1YiI6Imxpemhlbnl1In0.zQTP5CUJLGIkDRMqHmjIPCgm3a8bpJYK9uoADhzz9GW2YlGp_7Cf5E0ixyIUzW3k0IEa4nbC2nfmRJdvmQBkUw";

        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);

        String subject = new DefaultJwtParser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
        System.out.println(subject);
    }


    @Test
    public void t7() {
        List<Long> l1 = new ArrayList<Long>() {{
            add(1L);
            add(2L);
        }};
        List<Long> l2 = new ArrayList<Long>() {{
            add(1L);
            add(4L);
        }};
        Collection<Long> intersection = CollectionUtil.intersection(l1, l2);
        System.out.println(intersection.toString());
    }

    @Test
    public void t8() {
        Collection<GrantedAuthority> grantedAuthorities = (Collection<GrantedAuthority>) redisUtils.get("role::user-rloe:lizhenyu");
        List<String> collect = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println(collect);
    }
}
