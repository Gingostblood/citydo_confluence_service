package cn.gingost.config.authcheck;

import cn.gingost.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:lezzy
 * @Date:2020/7/30 10:32
 */

@Service("ad")
public class Admin {
    public Boolean check(String...permissions){
        List<String> adPermissions= SecurityUtils.getUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return adPermissions.contains("admin") || Arrays.stream(permissions).anyMatch(adPermissions::contains);
    }
}
